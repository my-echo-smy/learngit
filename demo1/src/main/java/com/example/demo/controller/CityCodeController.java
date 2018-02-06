package com.example.demo.controller;


import com.example.demo.model.CityCode;
import com.example.demo.service.CityCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author XHL on 2018/1/30
 */
@RestController
public class CityCodeController {

    @Resource
    private CityCodeService cityCodeService;


    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public CityCode test() {
        return cityCodeService.getCityCode("云F");
    }
}
