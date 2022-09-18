package com.example.vocaliz.verificationModule.controller;

import com.example.vocaliz.verificationModule.entity.*;
import com.example.vocaliz.verificationModule.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping(value = "/verification", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/randomPassword/{email}")
    public ResponseEntity<Object> randomPassword(@PathVariable (name = "email") String email) {
        Map<String,Object> res = new HashMap<>();
        mailService.randomPasswordMail(email);
        res.put("msg","Success");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody AuthRequest request) {
        Map<String,Object> res = new HashMap<>();
        if(mailService.resetPasswordMail(request)){
            res.put("msg","Success");
            return ResponseEntity.ok(res);
        }else{
            res.put("msg","Wrong password.");
            return ResponseEntity.status(403).body(res);
        }

    }

    @PostMapping("/resendCode/{email}")
    public ResponseEntity<Object>  resendCodeMail(@PathVariable (name = "email" ) String email) {
        Map<String,Object> res = new HashMap<>();
        mailService.resendCodeMail(email);
        res.put("msg","Success");
        return ResponseEntity.ok(res);
    }

}
