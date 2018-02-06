package com.example.demo.dao;

import com.example.demo.model.CityCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 2018/2/2 0002.
 */
public interface CityCodeDao extends JpaRepository<CityCode, String> {
    @Override
    CityCode findOne(String cityCode);
}
