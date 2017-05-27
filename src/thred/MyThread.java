package thred;

import client.MsgFromServer;
import client.RequestType;
import client.ClientConsole;

/**
 * This class contain methods for handling with threads.
 */
public class MyThread extends Thread {

	private RequestType op;
	private int opIndex;
	private Object obj;

	/**
	 * @param op 		Which function to use
	 * @param opIndex	The index in the array list where to save the data
	 * @param obj		The object that being send to server or from it.
	 */
	public MyThread(RequestType op, int opIndex, Object obj) {
		super();
		this.op = op;
		this.opIndex = opIndex;
		this.obj = obj;
	}


	public MyThread(RequestType op, int opIndex) {
		super();
		this.op = op;
		this.opIndex = opIndex;
		this.obj = "";
	}
	
	public MyThread(RequestType op, Object obj) {
		super();
		this.op = op;
		this.obj = obj;
		this.opIndex=0;
	}

	/* 
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		MsgFromServer.setDataListByIndex(opIndex, null);
		ClientConsole.getFromServer(op, obj);
		try {
			while (MsgFromServer.getDataListByIndex(opIndex) == null)
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}


	public RequestType getRequestType() {
		return op;
	}


	public void setRequestType(RequestType op) {
		this.op = op;
	}

	public int getRequestTypeIndex() {
		return opIndex;
	}


	public void setRequestTypeIndex(int opIndex) {
		this.opIndex = opIndex;
	}


	public Object getObj() {
		return obj;
	}


	public void setObj(Object obj) {
		this.obj = obj;
	}

}