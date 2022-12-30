package com.java.gmall.ums.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBoundVO implements Serializable{

    private Long userId;

    private Integer integration;

    private Integer growth;
}