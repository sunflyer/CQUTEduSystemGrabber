package cn.sunflyer.zfang;

//import javax.swing.JOptionPane;
import cn.sunflyer.zfang.obj.UserInfo;

public class Main {
	
	public static void main(String[] args){
		/**
		String username,password;
		username = JOptionPane.showInputDialog("输入你的学号：");
		password = JOptionPane.showInputDialog("输入教务系统密码：");
		
		UserInfo pUi = EduSystem.login(username,password);
	**/
		//TODO CHANGE YOUR NAME AND PASSWORD HERE
		UserInfo pUi = EduSystem.login("学号","密码");
		
		//ClassTableGrabber.getClassTable(pUi);
		
		//GradeExGrabber.getGradeGx(pUi);
		
		Saver.saveGrade(pUi);
		
	}

}
