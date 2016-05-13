package nsi.ita.chatbots.utils.socket;

import java.io.File;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class SocketFunction_Client {

	String[] arr_cmd = null;
	SocketClient sc = null;

	/**
	 * 构造函数
	 */
	public SocketFunction_Client(SocketClient sc) {
		this.sc = sc;
	}

	/**
	 * 解析命令
	 * 
	 * @param cmd
	 * @return
	 */
	public String analysis(String cmd) {

		arr_cmd = cmd.split(" ");

		// 与指定服务器建立连接
		if (mateOrder("connect")) {
			// 格式：connect [ip] [port]
			return connect();
		}

		// 关闭与服务器建立的连接
		else if (mateOrder("disconnect")) {
			// 格式：disconnect
			return disconnect();
		}

		// 清除显示框
		else if (mateOrder("cls")) {
			// 格式：cls
			sc.ta.setText("");
			return "";
		}

		// 共享资源文件
		else if (mateOrder("share")) {
			return share();
		}

		return "命令 " + arr_cmd[0] + " 无法识别！";
	}

	/**
	 * 比较字符串（命令行首单词）
	 * 
	 * @param str
	 * @return
	 */
	private boolean mateOrder(String str) {
		return str.equals(arr_cmd[0].toLowerCase());
	}

	/**
	 * 与指定服务器建立连接
	 * 
	 * @return
	 */
	private String connect() {
		try {
			sc.connect(arr_cmd[1], arr_cmd[2]);
			if (sc.bConnected) {
				return "与服务器 " + arr_cmd[1] + " 连接成功！";
			} else {
				return "与服务器 " + arr_cmd[1] + " 连接失败！";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return "与服务器 " + arr_cmd[1] + " 建立连接时发生异常："
					+ ex.getLocalizedMessage() + "\n用法：connect [ip] [port]";
		}
	}

	/**
	 * 关闭与服务器建立的连接
	 * 
	 * @return
	 */
	private String disconnect() {
		try {
			sc.disconnect();
			return "关闭与服务器的连接.";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "关闭与服务器连接时发生异常：" + ex.getLocalizedMessage();
		}
	}

	/**
	 * 共享文件到已连接的服务器中 share [lexicon/phrases/language/synonyms]
	 * [0:仅传输新文件/1:清空后传输所有文件/2:传输并替换已存在文件]
	 * @return
	 */
	private String share() {

		if (!sc.bConnected) {
			return "共享文件失败，未连接到服务器.";
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String path = "";
					if (arr_cmd[1].equals("lexicon")) {
						path = Library_Config.Lexicon_Path;
					} else if (arr_cmd[1].equals("phrases")) {
						path = Library_Config.Phrases_Path;
					} else if (arr_cmd[1].equals("language")) {
						path = Library_Config.Language_Path;
					} else if (arr_cmd[1].equals("synonyms")) {
						path = Library_Config.Synonyms_Path;
					}

					File file = new File(path);
					if (file.isDirectory()) {
						String[] filelist = file.list();
						for (int i = 0; i < filelist.length; i++) {
							File readfile = new File(path + filelist[i]);
							if (!readfile.isDirectory()) {
								// share [type] [filename] [rootword] [mode]
								String[] contents = new RWfile_Util(path).ReadFile(readfile.getName(), false);
								String content = "";
								for(String line :contents){
									content += (line + "\r\n");
								}
								sc.send(String.format(
										"share %s %s %s %s %s",
										arr_cmd[1],
										readfile.getName(),
										content, 
										""+ arr_cmd[2],
										""+ arr_cmd[3]));
								sc.Show("文件 " + readfile.getName()
										+ " 开始传输！······ " + i);
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					sc.Show( "共享本地 " + arr_cmd[1] + " 文件时发生异常：" +
					 ex.getLocalizedMessage() +
					 "\n用法：share [lexicon/phrases/language/synonyms] [0:仅传输新文件/1:清空后传输所有文件/2:传输并替换已存在文件]");
				}
			}
		}).start();

		return "共享本地 " + arr_cmd[1] + " 文件.";
	}
}
