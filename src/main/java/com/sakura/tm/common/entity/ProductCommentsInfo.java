package com.sakura.tm.common.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductCommentsInfo implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private String commentsMesssage;

    private Integer startLevel;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ProductCommentsInfo(Integer id, Integer productId, Integer userId, String commentsMesssage, Integer startLevel, Date createTime, Date updateTime) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.commentsMesssage = commentsMesssage;
        this.startLevel = startLevel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProductCommentsInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentsMesssage() {
        return commentsMesssage;
    }

    public void setCommentsMesssage(String commentsMesssage) {
        this.commentsMesssage = commentsMesssage == null ? null : commentsMesssage.trim();
    }

    public Integer getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}