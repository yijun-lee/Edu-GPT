package hello.hellospring.controller;

import hello.hellospring.dto.MessageDTO;
import hello.hellospring.service.GptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ajaxTestController {
    @GetMapping("/data")
    public String data() {
        return "ajax_test";
    }
    @RequestMapping(value = "/dataSend1",method = RequestMethod.POST)
    public String dataSend(Model model, MessageDTO dto){
        GptService gptService = new GptService();
        gptService.createGptService();
        String test = gptService.createChatCompletion(dto.getResult(),"user");
        model.addAttribute("msg",test);
        model.addAttribute("msg2",dto.getResult()+"/ this is the value sent by the server234");
        return "ajax_test :: #resultDiv";
    }

}
