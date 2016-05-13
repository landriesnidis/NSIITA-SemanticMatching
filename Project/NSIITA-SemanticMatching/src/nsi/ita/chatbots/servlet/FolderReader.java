package nsi.ita.chatbots.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsi.ita.chatbots.config.Library_Config;

public class FolderReader extends HttpServlet {

	private static final long serialVersionUID = 3837288076988429735L;

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
		
		PrintWriter out = response.getWriter();
		out.print("文件列表：../" + folderName + "\n" + readfile(folderName));
		out.flush();
		out.close();
	}

	
	private String readfile(String folderName)throws FileNotFoundException, IOException {
		String rst = "";
		String filepath;
		if(folderName != null){
			filepath = Library_Config.Library_Path + folderName + "\\";
		}else{
			filepath = Library_Config.Library_Path;
		}
		
		
		File file = new File(filepath);
		if (!file.isDirectory()) {
//			System.out.println("文件");
//			System.out.println("path=" + file.getPath());
//			System.out.println("absolutepath=" + file.getAbsolutePath());
//			System.out.println("name=" + file.getName());
			rst += "这是一个文件.";
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					rst += ("[File]\t\t" + readfile.getName());
					rst += "\n";
					} else if (readfile.isDirectory()) {
						rst += ("[Folder]\t" + filelist[i] + "\n");
				}
			}

		}
		return rst;
	}
}
