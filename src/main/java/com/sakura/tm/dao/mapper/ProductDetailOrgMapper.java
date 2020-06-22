package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.ProductDetailOrg;
import com.sakura.tm.common.entity.example.ProductDetailOrgExample;
import com.sakura.tm.dao.generator.BaseGeneratorMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* Created by Mybatis Generator on 2020/06/17
*/
@Mapper
public interface ProductDetailOrgMapper extends BaseGeneratorMapper<ProductDetailOrg> {
    long countByExample(ProductDetailOrgExample example);
}