package com.sakura.tm;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.dao.mapper.SmbmsUserGeneratorMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/17 17:08
 */
@Slf4j
@SpringBootTest
public class StartTest {

    @Autowired
    private SmbmsUserGeneratorMapper smbmsUserGeneratorMapper;

    @Test
    void t1() {
        List<SmbmsUser> list = smbmsUserGeneratorMapper.selectAll();
        System.out.println(JSON.toJSON(list));
    }
}
