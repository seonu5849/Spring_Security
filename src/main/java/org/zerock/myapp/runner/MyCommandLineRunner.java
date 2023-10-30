package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Log4j2
@NoArgsConstructor

@Order(1)
@Component
public class MyCommandLineRunner  implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.trace("run({}) invoked", Arrays.toString(args));
    }// run
} // end class
