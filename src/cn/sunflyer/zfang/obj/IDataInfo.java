package cn.sunflyer.zfang.obj;

public interface IDataInfo {
	
	public String toString();
	
	public String toJson();

	/**
	 * 获取所有Load注解标注为isReq为true的数据字段名称
	 * */
	public String[] getRequiredDataName();
	
	/**
	 * 获取所有Load注解标注为isReq为true的数据字段内容
	 * */
	public String[] getRequiredData();
}
