
package client;

import java.io.Serializable;

/**
 * This class is used for transferring massages for the server. The attribute
 * "op" contains which functionality is needed. The attribute "msg" contatins
 * the object/s that being send.
 */
public class Op implements Serializable {

	Object msg;
	RequestType op;

	public Op(Object msg, RequestType op) {
		this.msg = msg;
		this.op = op;
	}

	public Op() {
		super();
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	public RequestType getOp() {
		return op;
	}

	public void setOp(RequestType op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return "Op [msg=" + msg + ", op=" + op + "]";
	}

}
