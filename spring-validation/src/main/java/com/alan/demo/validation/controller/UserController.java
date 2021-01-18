package com.alan.demo.validation.controller;

import com.alan.demo.validation.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Alan Yin
 * @date 2020/12/29
 */
@RestController
@RequestMapping("/webapi")
@Slf4j
public class UserController {

    @GetMapping("/save")
    public String save(@Valid UserDO userDO, BindingResult bindingResult) {
        String errorMsg = validateParam(bindingResult);
        if (StringUtils.hasText(errorMsg)) {
            return errorMsg;
        }
        log.info("user:{}", userDO);
        return "success";
    }

    private String validateParam(BindingResult bindingResult) {
        String errorMsg = "";
        boolean hasErrors = bindingResult.hasErrors();
        if (hasErrors) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errorMsg += objectError.getDefaultMessage() + ";";
            }
        }
        return errorMsg;
    }
}
