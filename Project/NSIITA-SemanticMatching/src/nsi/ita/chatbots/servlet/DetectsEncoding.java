package nsi.ita.chatbots.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsi.ita.chatbots.function.PhrasesAnalysis;

/**
 * detects_encoding.do
 * @author Administrator
 *
 */

public class DetectsEncoding extends HttpServlet {

	private static final long serialVersionUID = 2689859356687199529L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 必填参数
		String Text = request.getParameter("text");
		
		PrintWriter out = response.getWriter();
		out.print("检测到的文本内容为：" + Text);
		out.flush();
		out.close();
	}

}
