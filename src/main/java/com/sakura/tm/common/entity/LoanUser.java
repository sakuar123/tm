package com.sakura.tm.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李七夜 on 2020/5/22 17:03
 * @author 李七夜
 */
@Data
@Builder
@TypeAlias("user")
@AllArgsConstructor
@NoArgsConstructor
public class LoanUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * user_id
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
	 * pwd_slt
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
	 * 性别，0:女，1:男
	 */
	private Integer sex;

	/**
	 * 用户状态，-1:禁用，1:启用
	 */
	private Integer state;

	/**
	 * 是否是实名认证，-1:未实名认证，1:已经实名认证
	 */
	private Integer isRealNameAuthentication;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * create_time
	 */
	private Date createTime;

	/**
	 * update_time
	 */
	private Date updateTime;

}
