package com.sakura.tm;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.sakura.tm.common.entity.AmAdmin;
import com.sakura.tm.common.entity.example.AmAdminExample;
import com.sakura.tm.common.util.MD5Util;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.dao.mapper.AmAdminGeneratorMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TmApplicationTests {

    @Autowired
    private AmAdminGeneratorMapper amAdminGeneratorMapper;

    /**
     * 保存
     */
    @Test
    void insert() {
        AmAdmin amAdmin = AmAdmin
                .builder()
                .organizationId(1008611)
                .accountNumber("13005669999")
                .headImage("http://so.qqdna.com/templates/logo/logo2.gif")
                .adminPerson("张三")
                .phone("13005669999")
                .password(MD5Util.getMd5("123456", RandomStringUtils.randomAlphanumeric(5)))
                .email("lw38373238dangji@163.com")
                .roleId(1)
                .del("1")
                .createTime(new Date())
                .modifiedTime(new Date())
                .build();
        amAdminGeneratorMapper.insertSelective(amAdmin);
        //注意看这里,我并没有对id进行设值,取用的是MySQL维护的自增id,
        //所以在保存入库后Mybatis自动将数据中自增的Id映射到我传入的对象中
        //想要实现这一步,需要在实体的id上加入@GeneratedValue(strategy = GenerationType.IDENTITY)注解
        System.out.println(amAdmin.getId());
    }

    /**
     * 查询
     */
    @Test
    void select() {
        //带条件以及分页的查询,如果只是简单的查询可以使用selectByExample,然后使用PageHelper分页
        List<AmAdmin> amAdminList = amAdminGeneratorMapper.selectByExampleAndRowBounds(
                new AmAdminExample()
                        .or()
                        .andAccountNumberLike(StringUtils.join("%", "1300", "%"))
                        .andHeadImageEqualTo("http://so.qqdna.com/templates/logo/logo2.gif")
                        .example(), new RowBounds(1, 10)
        );
        System.out.println(JSON.toJSON(amAdminList));
        PageHelper.startPage(1, 10);
        amAdminList = amAdminGeneratorMapper.selectByExample(new AmAdminExample()
                .or()
                .andAccountNumberLike(StringUtils.join("%", "1300", "%"))
                .andHeadImageEqualTo("http://so.qqdna.com/templates/logo/logo2.gif")
                .example()
        );
        //分页数据使用PageResult出去,分页信息已经全部封装好了
        System.out.println(JSON.toJSON(PageResult.success(amAdminList)));
    }

	/**
	 * 更新
	 */
    @Test
    void update() {
        AmAdmin amAdmin = amAdminGeneratorMapper
                .selectOneByExample(new AmAdminExample()
                        .or()
                        .andIdEqualTo(1)
                        .example());
        amAdmin.setAccountNumber("13005779999");
        amAdmin.setAdminPerson("赵六");
        amAdmin.setHeadImage("https://csdnimg.cn/cdn/content-toolbar/csdn-logo.png?v=20200416.1");
        //第一个参数是需要更新的类,第二个参数是条件
        amAdminGeneratorMapper.updateByExampleSelective(amAdmin, new AmAdminExample()
                .or()
                .andIdEqualTo(1)
                .example());
        System.out.println(JSON.toJSON(amAdmin));
    }

	/**
	 * 删除
	 */
	@Test
    void delete() {
        AmAdmin amAdmin = AmAdmin
                .builder()
                .organizationId(1008611)
                .accountNumber("13005669999")
                .headImage("http://so.qqdna.com/templates/logo/logo2.gif")
                .adminPerson("张三")
                .phone("13005669999")
                .password(MD5Util.getMd5("123456", RandomStringUtils.randomAlphanumeric(5)))
                .email("lw38373238dangji@163.com")
                .roleId(1)
                .del("1")
                .createTime(new Date())
                .modifiedTime(new Date())
                .build();
        amAdminGeneratorMapper.insertSelective(amAdmin);
        System.out.println(JSON.toJSON(amAdmin));
        amAdminGeneratorMapper.deleteByExample(new AmAdminExample().or().andIdEqualTo(amAdmin.getId()).example());
    }

}
