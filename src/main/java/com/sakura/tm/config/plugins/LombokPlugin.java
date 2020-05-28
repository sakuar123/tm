package com.sakura.tm.config.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.*;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/5/27 14:30
 */
public class LombokPlugin extends PluginAdapter {

	private final Collection<Annotations> annotations;

	public LombokPlugin() {
		annotations = new LinkedHashSet<>(Annotations.values().length);
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}


	@Override
	public boolean modelBaseRecordClassGenerated(
			TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable
	) {
		addAnnotations(topLevelClass);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(
			TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable
	) {
		addAnnotations(topLevelClass);
		return true;
	}


	@Override
	public boolean modelRecordWithBLOBsClassGenerated(
			TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable
	) {
		addAnnotations(topLevelClass);
		return true;
	}

	@Override
	public boolean modelGetterMethodGenerated(
			Method method,
			TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable,
			ModelClassType modelClassType
	) {
		return false;
	}

	@Override
	public boolean modelSetterMethodGenerated(
			Method method,
			TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable,
			ModelClassType modelClassType
	) {
		return false;
	}

	/**
	 * 给目标类添加需要的注解
	 * @param topLevelClass the partially implemented model class
	 */
	private void addAnnotations(TopLevelClass topLevelClass) {
		if (annotations.contains(Annotations.HASLOMBOK)) {
			for (Annotations annotation : annotations) {
				topLevelClass.addImportedType(annotation.javaType);
				topLevelClass.addAnnotation(annotation.asAnnotation());
			}
		}
	}

	/**
	 * 处理定义的属性
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		Enumeration<?> enumeration = properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String annotationName = enumeration.nextElement().toString();
			if (annotationName.contains(".")) {
				continue;
			}
			String value = properties.getProperty(annotationName);
			if (!Boolean.parseBoolean(value)) {
				continue;
			}
			Annotations annotation = Annotations.getValueOf(annotationName);
			if (annotation == null) {
				continue;
			}
			String optionsPrefix = annotationName + ".";
			Enumeration<?> propertyNames = properties.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement().toString();
				if (!propertyName.startsWith(optionsPrefix)) {
					continue;
				}
				String propertyValue = properties.getProperty(propertyName);
				annotation.appendOptions(propertyName, propertyValue);
			}
			this.annotations.add(annotation);
			this.annotations.addAll(Annotations.getDependencies(annotation));
		}
	}

	private enum Annotations {
		HASLOMBOK("hasLombok", "@Data", "lombok.Data"),
		BUILDER("builder", "@Builder", "lombok.Builder"),
		ALL_ARGS_CONSTRUCTOR("allArgsConstructor", "@AllArgsConstructor", "lombok.AllArgsConstructor"),
		NO_ARGS_CONSTRUCTOR("noArgsConstructor", "@NoArgsConstructor", "lombok.NoArgsConstructor"),
		ACCESSORS("accessors", "@Accessors", "lombok.experimental.Accessors"),
		TO_STRING("toString", "@ToString", "lombok.ToString");


		private final String paramName;
		private final String name;
		private final FullyQualifiedJavaType javaType;
		private final List<String> options;


		Annotations(String paramName, String name, String className) {
			this.paramName = paramName;
			this.name = name;
			this.javaType = new FullyQualifiedJavaType(className);
			this.options = new ArrayList<String>();
		}

		private static Annotations getValueOf(String paramName) {
			for (Annotations annotation : Annotations.values())
				if (String.CASE_INSENSITIVE_ORDER.compare(paramName, annotation.paramName) == 0)
					return annotation;

			return null;
		}

		private static Collection<Annotations> getDependencies(Annotations annotation) {
			if (annotation == ALL_ARGS_CONSTRUCTOR)
				return Collections.singleton(NO_ARGS_CONSTRUCTOR);
			else
				return Collections.emptyList();
		}

		private static String quote(String value) {
			if (Boolean.TRUE.toString().equals(value) || Boolean.FALSE.toString().equals(value))
				return value;
			return value.replaceAll("[\\w]+", "\"$0\"");
		}

		private void appendOptions(String key, String value) {
			String keyPart = key.substring(key.indexOf(".") + 1);
			String valuePart = value.contains(",") ? String.format("{%s}", value) : value;
			this.options.add(String.format("%s=%s", keyPart, quote(valuePart)));
		}

		private String asAnnotation() {
			if (options.isEmpty()) {
				return name;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append("(");
			boolean first = true;
			for (String option : options) {
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(option);
			}
			sb.append(")");
			return sb.toString();
		}

		@Override
		public String toString() {
			return "Annotations{" +
					"paramName='" + paramName + '\'' +
					", name='" + name + '\'' +
					", javaType=" + javaType +
					", options=" + options +
					'}';
		}
	}
}

