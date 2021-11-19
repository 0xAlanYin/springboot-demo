package com.alan.dubbo.api.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Alan Yin
 * @date 2021/11/1
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    private int id;

    private String name;

    private Float amount;

}
