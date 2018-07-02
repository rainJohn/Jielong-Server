package com.jielong.core.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QRcodeServiceTest {

    @Autowired
    QRcodeService qRcodeService;

    @Test
    public  void  testImage(){
        String path="pages/index/index?parentUserId=42";
        File imageFile=qRcodeService.getQRcodeImage(path);
        Assert.assertNotNull(imageFile);
    }


}
