package entity;

import java.io.Serializable;

public class File implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3817447778523841598L;
	private String Fileid;
	private int FileType;
	private File File;
	/**
	 * FileType={1-PDF, 2-WORD, 3-ZIP}
	 */
	public File() {
		super();
	}
	public File(String fileid, int fileType, File file) {
		super();
		Fileid = fileid;
		FileType = fileType;
		File = file;
	}
	public String getFileid() {
		return Fileid;
	}
	public void setFileid(String fileid) {
		Fileid = fileid;
	}
	public int getFileType() {
		return FileType;
	}
	public void setFileType(int fileType) {
		FileType = fileType;
	}
	public File getFile() {
		return File;
	}
	public void setFile(File file) {
		File = file;
	}
	
	@Override
	public String toString() {
		return "File [Fileid=" + Fileid + ", FileType=" + FileType + "]";
	}
	
	

}
