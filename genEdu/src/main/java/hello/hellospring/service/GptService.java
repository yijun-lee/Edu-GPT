package hello.hellospring.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GptService {
    private OpenAiService service;
    private ChatCompletionRequest chatCompletionRequest;
    List<ChatMessage> messages = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    public void createGptService(){
        service = new OpenAiService("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",1800);
        chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();
    }

    public String createChatCompletion(String text, String role)
    {
        ChatMessage message = new ChatMessage();
        message.setRole(role);
        message.setContent(text);
        messages.add(message);
        return service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }

    public void initiateChatCompletion()
    {
        messages.clear();
    }

    public String generateSummary(String[] text){
        initiateChatCompletion();
        if (text.length == 1)
            return createChatCompletion("summarize this:".concat(text[0]), "user");
        else {
            int len  = text.length;
            String summary_result = "";
            for (int i = 0;i<len;i++)
            {
                initiateChatCompletion();
                summary_result = summary_result.concat(createChatCompletion("summarize this:".concat(text[i]), "user"));
                System.out.println(i);
                System.out.println(summary_result);
            }
            return createChatCompletion("summarize this :".concat(summary_result),"user");
        }
    }
    public String generateExercise(String[] text, int quiz_cnt, String difficulty, int quiz_type){
        initiateChatCompletion();//전에 내용을 초기화 시키고 새로운 세션으로 시작할지?
        //Please make 5 questions about this article so that is multiple choice
        String quiz_prompt = "";
        String summary_result = text[0];
        if (quiz_type == 0)//객
            quiz_prompt ="다음 글에 대한 객관식 문제를 in Korean로 " + Integer.toString(quiz_cnt) + "개 만들어주는데, 각 문제당 4개의 선택지가 나오도록 만들고 선택지는 알파벳 소문자를 사용하고 \'a.\' 식으로 표시해줘. 각 문제 다음에는 답을 알려줘. 그리고 각 문제당 답은 하나씩만 나와야해 문제 난이도는 " + difficulty +"수준으로 만들어줘. : ";
        else if (quiz_type == 1) {//주
            quiz_prompt = "다음 글에 대한 주관식 문제를 한국어로 " + Integer.toString(quiz_cnt) + "개 만들어주는데, 각 문제 다음에는 답을 알려줘. 문제 난이도는 " + difficulty +"수준으로 만들어줘. : ";
        } else if (quiz_type == 2) {//TF
            quiz_prompt = "True/False 문제를 한국어로 " + Integer.toString(quiz_cnt) + "개 만들어주는데, 각 문제 다음 줄에 답을 알려줘. 출력 형식은 \n1. 문제\n답: True\n이런식으로 출력해줘 : ";
        }
        String quiz = createChatCompletion(quiz_prompt.concat(summary_result), "user");
        System.out.println(quiz);
        //summary_result = generateSummary(text);
        return quiz;
    }


    public String[][] convertToQuiz(String quiz_text, int cnt, int quiz_type)
    {
        String quiz[][];
        String[] line = quiz_text.split("\n");
        int index;
        if (quiz_type == 0)
        {
            quiz = new String[cnt][6];
            for(int i = 0;i<cnt;i++)
            {
                index = 0;
                for(int j=0;j<8;j++)
                {
                    if (j!=5 && j!=7)
                    {
                        if(j==6)
                            quiz[i][index] = line[i*8 + j].substring(3,4);
                        else
                            quiz[i][index] = line[i*8 + j];
                        index +=1;
                    }
                    if (i == cnt-1 && j == 6)
                        break;
                }
            }

        } else if (quiz_type == 2) {
            quiz = new String[cnt][2];
            for(int i = 0;i<cnt;i++)
            {
                index = 0;
                for(int j=0;j<3;j++)
                {
                    if (j!=2)
                    {
                        if (j==1){
                            quiz[i][index] = line[i * 3 + j].substring(3);
                        }else {
                            quiz[i][index] = line[i * 3 + j];
                        }
                        index +=1;
                    }
                }
            }
        }
        else{
            quiz = new String[cnt][2];
        }

        return quiz;
    }

    public String[][] makeQuiz(String[] text, int quiz_cnt, String difficulty, int quiz_type){
        String quiz_text = generateExercise(text,quiz_cnt,difficulty,quiz_type);
        return convertToQuiz(quiz_text,quiz_cnt,quiz_type);
//        quiz_session(convertToQuiz(quiz_text,quiz_cnt,quiz_type), quiz_cnt,quiz_type);
//        return convertToQuiz(quiz_text,quiz_cnt,quiz_type);
    }
    public HashMap<String, Object> listToJson(HashMap<String, Object> map, String[][] quiz, int cnt, int type){
        map.clear();
        HashMap<String, Object> tmp;
        if(type == 0)
        {
            for(int i = 0;i<cnt;i++){
                tmp = new HashMap<>();
                tmp.put("num", i+1);
                tmp.put("problem",quiz[i][0]);
                tmp.put("example1",quiz[i][1]);
                tmp.put("example2",quiz[i][2]);
                tmp.put("example3",quiz[i][3]);
                tmp.put("example4",quiz[i][4]);
                map.put("quiz"+String.valueOf(i), tmp);
            }
        }
        else if(type == 2)
        {
            for(int i = 0;i<cnt;i++){
                tmp = new HashMap<>();
                tmp.put("num", i+1);
                tmp.put("problem",quiz[i][0]);
                map.put("quiz"+String.valueOf(i), tmp);
            }
        }
        return map;
    }

    public boolean score_ans(String [][]quiz, int i, String ans, int quiz_type)
    {
        if (quiz_type == 0){
            if (quiz[i][5].equals(ans)) return true;
            else return false;
        }
        else if(quiz_type == 2){
            if (quiz[i][1].equals(ans)) return true;
            else return false;
        }
        else return (quiz[i][1] == ans); //다른 유형의 문제 올자리
    }
    public void quiz_session(String [][] quiz, int cnt, int quiz_type)
    {
        int j,i;
        String answer;
        for(i = 0;i<cnt;i++){
            if (quiz_type == 0){
                for(j = 0;j<5;j++)
                    System.out.println(quiz[i][j]);
                answer = "c";
                if (answer.equals(quiz[i][5]))
                    System.out.println("correct");
                else
                    System.out.println("wrong");
            } else if (quiz_type == 2){
                for(j = 0;j<1;j++)
                    System.out.println(quiz[i][j]);
                answer = "False";
                if (answer.equals(quiz[i][1]))
                    System.out.println("correct");
                else
                    System.out.println("wrong");
            }
        }
    }

    public String generateTranslation(String[] text, String from_lan, String to_lan)
    {
        initiateChatCompletion();//전에 내용을 초기화 시키고 새로운 세션으로 시작할지?
        String translation_prompt = "translate this article that is written by " + from_lan + "to" + to_lan + ":";
        if (text.length == 1)
            return createChatCompletion(translation_prompt.concat(text[0]), "user");
        else{
            int len  = text.length;
            String summary_result = "";
            for (int i = 0;i<len;i++)
            {
                initiateChatCompletion();
                summary_result = summary_result.concat(createChatCompletion(translation_prompt.concat(text[i]), "user"));
                System.out.println(i);
                //System.out.println(summary_result);
            }
            return summary_result;
        }
    }

    public void makePPT(int page_cnt, String target, String[] info, String file_summary) throws IOException {
        String response = makePptGptResponse(file_summary,page_cnt,target);
        String[][] list = pptTextToList(page_cnt, response);
        create_ppt(page_cnt,list,info);
    }

    public String makePptGptResponse(String file_summary, int page_cnt, String target){
        String prompt = "다음 내용을 가지고 ppt 슬라이드를 만들고 싶어. \n" + "슬라이드 장수는 "+ Integer.toString(page_cnt) + "장 이고 발표대상은 " + target + ". ";
        prompt = prompt + "ppt에 들어 갈 내용을 알려줘. \n" +
                "Slide:\n" +
                "제목: {제목}\n" +
                "내용: {내용}\n" +
                "이 형식으로 답변해줘.\n" +
                "밑의 내용으로 답변해주면 된다.\n" +
                file_summary;
        String response = createChatCompletion(prompt, "user");
        return response;
    }

    public String[][] pptTextToList(int page_cnt, String text){
        String[][] pptList = new String[page_cnt][2];
        String[] line = text.split("\n");
        int cnt, j;
        j = 0;
        for(int i=0;i<page_cnt;i++)
        {
            cnt = 0;
            while (cnt<3) {
                if (line[j].equals("") == false) {
                    if (cnt > 0) pptList[i][cnt - 1] = line[j].substring(4);
                    cnt += 1;
                }
                j+=1;
            }
        }
        return pptList;
    }

    public void create_ppt(int slide_cnt, String[][] pptList, String[] pptInfo) throws IOException {
        int i;
        XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream("/Users/koomin/구민/ppt_template.pptx"));
        XMLSlideShow newPpt = new XMLSlideShow();
        XSLFSlide originalSlide;
        XSLFSlide newSlide;
        //제목
        originalSlide = originalPpt.getSlides().get(0);
        newSlide = newPpt.createSlide();
        newSlide.importContent(originalSlide);
        i = 0;
        for (XSLFShape shape : newSlide.getShapes()) {
            if (shape instanceof XSLFTextBox) {
                XSLFTextBox textBox = (XSLFTextBox) shape;
                System.out.println(textBox.getText());
                textBox.setText("");
                if (i == 0) {//부제
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText(pptInfo[1]);
                    run.setFontSize(18.0);
                    run.setFontColor(new Color(255,255,255));
                }
                else if(i == 1){//제목
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText(pptInfo[0]);
                    run.setBold(true);
                    run.setFontSize(44.0);
                    run.setFontColor(new Color(89,89,89));
                }
                else{//제작자
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText(pptInfo[2]);
                    run.setFontSize(18.0);
                    run.setFontColor(new Color(89,89,89));
                }
                i+=1;
            }
        }
        //제목

        //분문
        originalSlide = originalPpt.getSlides().get(1);
        for (int page_no = 0; page_no < slide_cnt; page_no++) {
            newSlide = newPpt.createSlide();
            newSlide.importContent(originalSlide);
            i = 0;
            for (XSLFShape shape : newSlide.getShapes()) {
                if (shape instanceof XSLFTextBox) {
                    XSLFTextBox textBox = (XSLFTextBox) shape;
                    System.out.println(textBox.getText());
                    textBox.setText("");
                    if (i == 0) {//내용
                        XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                        XSLFTextRun run = paragraph.addNewTextRun();
                        run.setText(pptList[page_no][1]);
                        run.setFontSize(28.0);
                        paragraph.setBullet(true);
                    } else if (i == 1) {//번호
                        XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                        XSLFTextRun run = paragraph.addNewTextRun();
                        run.setText("0" + Integer.toString(page_no + 1));
                        run.setBold(true);
                        run.setFontSize(48.0);
                        run.setFontColor(new Color(100, 222, 207));
                    } else {//제목
                        XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                        XSLFTextRun run = paragraph.addNewTextRun();
                        run.setText(pptList[page_no][0]);
                        run.setBold(true);
                        run.setFontSize(18.0);
                        run.setFontColor(new Color(89, 89, 89));
                    }
                    i += 1;
                }
            }
        }
        //분문
        //마침
        originalSlide = originalPpt.getSlides().get(2);
        newSlide = newPpt.createSlide();
        newSlide.importContent(originalSlide);
        //마침
        try {
            FileOutputStream out = new FileOutputStream("ppt_test2.pptx");
            newPpt.write(out);
            out.close();
            System.out.println("PPT 파일이 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String pdfToText(String file_url) throws IOException {
        File file = new File(file_url);
        PDDocument document;
        document = PDDocument.load(file);
        PDFTextStripper s = new PDFTextStripper();
        String content = s.getText(document);
        document.close();
        return content;
    }

    public String wordToText(String file_url) throws IOException {
        String content = "";
        File file = new File(file_url);
        InputStream is = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(is);
        //List<XWPFParagraph> content = document.getParagraphs();
        for(XWPFParagraph paragraph: document.getParagraphs())
        {
            content = content + paragraph.getText();
        }
        document.close();
        return content;
    }

    public String pptToText(String file_url) throws IOException {
        String content = "";
        XMLSlideShow document = new XMLSlideShow(new FileInputStream(file_url));
        for(XSLFSlide slide: document.getSlides()){
            XSLFTextShape[] shapes = slide.getPlaceholders();
            for (XSLFTextShape textShape : shapes){
                content = content + textShape.getText();
            }
        }
        return content;
    }
    public String[] longTextSplit(String content, int divCnt)
    {
        int div = divCnt;
        String[] split;
        String[] word = content.split(" |\n|\t");
        int token = word.length;
        System.out.println(token);
        if (token < div)
        {
            String[] result = new String[1];
            result[0] = content;
            split = result;
        }
        else
        {
            int cnt = token / div;
            if (cnt % div != 0)
                cnt = cnt+1;
            String[] result = new String[cnt];
            if (cnt % div != 0)
                cnt = cnt-1;
            int i;
            for (i = 0;i<cnt;i++)
            {
                String temp[] = new String[div];
                for(int j  = i*div;j<(i+1)*div;j++)
                {
                    temp[j - (i*div)] = word[j];
                }
                result[i] = String.join(" ", temp);
            }
            if (cnt % div != 0)
            {
                String temp[] = new String[word.length - i*div];
                for(int j  = i*div;j< word.length;j++)
                {
                    temp[j - (i*div)] = word[j];
                }
                result[i] = String.join(" ", temp);
            }
            split = result;
        }
        //System.out.print(word.length);
        return split;
    }


//

//    public String gptTest(String text, String role)
//    {
//        List<ChatMessage> messages = new ArrayList<>();
//        ChatMessage message = new ChatMessage();
//        message.setRole(role);
//        message.setContent(text);
//        messages.add(message);
//        OpenAiService service = new OpenAiService("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",1800);
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
//                .messages(messages)
//                .model("gpt-3.5-turbo")
//                .build();
//        List<ChatCompletionChoice> answer = service.createChatCompletion(chatCompletionRequest).getChoices();
//        return answer.get(0).getMessage().getContent();
//        //service.createChatCompletion(chatCompletionRequest).getChoices().forEach(System.out::println);
//    }




//    public String generate_summary2(String[] text) throws Exception {
//        if (text.length == 1)
//            return chatGPT("summarize this :".concat(text[0]));
//        else
//        {
//            int len  = text.length;
//            String summary_result = "";
//            for (int i = 0;i<len;i++)
//            {
//                summary_result = summary_result.concat(chatGPT("summarize this in 100 words in korean:".concat(text[i])));
//                System.out.println(i);
//                System.out.println(summary_result);
//            }
//            return chatGPT("summarize this :".concat(summary_result));
//        }
//    }







   // public String sendQuestion(String text) {
//        OpenAiService service = new OpenAiService("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",1800);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(text)
//                .model("text-davinci-003")
//                .echo(false)
//                .maxTokens(2000)
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.0)
//                .build();
//        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
//        List answer;
//        answer = service.createCompletion(completionRequest).getChoices();
//        String response = answer.toString();
//        System.out.print(response);
//        return response;
//        //return service.createCompletion(completionRequest).getChoices().toString();
//        //getChoices().toString();
//    }
//    public String generate_summary(String text) {
//        OpenAiService service = new OpenAiService("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",1800);
//        String request = "summarize this in korea : ";
//        request.concat(text);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(text)
//                .model("text-davinci-003")
//                .echo(false)
//                .maxTokens(2000)
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.0)
//                .build();
//        List answer;
//        answer = service.createCompletion(completionRequest).getChoices();
//        String response = answer.toString();
//        //System.out.print(response);
//        return response;
//    }
//    public String generate_exercise(String text) {
//        OpenAiService service = new OpenAiService("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",1800);
//        String request = "Make 5 exercises about the text:";
//        request.concat(text);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(text)
//                .model("text-davinci-003")
//                .echo(false)
//                .maxTokens(2000)
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.0)
//                .build();
//        List answer;
//        answer = service.createCompletion(completionRequest).getChoices();
//        String response = answer.toString();
//        //System.out.print(response);
//        return response;
//    }
//
//    public String generate_translation(String text) {
//        OpenAiService service = new OpenAiService("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",1800);
//        String request = "Translate this in Korean:";
//        request.concat(text);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("user")
//                .model("text-davinci-003")
//                .echo(false)
//                .maxTokens(2000)
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.0)
//                .build();
//        List answer;
//        answer = service.createCompletion(completionRequest).getChoices();
//        String response = answer.toString();
//        //System.out.print(response);
//        return response;
//    }
//    public String chatGPT(String text) throws Exception {
//        String url = "https://api.openai.com/v1/completions";
//        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
//
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Authorization", "Bearer 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");
//
//        JSONObject data = new JSONObject();
//        data.put("model", "text-davinci-003");
//        data.put("prompt", text);
//        data.put("max_tokens", 2000);
//        data.put("temperature", 1.0);
//
//        con.setDoOutput(true);
//        con.getOutputStream().write(data.toString().getBytes());
//
//        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
//                .reduce((a, b) -> a + b).get();
//        //System.out.println(output);
//        String response = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
//        return response;
//        //System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
//    }

}

