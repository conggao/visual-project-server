package com.gc.vp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.vp.VisualProjectApplication;
import com.gc.vp.entity.vo.code.ComTreeReq;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VisualProjectApplication.class)
@ActiveProfiles("dev")
class GenCodeServiceTest {
    @Autowired
    private GenCodeService genCodeService;

    @Test
    void genCode() throws JsonProcessingException {
        String reqStr = "[ { \"parentId\": 0, \"id\": 1, \"type\": \"tag\", \"comName\": \"container\", \"childrens\": [ { \"parentId\": 1, \"id\": 2, \"type\": \"tag\", \"comName\": \"button\", \"childrens\": [] }, { \"parentId\": \"1\", \"id\": 3, \"type\": \"tag\", \"comName\": \"button\", \"childrens\": [] } ] } ]";
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<ComTreeReq> coms = objectMapper.readValue(reqStr, ArrayList.class);
        String code = genCodeService.genCode(coms);
        System.out.println(code);
    }
}