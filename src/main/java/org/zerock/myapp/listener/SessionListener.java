package org.zerock.myapp.listener;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


//=================================================
// 1. For Spring Boot 2.7.x
//=================================================
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//import javax.servlet.annotation.WebListener;

//=================================================
// 2. For Spring Boot 3.1.x
//=================================================
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.annotation.WebListener;


@Log4j2

// Class contains `required fields`, You have to `force` NoArgsConstructor. (***)
@NoArgsConstructor

@WebListener
public final class SessionListener implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.trace("---------------------------------------");
        log.trace("* sessionCreated(se) invoked.");
        log.trace("---------------------------------------");

        HttpSession httpSession = se.getSession();
        log.info("\t+ session: {}", httpSession.getId());
    } // sessionCreated

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.trace("---------------------------------------");
        log.trace("* sessionDestroyed(se) invoked.");
        log.trace("---------------------------------------");

        HttpSession httpSession = se.getSession();
        log.info("\t+ session: {}", httpSession.getId());
    } // sessionDestroyed

} // end class
