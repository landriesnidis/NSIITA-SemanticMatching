package nsi.ita.chatbots.utils.socket;

import java.io.*;
import java.net.*;

public class SocketSendData {

	String IPaddress = null;
	int Port = 0;
	Socket socket = null;

	public SocketSendData(String ip, int port, final String strData) throws UnknownHostException, IOException { // 构造函数
		this.IPaddress = ip;
		this.Port = port;
		
		Connection();
		SendData(strData);
		Close();
	}

	private void Connection() throws UnknownHostException, IOException { // 建立连接
		socket = new Socket(IPaddress, Port);
	}

	private void Close() throws IOException { // 关闭Socket
		socket.close();
	}

	private void SendData(String data) throws IOException { // 发送数据
		PrintWriter os = new PrintWriter(socket.getOutputStream());
		os.write(data);
		os.flush();
		os.close(); // 关闭Socket输出流
	}
}