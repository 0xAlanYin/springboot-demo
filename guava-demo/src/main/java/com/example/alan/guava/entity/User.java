package com.example.alan.guava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Alan Yin
 * @date 2021/7/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User implements Serializable {

    private Long id;

    private String name;

}
