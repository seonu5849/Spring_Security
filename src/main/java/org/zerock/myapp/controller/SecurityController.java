package org.zerock.myapp.controller;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@NoArgsConstructor

@Controller
public class SecurityController {

    // https://localhost/member

    @GetMapping("/")
    String index(){
        log.trace("index () invoked.");
        return "index";
    } // index


    @GetMapping("/member")
    void member(){
        log.trace("member () invoked.");

    } // member

    @GetMapping("/manager")
    void manager(){
        log.trace("manager() invoked.");

    } // manager

    @GetMapping("/admin")
    void admin(){
        log.trace("admin() invoked.");

    } // admin


    @GetMapping("/login")
    void login(){
        log.trace("login() invoked.");

    } // login

    @GetMapping("/loginSuccess")
    void loginSuccess(){
        log.trace("loginSuccess() invoked.");

    } // loginSuccess

    @GetMapping("/accessDenied")
    void accessDenied(){
        log.trace("accessDenied() invoked.");

    } // accessDenied


} // end class
