package nsi.ita.chatbots.utils.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
	boolean started = false;
	int port = 0;
	ServerSocket ss = null;
	List<ChatClient> clients = new ArrayList<ChatClient>(); // 保存客户端线程类

//	public static void main(String[] args) {
//		new SocketServer(port).start();
//	}

	public SocketServer(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			ss = new ServerSocket(port); 				// 建立服务端对象
			started = true;
			System.out.println("Socket服务已启动，端口：" + port);
		} catch (BindException e) {
			System.out.println("端口使用中");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while (started) {
				Socket s = ss.accept(); 				// 接收客户端
				ChatClient c = new ChatClient(s);
				System.out.println("客戶端接收成功");
				new Thread(c).start(); 					// 启动线程
				clients.add(c); 						// 添加线程类
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ChatClient implements Runnable { 				// 建立客户端线程接收，发送数据
		private Socket s;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		boolean bConnected = false;

		public ChatClient(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (SocketException e) {
				System.out.println("對方退出了");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			System.out.println("开启线程");
			try {
				while (bConnected) {
					String str = dis.readUTF();
					String result = new SocketFunction_Server().analysis(str);
					this.send(result);
				}
			} catch (EOFException e) {
				System.out.println("客戶端退出了");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (dis != null)
					if (s != null)
						try {
							dis.close();
							s.close();
							dos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		}
	}
																				
}