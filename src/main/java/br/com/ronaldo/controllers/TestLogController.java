package br.com.ronaldo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/test")
    public String testLog() {

        logger.debug("Thist is a DEBUG log");
        logger.info("Thist is a INFO log");
        logger.warn("Thist is a WARN log");
        logger.error("Thist is a ERROR log");
        return "Logs generated successfully";
    }

}
