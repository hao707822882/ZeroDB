package bean;

public class Message {
	int type;
	String data;

	public Message(int type, String data) {
		this.type = type;
		this.data = data;
	}

	public Message() {

	}

	public int getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "������Ϣ��������" + type + "���ص�������" + data;
	}

}
