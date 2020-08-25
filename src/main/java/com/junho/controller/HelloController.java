package com.junho.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/hello")
    public ResponseEntity<String> test() {
        // JWT Token이 제대로 인증되었을 경우 해당 URL로 접속하여 Hello~! 라는 문구를 볼 수 있다.
        return new ResponseEntity<String>("Hello~!", HttpStatus.OK);
    }
}
