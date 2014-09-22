package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServerSocket {

	ServerSocket serversockt;

	public ServerSocket initServerSeockt(int port) throws IOException {
		return this.serversockt = new ServerSocket(port);
	}

	public void accept() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = serversockt.accept();
						System.out.println("连接上一个client");
						doAccept(socket);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	public abstract void doAccept(Socket socket);
}
