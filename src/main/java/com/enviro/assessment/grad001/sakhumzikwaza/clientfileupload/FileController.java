package com.enviro.assessment.grad001.sakhumzikwaza.clientfileupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping("/")
    public String viewHomePage(){
        return "home";

    }
}
