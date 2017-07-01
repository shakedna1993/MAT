package entity;
/**
 * 
 * Class-Course view class for tableview format purposes.
 *
 */
public class CourseClassView {
	
		private String courseid,coursename,hours,teacherid,teachername;

		public CourseClassView(String courseid, String coursename, String hours, String teacherid, String teachername) {
			super();
			this.courseid = courseid;
			this.coursename = coursename;
			this.hours = hours;
			this.teacherid = teacherid;
			this.teachername = teachername;
		}

		public String getCourseid() {
			return courseid;
		}

		public void setCourseid(String courseid) {
			this.courseid = courseid;
		}

		public String getCoursename() {
			return coursename;
		}

		public void setCoursename(String coursename) {
			this.coursename = coursename;
		}

		public String getHours() {
			return hours;
		}

		public void setHours(String hours) {
			this.hours = hours;
		}

		public String getTeacherid() {
			return teacherid;
		}

		public void setTeacherid(String teacherid) {
			this.teacherid = teacherid;
		}

		public String getTeachername() {
			return teachername;
		}

		public void setTeachername(String teachername) {
			this.teachername = teachername;
		}

		
}
