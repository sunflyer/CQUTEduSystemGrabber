package cn.sunflyer.zfang.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Invoker {
	public boolean req() default true;
	public String name();
	public String nodata() default "当前区块没有可用数据";
	public boolean hasConclusion() default false;
	public String conclusionMethod() default "";
}
