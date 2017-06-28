package client;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import entity.Class;
import entity.Course;
import entity.Reports;
import entity.Semester;
import entity.Student;
import entity.Teacher;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import thred.IndexList;
import thred.MyThread;

public class StatisticReportCTGuIController implements Initializable {
	@FXML 
	Label ManName,selectclass,choosenum;
	@FXML
	ComboBox<String> STC;
	@FXML
	ComboBox<String> STC1;
	@FXML
	ComboBox<String> STC2;
	@FXML
	Button selectBtn;
	@FXML
	Button selectBtn1;
	@FXML
	Button selectBtn2;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML 
	TextField num;
	@FXML
	ImageView Logo;
	@FXML
	BarChart<String,Float> diagram;
	
	private ObservableList<String> classes = FXCollections.observableArrayList();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		User s =new User();
		s=(User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());
	
		setComboBoxChooseClass();
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxChooseClass(){
		ArrayList<Class> a1 = new ArrayList<Class>();
		ArrayList<String> a2 = new ArrayList<String>();
		Class cl1 = new Class();
		MyThread a = new MyThread(RequestType.createClassEntity, IndexList.createClassEntity,cl1);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.createClassEntity);
		
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getName());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}
	
	@FXML
	@SuppressWarnings("unchecked")
	public void setComboBoxStartSem(){
		ArrayList<Semester> a1 = new ArrayList<Semester>();
		ArrayList<String> a2 = new ArrayList<String>();
		Semester sem = new Semester();
		MyThread a = new MyThread(RequestType.createSemesterEntity, IndexList.createSemesterEntity, sem);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Semester>) MsgFromServer.getDataListByIndex(IndexList.createSemesterEntity);
		
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getSemId());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC1.setItems(list);
	}
	
	@FXML
	@SuppressWarnings("unchecked")
	public void setComboBoxEndSem(){
		ArrayList<Semester> a1 = new ArrayList<Semester>();
		ArrayList<String> a2 = new ArrayList<String>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		Semester sem = new Semester();
		Object st= STC1.getValue();
		int Semid=Integer.parseInt(st.toString());
		
		MyThread a = new MyThread(RequestType.createSemesterEntity, IndexList.createSemesterEntity, sem);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Semester>) MsgFromServer.getDataListByIndex(IndexList.createSemesterEntity);
		
		for(int i=0;i<a1.size();i++){
			a2.add(a1.get(i).getSemId());
			a3.add(Integer.parseInt(a1.get(i).getSemId()));
		}
		
		a2.clear();
		for(int i=0; i<a3.size();i++){
			if(a3.get(i)>=Semid)
				a2.add("0"+(a3.get(i).toString()));
		}

		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC2.setItems(list);
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void setDiagram(){
		
		String ClassName=(String)STC.getValue();
		Object st= STC2.getValue();
		int ens=Integer.parseInt(st.toString());
		Object st1= STC1.getValue();
		int sts=Integer.parseInt(st1.toString());
		
		ArrayList<String> courselist = new ArrayList<String>();
		ArrayList<Student> a2 = new ArrayList<Student>();
		ArrayList<Student> a3 = new ArrayList<Student>();
		ArrayList<String> a4 = new ArrayList<String>();
		ArrayList<Float> grade = new ArrayList<Float>();
		ArrayList<Float> classgrade = new ArrayList<Float>();
		
		Class cl=new Class();
		cl.setName(ClassName);
		MyThread t = new MyThread(RequestType.ClassNameToId, IndexList.ClassNameToId,cl);
		t.start();
		try {
			t.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		cl = (Class) MsgFromServer.getDataListByIndex(IndexList.ClassNameToId);
	
		
		MyThread a = new MyThread(RequestType.ClassTeacherList, IndexList.ClassTeacherList,cl);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		cl = (Class) MsgFromServer.getDataListByIndex(IndexList.ClassTeacherList);
		courselist=cl.getCourseList();
		
		MyThread b = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity,cl);
		b.start();
		try {
			b.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
				
/*	
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
     //   final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
      //  bc.setTitle("Teacher Class Summery");
        xAxis.setLabel("Class");       
        yAxis.setLabel("Grades");
       
        
        String[] srr = new String[a1.size()];
        Float[] arr = new Float[classgrade.size()];
        
        for(int i = 0; i < a1.size(); i++)
        {
        	srr[i] = a1.get(i).getClassId();
        	arr[i] = classgrade.get(i);
        }
        
        classes.addAll(Arrays.asList(srr));
        xAxis.setCategories(classes);
        
        XYChart.Series<String,Float> series = new XYChart.Series<>();
        for (int i = 0; i < a1.size(); i++){
        	series.setName(srr[i]);
        	series.getData().add(new XYChart.Data<>(srr[i], arr[i]));
 
        }
    
        diagram.getData().add(series);
	*/	
	}
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.ShowReportSection();
	}
	
	@FXML
	public void clsReportCT() {
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT, MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			connectionmain.showLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
