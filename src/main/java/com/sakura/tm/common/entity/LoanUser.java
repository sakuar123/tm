package com.sakura.tm.common.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * loan_user
 * @author 李七夜
 * @date 2020-05-28 17:36:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanUser implements Serializable {
    /**
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     */
    private String pwdSlt;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别,0:女,1:男
     */
    private Integer sex;

    /**
     * 用户状态,-1:禁用,1:启用
     */
    private Integer state;

    /**
     * 是否是实名认证,-1:未实名认证,1:已经实名认证
     */
    private Integer isRealNameAuthentication;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     */
    private Date createTime;

    /**
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}