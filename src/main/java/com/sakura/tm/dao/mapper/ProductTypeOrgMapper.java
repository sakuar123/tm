package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.ProductTypeOrg;
import com.sakura.tm.common.entity.example.ProductTypeOrgExample;
import com.sakura.tm.dao.generator.BaseGeneratorMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductTypeOrgMapper extends BaseGeneratorMapper<ProductTypeOrg> {
    long countByExample(ProductTypeOrgExample example);
}