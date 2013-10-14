package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.weixin.service.FileService;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月14日 下午2:37:45 
 * 
 *
 */
public class FileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private FileService fileService = new FileService();
	private PrintWriter out = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		
		String action = (String)request.getAttribute("action");
		if(action.equals("createHtml")){
			//生成静态html
			createHtml(request,response);
		}
		else if(action.equals("fileUpload")){
			//上传文件
			fileUpload(request, response);
		}
	}
	
	private void createHtml(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String html = request.getParameter("html");
		String path = request.getParameter("path");
		boolean flag = fileService.createHtml(html, path);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		DiskFileItemFactory factory = null;
		ServletFileUpload upload = null;
		List<FileItem> items = null;
		boolean flag = false;
		try {
			factory = new DiskFileItemFactory();
			upload = new ServletFileUpload(factory);
			items = upload.parseRequest(request);
			flag = fileService.fileUpload(items);
		} catch (FileUploadException e) {
			e.printStackTrace();
			flag = false;
		} finally{
			factory = null;
			upload = null;
			items = null;
		}
		if(flag){
			out.print("ok");
			return;
		}
		out.println("error");
	}

}
