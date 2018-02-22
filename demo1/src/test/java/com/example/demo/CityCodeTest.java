package com.example.demo;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.demo.model.CityCode;
import com.example.demo.service.CityCodeService;
import com.example.demo.service.OkHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author XHL on 2018/1/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CityCodeTest {

    private static final String ACCOUNT = "tnw";
    private static final String ACCESS_TOKEN = " ";
    private static final String URL = "http://dev.tianxingshuke.com/api/rest/traffic/violationV2?";

    @Resource
    private CityCodeService cityCodeService;


}
