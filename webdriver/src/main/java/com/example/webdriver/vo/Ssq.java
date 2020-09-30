package com.example.webdriver.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Ssq {
    private String sheng;
    private String shi;
    private String qu;
    private String shiUrl;
    private String quUrl;
    private int year;
    private Map<String, String> price = new LinkedHashMap<>();

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
