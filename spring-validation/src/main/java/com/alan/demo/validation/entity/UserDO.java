package com.alan.demo.validation.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Alan Yin
 * @date 2020/12/29
 */
@Data
public class UserDO {

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Pattern(regexp = "^[0-9]{1,2}$", message = "年龄必须是整数")
    private Integer age;

    @NotBlank(message = "邮箱不能为空")
    private String email;
}

