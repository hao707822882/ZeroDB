package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bean.Message;

public class SocketUtil {

	public static Socket getClientSocket(String address, int port)
			throws UnknownHostException, IOException {
		return new Socket(address, port);
	}

	public static void write(DataOutputStream out, Message message)
			throws IOException {
		write(out, message);
	}

	public static void WriteData(DataOutputStream out, Message message)
			throws IOException {
		out.write((byte) message.getType());
		out.writeInt(message.getData().getBytes().length);
		out.write(message.getData().getBytes());
	}

	public static Message readData(DataInputStream in) throws IOException {
		byte[] data = null;
		Message message = new Message();
		int type = in.readByte();
		int length = in.readInt();
		in.read(data, 0, length);
		message.setType(type);
		message.setData(new String(data));
		return message;
	}
}
