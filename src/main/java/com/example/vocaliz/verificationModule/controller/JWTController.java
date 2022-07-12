package com.example.vocaliz.verificationModule.controller;

import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.userModule.service.*;
import com.example.vocaliz.verificationModule.entity.*;
import com.example.vocaliz.verificationModule.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping(value = "/verification",produces = MediaType.APPLICATION_JSON_VALUE)
public class JWTController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    @Lazy
    private AppUserService appUserService;

    @Operation(summary = "sign up", description = "註冊body裡，只需填email, name, password")
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody AppUser request){
        Map<String,Object> res = new HashMap<>();

        if(jwtService.signUp(request)){
            res.put("msg","Success");
            return ResponseEntity.status(201).body(res);
        }else{
            res.put("msg","Has the same email registered!");
            return ResponseEntity.status(409).body(res);
        }
    }

    @Operation(summary = "驗證", description = "")
    @PutMapping("/verify/{email}/{code}")
    public ResponseEntity<Object> verify(@PathVariable String email,@PathVariable String code){
        Map<String,Object> res = new HashMap<>();

        if(jwtService.verifyCode(email,code)){
            res.put("msg","Success");
            return ResponseEntity.ok(res);
        }else{
            res.put("msg","Verification code error.");
            return ResponseEntity.status(418).body(res);
        }
    }

    @Operation(summary = "登入", description = "")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> issueToken(@Valid @RequestBody AuthRequest request) {
        String token = jwtService.generateToken(request);
        Map<String, Object> response = new HashMap<>(Collections.singletonMap("token", token));

        Map<String, Object> user = jwtService.parseToken(response.get("token").toString());
        String userEmail = user.get("email").toString();
        response.put("activate",appUserService.getUserByEmail(userEmail).getIsActivate());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "前端用不到", description = "")
    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);

        return ResponseEntity.ok(response);
    }
}
