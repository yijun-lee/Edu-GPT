package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.dto.MessageDTO;
import hello.hellospring.service.GptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Controller
public class GptController {
    GptService gptService = new GptService();
    String summary="OpenAI has developed GPT-4, a large-scale multimodal model that exhibits human-level performance on various professional and academic benchmarks. It can accept image and text inputs and produce text outputs, but has limitations and safety challenges that need to be studied. Although GPT-4 outperforms previous language models, it still has reliability issues and can make reasoning errors. The article emphasizes the importance of careful usage guidelines and safety techniques. OpenAI is collaborating with external researchers to assess potential impacts and build evaluations for dangerous capabilities. GPT-4 represents a significant step towards safely deploying broadly useful AI systems, but continued work on safety and alignment is necessary.";
    String quest;

    String quiz[][];
    int quiz_cnt;
    int quiz_type;
    @GetMapping("/gpt")
    public String gpt() {
        gptService.createGptService();
        return "gpt";
    }
    @ResponseBody
    @RequestMapping(value = "/summary",method = RequestMethod.POST)
    public HashMap<String, Object> summaryDoc(@RequestBody HashMap<String, Object> map) throws Exception{
        gptService.initiateChatCompletion();
        String text = gptService.pdfToText(map.get("file_path").toString());
        String[] origin_text = gptService.longTextSplit(text,2000);
        summary = gptService.generateSummary(origin_text);
        System.out.println(summary);
        map.put("summary", summary);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/quest",method = RequestMethod.POST)
    public HashMap<String, Object> chatQna(@RequestBody HashMap<String, Object> map){
        System.out.println(map.get("quest"));
        String reponse = gptService.createChatCompletion(map.get("quest").toString(),"user");
        System.out.print(reponse);
        map.put("response",reponse);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/exercise",method = RequestMethod.POST)
    public HashMap<String, Object> makeQuiz(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        String[] text = new String[1];
        text[0] = summary;
        quiz = gptService.makeQuiz(text, Integer.parseInt(map.get("quiz_cnt").toString()),map.get("difficulty").toString(),Integer.parseInt(map.get("quiz_type").toString()));
        quiz_cnt = Integer.parseInt(map.get("quiz_cnt").toString());
        quiz_type = Integer.parseInt(map.get("quiz_type").toString());
        System.out.println(quiz);
        map = gptService.listToJson(map, quiz, Integer.parseInt(map.get("quiz_cnt").toString()), Integer.parseInt(map.get("quiz_type").toString()));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/scoring",method = RequestMethod.POST)
    public HashMap<String, Object> scoreQuiz(@RequestBody HashMap<String, Object> map) {
        System.out.println(map);
        ArrayList<?> ans_data = (ArrayList<?>) map.get("ans_data");
        LinkedHashMap ans_list;
        String score = "";
        boolean is_correct;
        for (int i=0;i<quiz_cnt;i++)
        {
            ans_list = (LinkedHashMap) ans_data.get(i);
            is_correct = gptService.score_ans(quiz,i,ans_list.get("answer").toString(),quiz_type);
            if (is_correct) score = score + Integer.toString(i+1) + "번: 정답 ";
            else score = score + Integer.toString(i+1) + "번: 오답 ";
        }
        map.clear();
        map.put("score", score);
        System.out.println(map);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/makePpt",method = RequestMethod.POST)
    public HashMap<String, Object> makePPT(@RequestBody HashMap<String, Object> map) throws IOException {
        System.out.println(map);
        String[] info = new String[3];
        info[0] = map.get("head").toString();
        info[1] = map.get("subHead").toString();
        info[2] = map.get("presenter").toString();

        gptService.makePPT(Integer.parseInt(map.get("pageCnt").toString()), map.get("target").toString(), info, summary);
        return map;
    }

//    @PostMapping("/gpt/quest")
//    public String createQuestion(GptForm form, Model model) throws Exception {
//        quest = form.getQuest();
//        System.out.print(quest);
//        test = gptService.createChatCompletion(quest,"user");
//        System.out.print(test);
////        model.addAttribute("answer", test);
//        model.addAttribute("request", "아이폰");
//        //form.setanswer(gptService.chatGPT(quest));
//        //form.setanswer(gptService.sendQuestion(quest));
//        return "redirect:/gpt_answer";
//    }
//    @GetMapping(value = "/gpt_answer")
//    public String home_login(Model model) {
//        model.addAttribute("answer", test);
//        model.addAttribute("request", quest);
//        return "/gpt";
//    }
}
