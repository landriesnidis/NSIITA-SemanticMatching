package nsi.ita.chatbots.utils.socket;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SocketClient extends Frame { //繼承Frame类

	private static final long serialVersionUID = -4326362275929099600L;
	
	public Socket s = null;
    public DataOutputStream dos = null;
    public DataInputStream dis = null;
    public boolean bConnected = false;
	public SocketClient sc = null;
	public SocketFunction_Client sfo = null;
    
    recvThread r = new recvThread(); //线程类
    
    public TextField tf = new TextField(); //输入框对象
    public TextArea ta = new TextArea(); //显示框对象
    
    
    public static void main(String[] args) {
        new SocketClient();
    }
    
    /**
     * 构造函数
     */
    public SocketClient() {
    	sc = this;
    	sc.creatFrame();
    	sc.sfo = new SocketFunction_Client(sc);
	}
    
    /**
     * 产生图形界面
     */
    public void creatFrame() {     
        this.setBounds(300, 300, 800, 300);
        ta.setEditable(false);
        this.add(tf, BorderLayout.SOUTH);
        this.add(ta, BorderLayout.NORTH);
        this.addWindowListener(new WindowAdapter() { //响应关闭窗口事件
                    public void windowClosing(WindowEvent e) {
                        disconnect();
                        System.exit(0);
                    }
                });
        tf.addActionListener(new tfListener()); //响应输入事件
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * 建立客户端对象
     */
    public void connect(String ip,String port) {
        try {
            s = new Socket(ip, Integer.parseInt(port)); 
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            bConnected = true;
            new Thread(r).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 窗口关闭时关闭客户端，输入，输出
     */
    public void disconnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 输入框实现的接口，响应输入事件
     */
    class tfListener implements ActionListener { 
        public void actionPerformed(ActionEvent e) {
            String str = tf.getText();
            tf.setText("");
            
            send(str);
            Show(sfo.analysis(str));
        }
    }
    
    /**
     * 客户端线程接收数据
     */
    class recvThread implements Runnable {
        public void run() {
            try {
                while (bConnected) {
                    String str;
                    str = dis.readUTF(); //拿到数据
                    Show(str);
                }
            } catch (SocketException e) {
                System.out.println("连接已断开...");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * 发送数据到已连接的服务器中
     * @param str
     * @return
     */
     public boolean send(String str){
    	 
    	Show("Commend > " + str);
    	
    	//如果未建立连接则返回false
    	if(!bConnected){
    		return bConnected;
    	}
    	
    	try {
            dos.writeUTF(str);
            dos.flush();
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
    }
    
    /**
     * 显示内容到显示框中
     * @param str
     */
    public void Show(String str){
    	ta.setText(ta.getText() + getDate() + " " + str + "\n");//显示到显示框中
    }

	/**
	 * 获取当前日期 
	 * @return
	 */
  	private String getDate(){
  		//设置日期格式
  		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
  		// new Date()为获取当前系统时间
  		return df.format(new Date()) + "";
  	}
}