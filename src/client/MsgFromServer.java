package client;

import java.util.ArrayList;

import thred.IndexList;

/**
 * This class handles the connection between the server and the client
 */
public class MsgFromServer {

	public static ArrayList<Object> dataList = new ArrayList<>();

	/**
	 * This method creates new data list for all the info that returns from the server
	 */
	public static void initiazlizeData() {
		dataList = new ArrayList<>();
	}

	/**
	 * This method sets the data in the data list by int index to be obj value.
	 * 
	 * @param index where to insert data in list.
	 * @param obj what data to insert/change.
	 */
	public static void setDataListByIndex(int index, Object obj) {
		while (index >= dataList.size())
			dataList.add(null);
		dataList.set(index, obj);
	}

	/**
	 * This method returns the wanted data from the data list by int index.
	 * 
	 * @param index
	 * @return the data from dataList in wanted index
	 */
	public static Object getDataListByIndex(int index) {
		Object tmp = null;
		try {
			if (index >= dataList.size())
				return null;
			tmp = dataList.get(index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tmp;
	}

	/**
	 * This method sets the data in the data list by int index to be null.
	 * 
	 * @param index where to insert data in list.
	 */
	public static void deleteDataListByIndex(int index) {
		if (index >= dataList.size())
			return;
		dataList.set(index, null);
	}

	/**
	 * This method sets all data list from 4 till end to null, clean sheets
	 */
	public static void renewDataList() {
		for (int index = 4; index < dataList.size(); index++)
			deleteDataListByIndex(index);
	}

	public static void handleMsg(Object msg) {
		Op returnMsg = null;
		/* Switch case */
		try {
			returnMsg = (Op) msg;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(returnMsg.getMsg());
		switch (returnMsg.getOp()) {
		case LOGIN:
			setDataListByIndex(IndexList.LOGIN, returnMsg.getMsg());
			return;
		case LOGOUT:
			setDataListByIndex(IndexList.LOGOUT, returnMsg.getMsg());
		case StudentDetails:
			setDataListByIndex(IndexList.StudentDetails, returnMsg.getMsg());
			return;
		case UpdateUnit:
			setDataListByIndex(IndexList.UpdateUnit, returnMsg.getMsg());
		case Teacherdetails:
			setDataListByIndex(IndexList.Teacherdetails, returnMsg.getMsg());
		case StudentExists:
			setDataListByIndex(IndexList.StudentExists, returnMsg.getMsg());
		case ParentExists:
			setDataListByIndex(IndexList.ParentExists, returnMsg.getMsg());
		case AddStudent:
			setDataListByIndex(IndexList.AddStudent, returnMsg.getMsg());
		case UserNameExists:
			setDataListByIndex(IndexList.UserNameExists, returnMsg.getMsg());

		default:
			break;
		}

	}
}
