package cn.sunflyer.zfang.obj;

import java.lang.reflect.Field;

import cn.sunflyer.zfang.anno.Load;

public abstract class AbsInfo {
	
	protected AbsInfo(){
		
	}
	
	protected AbsInfo(String[] data){
		Class<?> pCls = this.getClass();
		Field [] pData = pCls.getFields();
		for( Field x : pData){
			Load pli = x.getAnnotation(Load.class);
			if(pli != null){
				try {
					x.set(this, data[pli.path()]);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(IndexOutOfBoundsException e){
					System.out.println("访问数组溢出 ： " + pli.name() + "于位置" + pli.path());
				}
			}
		}
	}
	
	public String toString(){
		Class<?> cls = this.getClass();
		Field[] data = cls.getFields();
		StringBuffer pSb = new StringBuffer();
		for(Field x : data){
			Load pli = x.getAnnotation(Load.class);
			if(pli != null && pli.isReq()){
				try {
					pSb.append(pli.name() + " : " + x.get(this) + " , ");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return pSb.toString();
	}
	
	public String toJson(){
		Class<?> cls = this.getClass();
		Field[] data = cls.getFields();
		StringBuffer pSb = new StringBuffer("{");
		for(Field x : data){
			Load pli = x.getAnnotation(Load.class);
			if(pli.isReq()){
				try {
					pSb.append("\"" + x.getName() + "\":\"" + String.valueOf(x.get(this)) + "\",");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		pSb.deleteCharAt(pSb.length() - 1).append("}");
		return pSb.toString();
	}

}
