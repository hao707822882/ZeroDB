package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import util.SocketUtil;
import bean.Message;

public class Client {

	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	public void init(String address, int port) {
		try {
			socket = SocketUtil.getClientSocket(address, port);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/****
	 * 
	 * 发送连接信息 如果包含数据库名字 user-passwd-dataBaseName 如果沒有数据库名字 user-passwd
	 * 
	 * 
	 * ****/
	public void conncte(int type, String data) {
		Message message = new Message(type, data);
		try {
			SocketUtil.write(out, message);
			message = receivedData();
			System.out.println(message);
			message = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/****
	 * 
	 * 从in输入流中国读取服务器返回的消息
	 * 
	 * ***/
	public Message receivedData() {
		Message message = null;
		try {
			message = SocketUtil.readData(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

}
