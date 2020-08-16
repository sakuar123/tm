package com.sakura.tm.web.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 李七夜 on 2020/5/20 13:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("父类query对象")
public class BaseQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
