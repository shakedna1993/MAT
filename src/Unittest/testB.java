package Unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.AssMainGUIController;
import client.ClientConsole;
import client.StudentUploadAssGUIController;
import entity.Assigenment;
import entity.Student;
import entity.Studentass;
import junit.framework.TestCase;
import thred.IndexList;
import thred.MyThread;
import javafx.scene.control.TableView;

public class testB extends TestCase{

	
	//connection to server
	public static ClientConsole cli= new ClientConsole("127.0.0.1", 5555);
	
	private Assigenment a;
	private Studentass SA;
	private Student s;
	private File f;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		a = new Assigenment();
		a.setAssId(15);
		s = new Student();
		s.setId("123456789");
		s.setName("Moshe");
		s.setUserName("moshe1");
		s.setPassword("1234");
		s.setParentId("003511427");
		f = new File("C:\\MAT-LocalFiles\\Dump20170704.sql");
		
		SA = new Studentass(a.getAssId(), s.getId(), "02100", "file.txt", new Date(), f, "nullFile");
		Path path = Paths.get(f.getAbsolutePath());
		try {
			SA.setData(Files.readAllBytes(path));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Testing downloading an assignment from the system.
	 * @throws IOException
	 */
	@Test
	public void testDownloadAss() {
		Assigenment downloadedAss = AssMainGUIController.downloadAss(a);
		assertNotEquals(downloadedAss, null);
	}
	
	@Test
	public void testUploadAss() {
		int a = StudentUploadAssGUIController.upFile(SA);
		assertEquals(a,0);
	}


}
