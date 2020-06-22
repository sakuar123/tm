package com.sakura.tm.dao1.tm;

import com.sakura.tm.common.util.PageData;
import com.sakura.tm.config.dao.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/12 17:43
 */
@Mapper
public interface TmTestMapper {

	@Select("select * from user_digita_org ")
	@DataSource("master")
	List<PageData> query();
}
