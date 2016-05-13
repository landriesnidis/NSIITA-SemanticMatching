package nsi.ita.chatbots.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class FileReader extends HttpServlet {

	private static final long serialVersionUID = -1619305700756512663L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 必填参数
		String folderName = request.getParameter("folder");
		String fileName = request.getParameter("file");
		String fileFormat = request.getParameter("format");
		
		fileName += ("." + fileFormat);
		String FilePath = Library_Config.Library_Path + folderName + "\\";
		
		String rst = "";
		String fileContext[] = new RWfile_Util(FilePath).ReadFile(fileName, false);
		for(String s:fileContext)
			rst += (s + "\n");
		
		PrintWriter out = response.getWriter();
		out.print("[浏览文件] ../" + folderName + "/" + fileName + ":\n\n" + rst);
		out.flush();
		out.close();
	}

}
