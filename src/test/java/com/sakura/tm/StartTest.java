package com.sakura.tm;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.DateUtils;
import com.sakura.tm.dao.mapper.AmAdminGeneratorMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/17 17:08
 */
@Slf4j
@SpringBootTest
public class StartTest {

    @Autowired
    private AmAdminGeneratorMapper amAdminGeneratorMapper;

    public static void main(String[] args) {
        System.out.println(CommonsUtil.getLocalIP());
    }

    @Test
    void t1() {
    }

}
