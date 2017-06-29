package client;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import entity.Class;
import entity.Course;
import entity.Semester;
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

/**
 * This class is the controller for the StatisticReportCT GUI
 */
public class StatisticReportCTGuIController implements Initializable {
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

	private ObservableList<String> teachers = FXCollections.observableArrayList();

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
	@FXML
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
	 * the grades of Courses teacher teach in the wanted Class of the selected
	 * semesters.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void setDiagramInfo() {
		String ClassName = (String) STC.getValue();
		Object st = STC2.getValue();
		Object st1 = STC1.getValue();
		if (st == null || st1 == null || STC2.getSelectionModel().getSelectedIndex() < 0
				|| STC1.getSelectionModel().getSelectedIndex() < 0)
			return;

		int ens = Integer.parseInt(st.toString());
		int sts = Integer.parseInt(st1.toString());
		Float sum = (float) 0, avg;
		int count = 0;

		ArrayList<Class> cla = new ArrayList<Class>();
		ArrayList<Class> clatec = new ArrayList<Class>();
		Set<String> uniquea1 = new HashSet<String>();
		ArrayList<String> courselist = new ArrayList<String>();
		ArrayList<String> teclist = new ArrayList<String>();
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<Course> course = new ArrayList<Course>();
		ArrayList<Course> course1 = new ArrayList<Course>();
		ArrayList<Float> grade = new ArrayList<Float>();
		ArrayList<Teacher> tecup = new ArrayList<Teacher>();

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

		MyThread a = new MyThread(RequestType.ClassTeacherList, IndexList.ClassTeacherList, cl);
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		cla = (ArrayList<Class>) MsgFromServer.getDataListByIndex(IndexList.ClassTeacherList);

		for (int i = 0; i < cla.size(); i++) {
			if ((Integer.parseInt(cla.get(i).getSemid()) < sts) || (Integer.parseInt(cla.get(i).getSemid()) > ens))
				cla.remove(i);
		}
		for (int i = 0; i < cla.size(); i++) {
			courselist.add(cla.get(i).getCourseid());
			teclist.add(cla.get(i).getTeachid());
		}
		cl.setCourseList(courselist);
		cl.setTecList(teclist);

		for (int i = 0; i < courselist.size(); i++) {
			Course cu = new Course();
			MyThread b = new MyThread(RequestType.CourseGradeList, IndexList.CourseGradeList, courselist.get(i));
			b.start();
			try {
				b.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			course = (ArrayList<Course>) MsgFromServer.getDataListByIndex(IndexList.CourseGradeList);
			for (int j = 0; j < course.size(); j++)
				grade.add(course.get(j).getGrade());
			cu.setCourseId(courselist.get(i));
			cu.setGradeList(grade);
			course1.add(cu);
			grade.clear();
		}

		for (int i = 0; i < teclist.size(); i++) {
			Class cl1 = new Class();
			for (int j = 0; j < cla.size(); j++) {
				if (teclist.get(i).equals(cla.get(j).getTeachid()))
					a1.add(cla.get(j).getCourseid());
			}
			cl1.setClassId(cla.get(i).getClassId());
			cl1.setTeachid(cla.get(i).getTeachid());
			cl1.setCourseList(a1);
			clatec.add(cl1);
			a1.clear();
		}

		cla.clear();
		for (int i = 0; i < clatec.size(); i++) {
			if (uniquea1.add(clatec.get(i).getTeachid()))
				cla.add(clatec.get(i));
		}
		clatec = cla;

		for (int i = 0; i < course1.size(); i++) {
			grade = course1.get(i).getGradeList();
			sum = avg = (float) 0;
			for (int j = 0; j < grade.size(); j++) {
				sum += grade.get(j);
			}
			if (sum > 0)
				avg = sum / grade.size();
			else
				avg = (float) 0;
			course1.get(i).setAvg(avg);
		}

		for (int i = 0; i < clatec.size(); i++) {
			sum = avg = (float) 0;
			count = 0;
			courselist = clatec.get(i).getCourseList();
			for (int j = 0; j < courselist.size(); j++) {
				for (int k = 0; k < course1.size(); k++) {
					if (courselist.get(j).equals(course1.get(k).getCourseId())) {
						sum += course1.get(k).getAvg();
						count++;
					}
				}
			}
			if (sum > 0)
				avg = sum / count;
			else
				avg = (float) 0;
			clatec.get(i).setAvg(avg);
		}

		MyThread tn = new MyThread(RequestType.createTeacherEntity, IndexList.createTeacherEntity);
		tn.start();
		try {
			tn.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		tecup = (ArrayList<Teacher>) MsgFromServer.getDataListByIndex(IndexList.createTeacherEntity);

		for (int i = 0; i < clatec.size(); i++)
			for (int j = 0; j < tecup.size(); j++)
				if (clatec.get(i).getTeachid().equals(tecup.get(j).getTecId()))
					clatec.get(i).setTecName(tecup.get(i).getTecName());

		setDiagram(clatec);
	}

	/**
	 * This method clears the Graph.
	 * 
	 */
	private void clearGraph() {
		teachers.clear();
		diagram.getData().clear();
	}

	/**
	 * This method Shows the Graph.
	 * 
	 */
	public void setDiagram(ArrayList<Class> clatec) {
		clearGraph();
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();

		diagram.setAnimated(false);
		diagram.setTitle("Class " + (String) STC.getValue() + " Teachers grades");
		xAxis.setLabel("Class");
		yAxis.setLabel("Grades");

		String[] srr = new String[clatec.size()];
		Float[] arr = new Float[clatec.size()];

		for (int i = 0; i < clatec.size(); i++) {
			srr[i] = clatec.get(i).getTecName();
			arr[i] = clatec.get(i).getAvg();
		}

		teachers.addAll(Arrays.asList(srr));
		xAxis.setCategories(teachers);

		XYChart.Series<String, Float> series = new XYChart.Series<>();
		series.setName("Teachers");
		for (int i = 0; i < clatec.size(); i++) {
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
	public void clsReportCT() {
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
