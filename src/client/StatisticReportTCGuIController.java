package client;


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
import javafx.beans.Observable;
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
import javafx.util.Callback;
import thred.IndexList;
import thred.MyThread;
import javafx.scene.control.TextField;

public class StatisticReportTCGuIController implements Initializable {
	@FXML 
	Label ManName,selecttec,choosenum;
	@FXML
	ComboBox<String> STC;
	@FXML
	ComboBox<String> STC1;
	@FXML
	ComboBox<String> STC2;
	@FXML
	Button Back;
	@FXML
	Button LogOut;
	@FXML
	Button selectBtn;
	@FXML
	Button selectBtn1;
	@FXML
	Button selectBtn2;
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
	
		setComboBoxChooseTec();
	}
	
	
	@SuppressWarnings("unchecked")
	public void setComboBoxChooseTec(){
		ArrayList<Teacher> a1 = new ArrayList<Teacher>();
		ArrayList<String> a2 = new ArrayList<String>();
		Teacher tec = new Teacher();
		MyThread a = new MyThread(RequestType.createTeacherEntity, IndexList.createTeacherEntity, tec);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Teacher>) MsgFromServer.getDataListByIndex(IndexList.createTeacherEntity);
		
		for(int i=0;i<a1.size();i++)
			a2.add(a1.get(i).getTecName());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}
	
	@FXML
	@SuppressWarnings("unchecked")
	public void setComboBoxStartSem(){
		ArrayList<Semester> a1 = new ArrayList<Semester>();
		ArrayList<String> a2 = new ArrayList<String>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
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
	
	/**
	 * This function gets the details needed for the report
	 * getting list of classes the teacher assign to.
	 * remove class from semesters that not in the range selected
	 * getting list of students and remove the students not in one of the classes
	 * adding list of students to the class entity
	 * 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void setDiagram(){
		
		String tecname=(String)STC.getValue();
		Object st= STC2.getValue();
		int ens=Integer.parseInt(st.toString());
		Object st1= STC1.getValue();
		int sts=Integer.parseInt(st1.toString());
		int flag=0;
		float sum=0,avg=0;
		ArrayList<Class> a1 = new ArrayList<Class>();
		ArrayList<Student> a2 = new ArrayList<Student>();
		ArrayList<Student> a3 = new ArrayList<Student>();
		ArrayList<String> a4 = new ArrayList<String>();
		ArrayList<Float> grade = new ArrayList<Float>();
		ArrayList<Float> classgrade = new ArrayList<Float>();
		Teacher tec=new Teacher();
		tec.setTecName(tecname);
		MyThread t = new MyThread(RequestType.TecNameToId, IndexList.TecNameToId,tec);
		t.start();
		try {
			t.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		tec = (Teacher) MsgFromServer.getDataListByIndex(IndexList.TecNameToId);
		
		
		MyThread a = new MyThread(RequestType.TeacherClassList, IndexList.TeacherClassList,tec);
		a.start();
		try {
			a.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.TeacherClassList);
		
		for(int i=0; i<a1.size(); i++)
			if((Integer.parseInt(a1.get(i).getSemid())>ens)||(Integer.parseInt(a1.get(i).getSemid())<sts))
				a1.remove(i);
		
		for(int i=0;i<a1.size();i++){
			for(int j=0; j<a1.size();j++){
				if(a1.get(i).getClassId().equals(a1.get(j).getClassId()))
					a4.add(a1.get(j).getCourseid());
			}
			a1.get(i).setCourseList(a4);
			a4.clear();
			}
		for(int i=0;i<a1.size();i++)
			for(int j=i+1; j<a1.size();j++)
				if(a1.get(i).getClassId().equals(a1.get(j).getClassId()))
					a1.remove(j);

		MyThread b = new MyThread(RequestType.StudentsList, IndexList.StudentsList);
		b.start();
		try {
			b.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		a2 = (ArrayList<Student>) MsgFromServer.getDataListByIndex(IndexList.StudentsList);
		for(int i=0; i<a2.size();i++){
			for(int j=0; j<a1.size(); j++)
				if(a2.get(i).getClassid().equals(a1.get(j).getClassId()))
					flag=1;
			if(flag==0)
				a2.remove(i);
			flag=0;
		}
		flag=0;
		for(int i=0;i<a1.size();i++){
			for(int j=0; j<a2.size();j++){
				if(a1.get(i).getClassId().equals(a2.get(j).getClassid()))
					a3.add(a2.get(j));
				a1.get(i).setStudentList(a3);
			}
			a3.clear();
		}
		
		for(int i=0; i<a1.size();i++){
			a3=a1.get(i).getStudentList();
			a4=a1.get(i).getCourseList();
			for(int j=0; j<a3.size();j++){
				MyThread c = new MyThread(RequestType.StudentCourse, IndexList.StudentCourse,a3.get(j));
				c.start();
				try {
					c.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				ArrayList<Course> lst=(ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.StudentCourse);
				for(int k=0;k<a4.size();k++){
					for(Course cu:lst)
						if(cu.getCourseId().equals(a4.get(k))){
							grade.add(cu.getGrade());
							flag=1;
						}
					if(flag==0)
						a4.remove(k);
					}
			}
			a1.get(i).setCourseList(a4);
			
			for(int j=0;j<grade.size();j++)
				sum+=grade.get(j);
			avg=sum/grade.size();
			sum=0;
			classgrade.add(avg);
		}
		
	
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
		
	}
	
	@FXML
	private void backButton(ActionEvent event) throws Exception{
		connectionmain.ShowReportSection();
	}
	
	@FXML
	public void clsReportTC() {
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
