package client;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import entity.Class;
import entity.Course;
import entity.Semester;
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

/**
 * This class is the controller for the StatisticReportCC GUI
 */
public class StatisticReportCCGuIController implements Initializable {
	@FXML
	Label ManName, selectclass, choosenum, grade_label;
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
	BarChart<String, Float> diagram;

	private ObservableList<String> classes = FXCollections.observableArrayList();

	/**
	 * initialize-initialize the Manager name.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		User s = new User();
		s = (User) (MsgFromServer.getDataListByIndex(IndexList.LOGIN));
		ManName.setText(s.getName());

		setComboBoxChooseClass();
	}

	/**
	 * This method Shows a list of the Classes exist in the system.
	 */
	@FXML
	@SuppressWarnings({ "unchecked" })
	public void setComboBoxChooseClass() {
		ArrayList<Class> a1 = new ArrayList<Class>();
		ArrayList<String> a2 = new ArrayList<String>();
		Class cl1 = new Class();
		MyThread a = new MyThread(RequestType.createClassEntity, IndexList.createClassEntity, cl1);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.createClassEntity);

		for (int i = 0; i < a1.size(); i++)
			a2.add(a1.get(i).getName());
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC.setItems(list);
	}

	/**
	 * This method Shows a list of the Semesters exist in the system.
	 */
	@SuppressWarnings("unchecked")
	public void setComboBoxStartSem() {
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

		for (int i = 0; i < a1.size(); i++)
			a2.add(a1.get(i).getSemId());

		STC1.getSelectionModel().clearSelection();
		STC2.getSelectionModel().clearSelection();
		clearGraph();
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC1.setItems(list);
	}

	/**
	 * This method Shows a list of the Semesters after the semester selected
	 * before that exist in the system.
	 */
	@FXML
	@SuppressWarnings("unchecked")
	public void setComboBoxEndSem() {
		Object st = STC1.getValue();
		if (st == null || STC1.getSelectionModel().getSelectedIndex() < 0)
			return;
		ArrayList<Semester> a1 = new ArrayList<Semester>();
		ArrayList<String> a2 = new ArrayList<String>();
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		Semester sem = new Semester();
		int Semid = Integer.parseInt(st.toString());

		MyThread a = new MyThread(RequestType.createSemesterEntity, IndexList.createSemesterEntity, sem);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		a1 = (ArrayList<Semester>) MsgFromServer.getDataListByIndex(IndexList.createSemesterEntity);

		for (int i = 0; i < a1.size(); i++) {
			a2.add(a1.get(i).getSemId());
			a3.add(Integer.parseInt(a1.get(i).getSemId()));
		}

		a2.clear();
		for (int i = 0; i < a3.size(); i++) {
			if (a3.get(i) >= Semid)
				a2.add("0" + (a3.get(i).toString()));
		}
		STC2.getSelectionModel().clearSelection();
		clearGraph();
		ObservableList<String> list = FXCollections.observableArrayList(a2);
		STC2.setItems(list);
	}

	/**
	 * This method gets the needed information for the report. The method shows
	 * the grades of the Courses the class is learning in the selected
	 * semesters.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void setDiagramInfo() {

		String ClassName = (String) STC.getValue();
		Object st1 = STC1.getValue();
		Object st = STC2.getValue();
		if (st == null || st1 == null || STC2.getSelectionModel().getSelectedIndex() < 0
				|| STC1.getSelectionModel().getSelectedIndex() < 0)
			return;

		int ens = Integer.parseInt(st.toString());
		int sts = Integer.parseInt(st1.toString());
		Float sum = (float) 0, avg;
		ArrayList<Course> courselist = new ArrayList<Course>();
		ArrayList<Course> tempc = new ArrayList<Course>();
		ArrayList<Float> grade = new ArrayList<Float>();

		Class cl = new Class();
		cl.setName(ClassName);
		MyThread t = new MyThread(RequestType.ClassNameToId, IndexList.ClassNameToId, cl);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		cl = (Class) MsgFromServer.getDataListByIndex(IndexList.ClassNameToId);

		MyThread a = new MyThread(RequestType.ClassCourseDetails, IndexList.ClassCourseDetails, cl.getClassId());
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		courselist = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.ClassCourseDetails);

		for (int i = 0; i < courselist.size(); i++)
			if ((Integer.parseInt(courselist.get(i).getSemid()) < sts)
					|| (Integer.parseInt(courselist.get(i).getSemid()) > ens))
				courselist.remove(i);

		for (int i = 0; i < courselist.size(); i++) {
			MyThread b = new MyThread(RequestType.CourseGradeList, IndexList.CourseGradeList,
					courselist.get(i).getCourseId());
			b.start();
			try {
				b.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			tempc = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.CourseGradeList);
			for (int j = 0; j < tempc.size(); j++)
				grade.add(tempc.get(j).getGrade());
			courselist.get(i).setGradeList(grade);
			grade.clear();
		}

		for (int i = 0; i < courselist.size(); i++) {
			grade = courselist.get(i).getGradeList();
			sum = avg = (float) 0;
			for (int j = 0; j < grade.size(); j++) {
				sum += grade.get(j);
			}
			if (sum > 0)
				avg = sum / grade.size();
			else
				avg = (float) 0;
			courselist.get(i).setAvg(avg);
		}
		for (int i = 0; i < courselist.size(); i++) {
			Course c12 = new Course();
			MyThread ct = new MyThread(RequestType.createCourseEntity, IndexList.createCourseEntity,
					courselist.get(i).getCourseId());
			ct.start();
			try {
				ct.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			c12 = (Course) MsgFromServer.getDataListByIndex(IndexList.createCourseEntity);
			courselist.get(i).setName(c12.getName());

		}
		setDiagram(courselist);
	}

	/**
	 * This method clears the Graph.
	 * 
	 */
	private void clearGraph() {
		classes.clear();
		diagram.getData().clear();
	}

	/**
	 * This method Shows the Graph.
	 * 
	 */
	public void setDiagram(ArrayList<Course> courselist) {
		clearGraph();
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		diagram.setAnimated(false);
		diagram.setTitle("Class " + (String) STC.getValue() + " Courses grades");
		xAxis.setLabel("Class");
		yAxis.setLabel("Grades");

		String[] srr = new String[courselist.size()];
		Float[] arr = new Float[courselist.size()];

		for (int i = 0; i < courselist.size(); i++) {
			srr[i] = courselist.get(i).getName();
			arr[i] = courselist.get(i).getAvg();
		}

		classes.addAll(Arrays.asList(srr));
		xAxis.setCategories(classes);

		XYChart.Series<String, Float> series = new XYChart.Series<>();
		series.setName("Courses");
		for (int i = 0; i < courselist.size(); i++) {
			series.getData().add(new XYChart.Data<>(srr[i], arr[i]));
		}

		diagram.getData().add(series);
	}

	/**
	 * This method goes back to the last window that been shown
	 */
	@FXML
	private void backButton(ActionEvent event) throws Exception {
		connectionmain.ShowReportSection();
	}

	/**
	 * Logout from the server
	 */
	@FXML
	public void clsReportCC() {
		MyThread a = new MyThread(RequestType.LOGOUT, IndexList.LOGOUT,
				MsgFromServer.getDataListByIndex(IndexList.LOGIN));
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
