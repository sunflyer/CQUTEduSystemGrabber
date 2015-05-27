package cn.sunflyer.zfang.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Load {
	
	public String name();
	
	public int path();
	
	public boolean isReq() default false;

}
