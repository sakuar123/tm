package com.sakura.tm.web.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/8/3 13:02
 */
@Data
public class SmbmsUserQuery extends BaseQuery {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("性别")
    private Integer region;
}
