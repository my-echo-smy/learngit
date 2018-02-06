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

    @Test
    public void cityTest() {
        CityCode cityCode = cityCodeService.getCityCode("云F");
        System.out.println("转换后的json数据为" + new Gson().toJson(cityCode));
    }

    /**
     * 交通违章查询-A
     */
    @Test
    public void cityTest1() {

        BufferedReader bufferedReader = null;
        BufferedWriter haveDataBuffer = null;
        BufferedWriter NoDataBuffer = null;
        BufferedWriter exceptionBuffer = null;
        BufferedWriter notSupportBuffer = null;
        String carNumber = null;
        String VIN = null;
        String carDriveNumber = null;
        try {
            //读取文件位置
            File file = new File("D:\\read.txt");
            //输出有结果文件位置
            File file1 = new File("D:\\haveData.txt");
            //输出无结果文件位置
            File file2 = new File("D:\\noData.txt");
            //异常数据位置
            File file3 = new File("D:\\exception.txt");
            //不支持位置
            File file4 = new File("D:\\notSupport.txt");

            FileReader reader = new FileReader(file);
            FileWriter haveWriter = new FileWriter(file1, true);
            FileWriter noDataWriter = new FileWriter(file2, true);
            FileWriter exceptionWriter = new FileWriter(file3, true);
            FileWriter notSupportWriter = new FileWriter(file4, true);

            bufferedReader = new BufferedReader(reader);
            haveDataBuffer = new BufferedWriter(haveWriter);
            NoDataBuffer = new BufferedWriter(noDataWriter);
            exceptionBuffer = new BufferedWriter(exceptionWriter);
            notSupportBuffer = new BufferedWriter(notSupportWriter);
            String str;
            int i = 0;
            while ((str = bufferedReader.readLine()) != null) {
                Thread.sleep(30);
                String[] a = str.split(",");
                //车牌号
                carNumber = a[0].trim();
                //车架号
                VIN = a[1].replace(" ", "");
                //发动机号
                carDriveNumber = a[2].replace(" ", "");
                String carCode;
                String areaCode;

                if (carNumber.startsWith("沪") || carNumber.startsWith("京") || carNumber.startsWith("津") || carNumber.startsWith("渝")) {
                    carCode = carNumber.substring(0, 1);
                } else {
                    carCode = carNumber.substring(0, 2);
                }

               CityCode cityCode = cityCodeService.getCityCode(carCode);
                if (cityCode!= null) {
                    areaCode = cityCode.getCityCode();
                    String url = URL + "account=tnw&accessToken=" + ACCESS_TOKEN + "&carNumber=" + carNumber + "&carCode=" + VIN + "&carDriveNumber=" + carDriveNumber + "&cityCode=" + areaCode;

                    String result = OkHttpUtils.get(url);
                    System.out.println("===========================" + result);
                    if (StringUtils.isNotBlank(result)) {
                        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                        if (jsonObject.has("data")) {
                            JsonObject data = jsonObject.getAsJsonObject("data");
                            if (data.get("hasData").toString().equals("\"true\"")) {
                                haveDataBuffer.write(carNumber + "###" + VIN + "###" + carDriveNumber + "###" + result + "\r\n");
                            } else {
                                NoDataBuffer.write(carNumber + "#" + VIN + "#" + carDriveNumber + "#" + result + "\r\n");
                            }
                        } else {
                            exceptionBuffer.write(carNumber + "#" + VIN + "#" + carDriveNumber + "\r\n");
                        }
                    } else {
                        exceptionBuffer.write(carNumber + "#" + VIN + "#" + carDriveNumber + "\r\n");
                    }
                } else {
                    notSupportBuffer.write(carNumber + "#" + VIN + "#" + carDriveNumber + "\r\n");
                }
                i++;
                if (i % 50 == 0) {
                    NoDataBuffer.flush();
                    haveDataBuffer.flush();
                    exceptionBuffer.flush();
                    notSupportBuffer.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exceptionBuffer.write(carNumber + "#" + VIN + "#" + carDriveNumber + "\r\n");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (haveDataBuffer != null) {
                    haveDataBuffer.close();
                }
                if (NoDataBuffer != null) {
                    NoDataBuffer.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (exceptionBuffer != null) {
                    exceptionBuffer.close();
                }
                if (notSupportBuffer != null) {
                    notSupportBuffer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
