package cn.sunflyer.zfang.obj;

public class DataVal {
	
	private CharSequence name;
	
	private CharSequence data;

	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}

	public CharSequence getData() {
		return data;
	}

	public void setData(CharSequence data) {
		this.data = data;
	}
	
	public DataVal(CharSequence name,CharSequence data){
		this.name = name;
		this.data = data;
	}
	
	public String toString(){
		return name + " : " + data;
	}

}
