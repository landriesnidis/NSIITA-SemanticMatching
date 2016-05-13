package nsi.ita.chatbots.function;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.ansj.splitWord.analysis.ToAnalysis;

import nsi.ita.chatbots.config.library.Language_Config;
import nsi.ita.chatbots.config.library.Lexicon_Config;
import nsi.ita.chatbots.config.library.Phrases_Config;
import nsi.ita.chatbots.utils.socket.SocketClient;
import nsi.ita.chatbots.utils.socket.SocketServer;

public class Init implements ServletContextListener {
	 
    public void contextInitialized(ServletContextEvent arg0) {
    		
    	//初始化开始
    	System.out.println("\nChatBots初始化加载中...\n");
    	
    	//初始化命令短语集
    	System.out.println("正在导入短语数据...");
    	Phrases_Config.Init();
    	System.out.println("短语导入完毕！\n");
    	
        //初始化词库    
    	System.out.println("正在导入词库数据...");
        Lexicon_Config.Init();
        System.out.println("词库导入完毕！\n");
        
        //初始化语言包
        System.out.println("正在导入语言包...");
        Language_Config.Init();
        System.out.println("语言包导入完毕！\n");
        
        //初始化分词系统
        System.out.println("正在初始化分词系统...");
        String str = "初始化ANSJ分词系统，当前使用分词模式：精准分词";
        ToAnalysis.parse(str);
        System.out.println("分词系统初始化完成！\n");
        
        //Socket服务
        System.out.println("正在启动Socket服务...");
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				new SocketServer(9876).start();
			}
		}).start();
        
        //Socket客户端
        System.out.println("正在启动Socket客户端...");
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				new SocketClient();
			}
		}).start();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}