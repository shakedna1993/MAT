package entity;

import java.io.Serializable;
import java.util.Arrays;

public class MyFile implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] data;
	private long size;
	private String fileName;

	public MyFile() {
		super();
	}

	public MyFile(byte[] data, long size, String fileName) {
		super();
		this.data = data;
		this.size = size;
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public MyFile setData(byte[] data) {
		this.data = data;
		return this;
	}

	public long getSize() {
		return size;
	}

	public MyFile setSize(long size) {
		this.size = size;
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public MyFile setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	@Override
	public String toString() {
		return "MyFile [fileName=" + fileName + ", size=" + size + "]";
	}

}
