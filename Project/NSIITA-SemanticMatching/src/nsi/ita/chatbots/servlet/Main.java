package nsi.ita.chatbots.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.config.Log_Config;
import nsi.ita.chatbots.function.PhrasesAnalysis;

/**
 * main.do
 * @author Administrator
 *
 */
public class Main extends HttpServlet {

	private static final long serialVersionUID = -1331826564609282689L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 必填参数
		String account = request.getParameter("account");
		String content = request.getParameter("content");
		String relevance = request.getParameter("rel");
		String languagePkg = request.getParameter("language");
		
		PhrasesAnalysis pa = new PhrasesAnalysis();
		
		String result = pa.toPhrasesAnalysis(content, languagePkg);
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		
		String log =  String.format("Date:%s \t IP:%s \t account=%s \t content=%s \t relevance=%s \t result=%s \r\n", 
				Log_Config.getDate("HH:mm:ss"),request.getRemoteAddr(),account,content,relevance,result);
		Log_Config.LOG(log);
	}

}
