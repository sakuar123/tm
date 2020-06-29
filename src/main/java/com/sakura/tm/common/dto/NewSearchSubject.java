package com.sakura.tm.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/22 16:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewSearchSubject {

	private Map<String,Object> directors;
	private String rate;
	private Integer cover_x;
	private String star;
	private String title;
	private String url;
	private Map<String,Object> casts;
	private String cover;
	private String id;
	private Integer cover_y;

}
