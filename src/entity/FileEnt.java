package entity;

import java.io.Serializable;

public class FileEnt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3817447778523841598L;
	private String Fileid, FileName, FilePath;
	private int FileType;
	private FileEnt File;
	/**
	 * FileType={1-PDF, 2-WORD, 3-ZIP}
	 */
	public FileEnt() {
		super();
	}
	
	public FileEnt(String fileid, String fileName, String filePath, int fileType, FileEnt file) {
		super();
		Fileid = fileid;
		FileName = fileName;
		FilePath = filePath;
		FileType = fileType;
		File = file;
	}

	public FileEnt(String fileid, int fileType, FileEnt file) {
		super();
		Fileid = fileid;
		FileType = fileType;
		File = file;
	}
	
	
	public FileEnt(String fileName, String filePath) {
		super();
		FileName = fileName;
		FilePath = filePath;
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
	public FileEnt getFile() {
		return File;
	}
	public void setFile(FileEnt file) {
		File = file;
	}
	
	
	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	@Override
	public String toString() {
		return "FileEnt [Fileid=" + Fileid + ", FileName=" + FileName + ", FilePath=" + FilePath + ", FileType="
				+ FileType + ", File=" + File + "]";
	}
	
	

}
