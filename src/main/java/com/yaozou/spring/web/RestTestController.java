package com.yaozou.spring.web;

import com.yaozou.spring.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class RestTestController {
    @Autowired
    private TestService testService;

}
