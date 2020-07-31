package com.sakura.tm.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.tm.common.util.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Mybatis Generator on 2020/07/31
 */
@ApiModel(value = "com.sakura.tm.common.entity.SmbmsUser")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "smbms_user")
public class SmbmsUser implements Serializable {

    @ApiModelProperty(value = "id主键ID")
    private Long id;

    @ApiModelProperty(value = "usercode用户编码")
    private String usercode;

    @ApiModelProperty(value = "username用户名称")
    private String username;

    @ApiModelProperty(value = "userpassword用户密码")
    private String userpassword;

    @ApiModelProperty(value = "gender性别（1:女、 2:男）")
    private Integer gender;

    @ApiModelProperty(value = "birthday出生日期")
    @JsonFormat(pattern = DateUtils.DAY_DATE_PATTERN)
    private Date birthday;

    @ApiModelProperty(value = "phone手机")
    private String phone;

    @ApiModelProperty(value = "address地址")
    private String address;

    @ApiModelProperty(value = "userrole用户角色（取自角色表-角色id）")
    private Long userrole;

    @ApiModelProperty(value = "createdby创建者（userId）")
    private Long createdby;

    @ApiModelProperty(value = "creationdate创建时间")
    @JsonFormat(pattern = DateUtils.DAY_DATE_PATTERN)
    private Date creationdate;

    @ApiModelProperty(value = "modifyby更新者（userId）")
    private Long modifyby;

    @ApiModelProperty(value = "modifydate更新时间")
    @JsonFormat(pattern = DateUtils.DAY_DATE_PATTERN)
    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public enum Column {
        id("id", "id", "BIGINT", false),
        usercode("userCode", "usercode", "VARCHAR", false),
        username("userName", "username", "VARCHAR", false),
        userpassword("userPassword", "userpassword", "VARCHAR", false),
        gender("gender", "gender", "INTEGER", false),
        birthday("birthday", "birthday", "DATE", false),
        phone("phone", "phone", "VARCHAR", false),
        address("address", "address", "VARCHAR", false),
        userrole("userRole", "userrole", "BIGINT", false),
        createdby("createdBy", "createdby", "BIGINT", false),
        creationdate("creationDate", "creationdate", "TIMESTAMP", false),
        modifyby("modifyBy", "modifyby", "BIGINT", false),
        modifydate("modifyDate", "modifydate", "TIMESTAMP", false);

        private static final String BEGINNING_DELIMITER = "\"";

        private static final String ENDING_DELIMITER = "\"";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER)
                        .toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}