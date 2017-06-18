package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Assigenment;
import entity.Course;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import thred.IndexList;
import thred.MyThread;

public class teacherControlerEditAss implements Initializable{
	@FXML
	javafx.scene.control.Label courseTxt;
	@FXML
	javafx.scene.control.Label assTxt;
	@FXML
	TextField stateTxt;
	@FXML
	TextField fileidTxt;
	

	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Course c = new Course();
	assTxt.setText(TchMainGUIController.assToChoose);
	ArrayList<Assigenment> b=(ArrayList<Assigenment>)MsgFromServer.getDataListByIndex(IndexList.setTableViewTeacherCourseAssigenment);
	
	MyThread a = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity, b.get(0).getCourseid());
	a.start();
	try {
			a.join();
	} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	c = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntity);
	courseTxt.setText(c.getName());
	}

}
