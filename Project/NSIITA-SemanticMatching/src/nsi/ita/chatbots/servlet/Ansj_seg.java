package nsi.ita.chatbots.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

public class Ansj_seg extends HttpServlet {

	private static final long serialVersionUID = 8735608105709784401L;

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
		
		List<Term> terms = ToAnalysis.parse(Text);
		
		PrintWriter out = response.getWriter();
		out.print("分词结果为：\n" + terms);
		out.flush();
		out.close();
	}
}
