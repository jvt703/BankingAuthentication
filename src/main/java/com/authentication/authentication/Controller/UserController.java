package com.authentication.authentication.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from secured endpiont");

    }

    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloAdmin(){
        return ResponseEntity.ok("hello from secured endpiont admin");

    }

}
