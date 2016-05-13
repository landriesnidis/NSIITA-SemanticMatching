package nsi.ita.chatbots.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class FileAppend extends HttpServlet {

	private static final long serialVersionUID = 6450127959989401275L;

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
		String content = request.getParameter("content");

		fileName += ("." + fileFormat);
		content += "\r\n";
		
		content = content.replace("_注释", "#");
		content = content.replace("_分隔", "|");
		
		String FilePath = Library_Config.Library_Path + folderName + "\\";
		boolean isWriter = new RWfile_Util(FilePath).AppendMethod(fileName, content);
		
		PrintWriter out = response.getWriter();
		out.print("[追加内容] ../" + folderName + "/" + fileName + " 状态:" + isWriter);
		out.flush();
		out.close();
	}

}
