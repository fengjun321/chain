package org.sparkera.nft.controller;


import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 */
@RestController
@RequestMapping("/health/")
@Slf4j
public class HealthCheckController {

    // 默认状态码为200
    private static final  int HEALTH_CHECK_STATUS_CODE200 = 200;
    private static final  int    HEALTH_CHECK_STATUS_CODE403 = 403;

    public static volatile int  STATUS_CODE= 200;

   
    /**
     * 查看应用状态
     */
    @RequestMapping("/status")
    public void status(HttpServletResponse response) {
        response.setStatus(STATUS_CODE);
        return;
    }
 
 }

