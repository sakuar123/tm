package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.ProductCommentsInfo;
import com.sakura.tm.common.entity.ProductCommentsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCommentsInfoMapper {
    int countByExample(ProductCommentsInfoExample example);

    int deleteByExample(ProductCommentsInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCommentsInfo record);

    int insertSelective(ProductCommentsInfo record);

    List<ProductCommentsInfo> selectByExample(ProductCommentsInfoExample example);

    ProductCommentsInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductCommentsInfo record, @Param("example") ProductCommentsInfoExample example);

    int updateByExample(@Param("record") ProductCommentsInfo record, @Param("example") ProductCommentsInfoExample example);

    int updateByPrimaryKeySelective(ProductCommentsInfo record);

    int updateByPrimaryKey(ProductCommentsInfo record);
}