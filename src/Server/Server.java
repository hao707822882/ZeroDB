package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server extends AbstractServerSocket {

	DataBaseEngin dbEngin;

	public Server() {
		dbEngin = new DataBaseEngin();
	}

	// 默认保存客户端的连接，以减少资源的消耗
	public void doAccept(Socket socket) {
		WorkerThread worker = new WorkerThread(socket, dbEngin);
		Thread work = new Thread(worker);
		work.start();
	}

	public void bootstart() {
		try {
			initServerSeockt(9000);
			accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.bootstart();
	}
}
