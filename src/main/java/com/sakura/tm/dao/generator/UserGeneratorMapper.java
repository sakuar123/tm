package com.sakura.tm.dao.generator;

import com.sakura.tm.common.entity.LoanUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/5/28 16:51
 */
@Mapper
public interface UserGeneratorMapper extends BaseGeneratorMapper<LoanUser> {
}
