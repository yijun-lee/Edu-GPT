package hello.hellospring.service;

import org.junit.jupiter.api.Test;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GptServiceTest {
    GptService gptService;
    public String text;


//    @Test
//    public void pdfTest() throws Exception {
//        gptService = new GptService();
//        text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/영상처리/11_Gamma.pdf");
//        System.out.print(text);
//        System.out.print(gptService.gptTest("이 글 좀 한글로 설명해줘 :".concat(text),"user"));
//        //System.out.print(gptService.generate_summary(text));
//        //System.out.print(gptService.generate_exercise(text));
//    }
//    @Test
//    public void gptTest() throws Exception {
//        gptService = new GptService();
//        System.out.print(gptService.gptTest("내 이름은 구민이야","user"));
//        //System.out.print(gptService.chatGPT("아이폰의 역사에 대해 알려줘"));
//
//    }

    //    @Test
//    public void longTextTest() throws Exception {
//        gptService = new GptService();
//        String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/2학년 2학기/확률통계 및 프로그래밍/강의자료/4주차 전체 수업.pdf");
//        //System.out.println(text);
//        String[] split;
//        split = gptService.longTextSplit(text,1500);
//        System.out.println(split[0]);
//        System.out.print(gptService.gptTest("이 글에 대해 한글로 설명해줘:".concat(split[0]),"user"));
//        //System.out.print(gptService.chatGPT("summarize this :".concat(split[0])));
//
//        //System.out.println(gptService.generate_summary2(split));
//    }
    @Test
    public void gpt_initiate_test() {
        gptService = new GptService();
        gptService.createGptService();
        System.out.println(gptService.createChatCompletion("영어로 번역해줘 : 나는 오늘 학교에 간다.", "user"));
        System.out.println(gptService.createChatCompletion("위 글 내용이 뭐였지?", "user"));
        gptService.initiateChatCompletion();
        System.out.println(gptService.createChatCompletion("위 글 내용이 뭐였지?", "user"));
    }

    @Test
    public void summary_test() throws IOException {
        gptService = new GptService();
        gptService.createGptService();
        //String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/2학년 2학기/확률통계 및 프로그래밍/강의자료/4주차 전체 수업.pdf");
        //String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/컴퓨터 네트워크/Chapter_3_v8.02.pdf");
        String text = gptService.pdfToText("/Users/koomin/구민/gpt-4-1-14.pdf");
        //System.out.println(text);
        String[] split;
        split = gptService.longTextSplit(text, 2000);
        System.out.println(split[0]);
        System.out.println(gptService.generateSummary(split));
        //System.out.println(gptService.createChatCompletion("summarize this".concat(split[0]),"user"));

    }

    @Test
    public void exercise_test() throws IOException {
        String[] quiz = new String[1];
        gptService = new GptService();
        gptService.createGptService();
        String text = gptService.pdfToText("/Users/koomin/구민/gpt-4-1-14.pdf");
        //String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/컴퓨터 네트워크/Chapter_3_v8.02.pdf");
        String[] split = new String[1];
//        split = gptService.longTextSplit(text, 2000);
        split[0] = "OpenAI has developed GPT-4, a large-scale multimodal model that can accept image and text inputs and produce text outputs. It exhibits human-level performance on various professional and academic benchmarks, including passing a simulated bar exam with a score around the top 10% of test takers. GPT-4 is a Transformer-based model pre-trained to predict the next token in a document. The report also discusses the challenge of developing deep learning infrastructure and optimizing methods that behave predictably across a wide range of scales. GPT-4 has similar limitations to earlier GPT models and can suffer from hallucinations. Care should be taken while using the outputs of GPT-4, particularly in contexts where reliability is important.GPT-4's performance on academic and professional exams, as well as its language modeling capabilities. GPT-4 exhibits human-level performance on most of the exams tested and outperforms previous language models on a variety of benchmarks. The model also accepts prompts consisting of both text and images and has similar capabilities as it does on text-only inputs. Preliminary results on academic vision benchmarks are also discussed in the article.Despite its capabilities, GPT-4 still has limitations and is not fully reliable. Language model outputs should be used with great care, especially in high-stakes contexts. GPT-4 significantly reduces hallucinations compared to previous models, but can still make reasoning errors and lack knowledge of recent events. Adversarial testing and red-teaming with domain experts have been used to improve the safety of GPT-4, along with a model-assisted safety pipeline and improvements on safety metrics. However, it is still possible to elicit bad behavior.The article discusses the potential benefits and risks of GPT-4 and its impact on society. The authors acknowledge the limitations and propose safety measures such as monitoring for abuse and iterative model improvement. They also collaborate with external researchers to better understand the potential impacts and build evaluations for dangerous capabilities. The article concludes by highlighting GPT-4's improved capabilities and its potential for broad use and safe deployment, but also emphasizes the need for continued work on safety and alignment. The authors will soon publish recommendations on preparing for AI's effects and projecting its economic impacts.";
        quiz[0] = gptService.generateExercise(split, 3, "어려움", 2);
        System.out.println(quiz[0]);
        System.out.println("100001");
//        System.out.println(gptService.generateTranslation(quiz,"english", "korean"));
//        System.out.println(gptService.generateExercise(split, 3, "어려움", "4지선다"));
    }

    @Test
    public void conquiz_test() {
        String quiz_text = "1. GPT-4와 같은 대규모 멀티모달 모델 개발의 주요 목표는 무엇인가요?\n" +
                "a. 자연어 텍스트 이해와 생성 능력 개선\n" +
                "b. 이미지와 텍스트 입력을 처리하고 텍스트 출력물을 생성하는 것\n" +
                "c. 다양한 전문가 및 학술 기준에서 인간 시험 응시자들보다 높은 점수 달성\n" +
                "d. 이전 모델보다 더 크고 강력한 모델 개발\n" +
                "\n" +
                "답: a. 자연어 텍스트 이해와 생성 능력 개선\n" +
                "\n" +
                "2. HumanEval 데이터셋이란 무엇인가요?\n" +
                "a. 다양한 복잡도의 Python 함수 합성 능력을 측정하는 데이터셋\n" +
                "b. 57개 주제를 다루는 전통적인 NLP 벤치마크 스위트\n" +
                "c. 영어 다중 선택 문항 세트\n" +
                "d. 인간을 위해 원래 설계된 시험 모음\n" +
                "\n" +
                "답: a. 다양한 복잡도의 Python 함수 합성 능력을 측정하는 데이터셋\n" +
                "\n" +
                "3. GPT-4의 한계는 무엇인가요?\n" +
                "a. 완벽하게 신뢰성이 있어 \"환각\"에 걸리지 않는다.\n" +
                "b. 무제한 컨텍스트 창이 있다.\n" +
                "c. 경험으로 배운다.\n" +
                "d. \"환각\"을 만들어내며 컨텍스트 창이 제한적이다.\n" +
                "\n" +
                "답: d. \"환각\"을 만들어내며 컨텍스트 창이 제한적이다.";
//        String quiz_text = "1. GPT-4 is a model that can only accept text inputs. \n" +
//                "답: False\n" +
//                "\n" +
//                "2. GPT-4 has no limitations and is fully reliable. \n" +
//                "답: False\n" +
//                "\n" +
//                "3. The authors of the article only discuss the benefits of GPT-4 and do not acknowledge any risks or limitations. \n" +
//                "답: False";
        gptService = new GptService();
        String[][] test = gptService.convertToQuiz(quiz_text, 3, 0);
    }

    @Test
    public void quiz_test() throws IOException {
        gptService = new GptService();
        gptService.createGptService();
        String text = gptService.pdfToText("/Users/koomin/구민/gpt-4-1-14.pdf");
        gptService.makeQuiz(gptService.longTextSplit(text, 2000), 3, "어려움", 2);
    }

    @Test
    public void translation_test() throws IOException {
        gptService = new GptService();
        gptService.createGptService();
        String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/2학년 2학기/확률통계 및 프로그래밍/강의자료/4주차 전체 수업.pdf");
        //String text = gptService.pdfToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/컴퓨터 네트워크/Chapter_3_v8.02.pdf");
        String[] split;
        split = gptService.longTextSplit(text, 2000);
        System.out.println(gptService.generateTranslation(split, "English", "korean"));
    }

    @Test
    public void word_test() throws IOException {
        gptService = new GptService();
        System.out.println(gptService.wordToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/컴퓨터 구조 및 운영체제/19011799_구민_과제1.docx"));
    }

    @Test
    public void ppt_test() throws IOException {
        gptService = new GptService();
        System.out.println(gptService.pptToText("/Users/koomin/Library/Mobile Documents/com~apple~CloudDocs/지능기전/3학년 1학기/컴퓨터 네트워크/Chapter_3_v8.02.pptx"));
    }

    @Test
    public void create_ppt_test1() {
        XMLSlideShow ppt = new XMLSlideShow();

        // 슬라이드 생성
        XSLFSlide slide = ppt.createSlide();

        // 제목 추가
        XSLFTextBox title = slide.createTextBox();
//        title.setText("CPU 작동 원리에 대한 소개");
        title.setAnchor(new java.awt.Rectangle(50, 50, 600, 50));
        XSLFTextParagraph paragraph = title.getTextParagraphs().get(0);

        // 텍스트 런 가져오기
        XSLFTextRun run = paragraph.addNewTextRun();
        run.setText("명령어의 실행 과정");

        // 폰트 크기 설정
        run.setFontSize(44.0); // 폰트 크기 18pt로 설정

        // 내용 추가
        XSLFTextBox content = slide.createTextBox();
//        content.setText("내용\n내용2\n내용3");
        content.setAnchor(new java.awt.Rectangle(50, 120, 600, 300));
        XSLFTextParagraph paragraph1 = content.getTextParagraphs().get(0);

        // 텍스트 런 가져오기
        XSLFTextRun run1 = paragraph1.addNewTextRun();
        run1.setText("CPU는 명령어를 실행하기 위해 Fetch, Decode, Execute의 세 단계로 나뉘어 작동합니다. \nFetch 단계에서는 명령어를 주기억장치에서 가져오고, Decode 단계에서는 명령어를 해석합니다. \nExecute 단계에서는 명령어에 따라 데이터를 처리하고 결과를 저장합니다.");
        paragraph1.setBullet(true);
        // 폰트 크기 설정
        run1.setFontSize(30.0); // 폰트 크기 18pt로 설정
//        run1.
        // PPT 파일 저장
        try {
            FileOutputStream out = new FileOutputStream("ppt_test.pptx");
            ppt.write(out);
            out.close();
            System.out.println("PPT 파일이 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void new_create_ppt_test() throws IOException {
        int i =0;
        XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream("/Users/koomin/구민/ppt_template.pptx"));
        XSLFSlide originalSlide = originalPpt.getSlides().get(0);

        XMLSlideShow newPpt = new XMLSlideShow();
        XSLFSlide newSlide = newPpt.createSlide();
        newSlide.importContent(originalSlide);
        for (XSLFShape shape : newSlide.getShapes()) {
            if (shape instanceof XSLFTextBox) {
                XSLFTextBox textBox = (XSLFTextBox) shape;
                System.out.println(textBox.getText());
                textBox.setText("");
                if (i == 0) {
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText("시발");
                    run.setFontSize(28.0);
                    paragraph.setBullet(true);
                }
                else if(i == 1){
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText("01");
                    run.setBold(true);
                    run.setFontSize(48.0);
                    run.setFontColor(new Color(100,222,207));
                }
                else{
                    XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
                    XSLFTextRun run = paragraph.addNewTextRun();
                    run.setText("시발");
                    run.setBold(true);
                    run.setFontSize(18.0);
                    run.setFontColor(new Color(89,89,89));
                }

                i+=1;
//                String text = textBox.getText(); // 텍스트 상자의 텍스트 내용 가져오기
//                System.out.println(textBox.getText());
//                textBox.setText("시발");
            }
        }
        try {
            FileOutputStream out = new FileOutputStream("ppt_test2.pptx");
            newPpt.write(out);
            out.close();
            System.out.println("PPT 파일이 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void create_ppt_test() throws IOException {
//        gptService = new GptService();
//        gptService.create_ppt(5);
//    }
    @Test
    public void makePptGptResponse() throws IOException {
        gptService = new GptService();
        gptService.createGptService();
        String summary="OpenAI has developed GPT-4, a large-scale multimodal model that exhibits human-level performance on various professional and academic benchmarks. It can accept image and text inputs and produce text outputs, but has limitations and safety challenges that need to be studied. Although GPT-4 outperforms previous language models, it still has reliability issues and can make reasoning errors. The article emphasizes the importance of careful usage guidelines and safety techniques. OpenAI is collaborating with external researchers to assess potential impacts and build evaluations for dangerous capabilities. GPT-4 represents a significant step towards safely deploying broadly useful AI systems, but continued work on safety and alignment is necessary.";
        String gptResponse = gptService.makePptGptResponse(summary, 6, "고등학생");
        System.out.println(gptResponse);
        String[][] pptList = gptService.pptTextToList(6,gptResponse);
        String[] info = new String[3];
        info[0] ="GPT-4";
        info[1] ="GPT4를 알아보자";
        info[2] = "구민";
        gptService.create_ppt(6,pptList,info);
    }
}