package com.sakura.tm.common.util;

import java.util.List;

import com.sakura.tm.common.emnu.CommonsCodeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 李七夜 on 2020/5/20 13:51
 *
 * @author 李七夜
 */
@Data
public class PageResult<T> {

    private Integer code;

    private String message;

    private Page<T> page;

    public static <T> PageResult<T> success(List<T> list) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(CommonsCodeEnum.success.getCode());
        pageResult.setMessage(CommonsCodeEnum.success.getName());
        pageResult.setPage(Page.converter((com.github.pagehelper.Page<T>) list));
        return pageResult;
    }

    @Data
    public static class Page<T> {

        /** 当前页 */
        @ApiModelProperty(value = "当前页", required = true)
        private int pageNum;
        /** 每页的数量 */
        @ApiModelProperty(value = "每页的数量", required = true)
        private int pageSize;
        /** 总条数 */
        @ApiModelProperty(value = "总条数", required = true)
        private long total;
        /** 总页数 */
        @ApiModelProperty(value = "总页数", required = true)
        private int totalPages;
        /** 结果集 */
        @ApiModelProperty(value = "结果集", required = true)
        private List<T> result;
        /** 拓展参数 */
        @ApiModelProperty(value = "拓展参数")
        private Object expand;

        static <T> Page<T> converter(com.github.pagehelper.Page<T> page) {
            if (page == null) {
                return null;
            }
            Page<T> p = new Page<>();
            p.setPageNum(page.getPageNum());
            p.setPageSize(page.getPageSize());
            p.setTotal(page.getTotal());
            p.setTotalPages(page.getPages());
            p.setResult(page.getResult());
            return p;
        }
    }
}
