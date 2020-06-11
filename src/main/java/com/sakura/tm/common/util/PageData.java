package com.sakura.tm.common.util;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.type.Alias;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李七夜
 */
@Alias("pd")
public class PageData extends ConcurrentHashMap implements Map {

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;

	public PageData(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap<>();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, StringEscapeUtils.unescapeHtml(value));//对html转义
		}
		//内置参数
//		for(EnumInterceptorDefineParams param:EnumInterceptorDefineParams.values()){
//			String ParamKey= param.getName();
//			String paramVal = (String)request.getAttribute(ParamKey);
//			if(!CommonUtils.isEmptyString(paramVal)){
//				returnMap.remove(ParamKey);
//				returnMap.put(ParamKey, paramVal);
//			}
//		}
		map = returnMap;
	}

	public PageData(HttpServletRequest request, boolean needHeaders) {
		String userid = request.getHeader("userid");
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap<>();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, StringEscapeUtils.unescapeHtml(value));
		}

		if (needHeaders) {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String s = headerNames.nextElement();
				String header = request.getHeader(s);
				returnMap.put(s, header);
			}
		}
		map = returnMap;
	}

	public PageData(HttpServletRequest request, String header) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap<>();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}

		String val = request.getHeader(header);
		returnMap.put(header, StringEscapeUtils.unescapeHtml(value));
		map = returnMap;
	}

	public PageData() {
		map = new HashMap();
	}

	public PageData(Map hashMap) {
		map = hashMap;
	}

	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(Object key) {
		return CommonsUtil.parseString(String.valueOf(get(key)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySets() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}

	/**
	 * 返回map
	 * @return
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * 将object对象转换成String
	 * @param key
	 * @return
	 */
	public String getStringVal(Object key) {
		return CommonsUtil.parseString(String.valueOf(get(key)));
	}

	public int getIntegerVal(Object key) {
		return CommonsUtil.parseInt(String.valueOf(get(key)));
	}

	public long getLongVal(Object key) {
		return CommonsUtil.parseLong(String.valueOf(get(key)));
	}

	public BigDecimal getBigDecimalVal(Object key) {
		return BigDecimal.valueOf(getDoubleVal(key));
	}

	public Double getDoubleVal(Object key) {
		return CommonsUtil.parseDouble(String.valueOf(get(key)), 0D);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Map mapResult = getMap();
		sb.append("{");
		for (Object key : mapResult.keySet()) {
			sb.append(key).append("=").append(mapResult.get(key)).append(",");
		}
		if (sb.indexOf("=") != -1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("}");
		return sb.toString();
	}
}
