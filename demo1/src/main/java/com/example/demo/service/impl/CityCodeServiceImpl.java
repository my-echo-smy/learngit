package com.example.demo.service.impl;


import com.example.demo.dao.CityCodeDao;
import com.example.demo.model.CityCode;
import com.example.demo.service.CityCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author XHL on 2018/1/30
 */
@Service
public class CityCodeServiceImpl implements CityCodeService {

    @Resource
    private CityCodeDao cityCodeDao;

    @Override
    public CityCode getCityCode(String cityCode) {
        return cityCodeDao.findOne(cityCode);
    }
}
