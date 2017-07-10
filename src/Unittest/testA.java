package Unittest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.AddStudentGUIController;
import client.ClientConsole;
import client.DefineClass4CourseGUIController;
import client.SecMainGUIController;
import entity.Course;
import entity.Semester;
import entity.Student;
import junit.framework.TestCase;

public class testA{
	//controllers 
	private SecMainGUIController openNewSem = new SecMainGUIController();
	private AddStudentGUIController addStudent = new AddStudentGUIController();
	private DefineClass4CourseGUIController defineCourse = new DefineClass4CourseGUIController();
	
	//connection to server
	public static ClientConsole cli= new ClientConsole("127.0.0.1", 5555);
	
	private Student s;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		s = new Student();
		s.setId("123456789");
		s.setName("Moshe");
		s.setUserName("moshe1");
		s.setPassword("1234");
		s.setParentId("003511427");
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Testing if a new semester opens.
	 * @throws IOException
	 */
	@Test
	public void testOpenNewSemester(){
		boolean b = openNewSem.openNewSem();
		assertEquals(true,b);
	}
	/**
	 * Testing Adding a generic student
	 */
	@Test
	public void addGenericStudent(){
		addStudent.addStudent(s.getId(), s.getName(), s.getUserName(), s.getPassword(), s.getParentId());
		boolean b = addStudent.studentExists(s.getId());
		assertEquals(true,b);
	}
	/**
	 * Trying to register the new student to a course with pre-requisites.
	 * The new student should not meet the pre-requisites.
	 */
	@Test
	public void checkStudentPreReqFail(){
		String par[] = {s.getId(),null,"02102",null};
		boolean b = defineCourse.checkStudentPre(par);
		assertEquals(false,b);
	}
	/**
	 * Trying to register the new student to a course with no pre-requisites.
	 * The new student should meet the pre-requisites.
	 */
	@Test
	public void checkStudentPreReqSucceed(){
		String par[] = {s.getId(),null,"02100",null};
		boolean b = defineCourse.checkStudentPre(par);
		assertEquals(true,b);
	}
}
