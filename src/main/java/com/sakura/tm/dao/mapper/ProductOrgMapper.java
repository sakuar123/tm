package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.ProductOrg;
import com.sakura.tm.common.entity.example.ProductOrgExample;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.generator.BaseGeneratorMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductOrgMapper extends BaseGeneratorMapper<ProductOrg> {
    long countByExample(ProductOrgExample example);

    List<PageData> query();
}