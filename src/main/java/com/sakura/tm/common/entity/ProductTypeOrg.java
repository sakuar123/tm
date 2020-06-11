package com.sakura.tm.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品类型表
 * product_type_org
 * @author 李七夜
 * @date 2020-06-11 14:46:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_type_org")
public class ProductTypeOrg implements Serializable {
    /**
     */
    private Integer id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 父类id
     */
    private Integer parentId;

    /**
     */
    private Date createTime;

    /**
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table product_type_org
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        name("name"),
        type("type"),
        parentId("parent_id"),
        createTime("create_time"),
        updateTime("update_time");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table product_type_org
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}