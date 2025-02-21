package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller    //컨트롤러임을 선언하는 어노테이션


public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username","김준수");    //username이라는 변수 설정

        return "greetings";     //파일이름. 서버가 알아서 templates 디렉토리에서 greeting.mustache 파일을 찾아 웹 브라우저로 전송함
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","rlawnstn");
        return "goodbye";
    }

}
