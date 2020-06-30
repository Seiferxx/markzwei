package com.seiferson.markzwei.controller;

import com.seiferson.markzwei.MarkzweiProcessor;
import com.seiferson.markzwei.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @PostMapping("/api/v1/processmarkzweitext")
    public @ResponseBody ResponseEntity<String> processMarkzweiText(@RequestBody Request request) {
        return ResponseEntity.ok(MarkzweiProcessor.processText(request.getText()));
    }
}
