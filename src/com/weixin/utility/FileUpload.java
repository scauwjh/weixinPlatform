package com.weixin.utility;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 文件上传类
 * 
 * 上传保存的路径以一个input（hidden类型）存，name为path
 * 上传保存的文件名存放在每一个input（file类型）的name里面
 * 
 * @注意：path必须放在文件表单前面
 */
public class FileUpload {
	public boolean upload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String filepath = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			String file = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					file = item.getName();
					String filename = item.getFieldName();

					if ((file == "") || (file == null))
						continue;
					String tmpPath = filepath;

					File folder = new File(tmpPath);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					if (filepath.endsWith("/"))
						tmpPath = tmpPath + filename;
					else
						tmpPath = tmpPath + "/" + filename;
					item.write(new File(tmpPath));
				} else {
					String field = item.getFieldName();
					if (!field.equals("path"))
						continue;
					filepath = item.getString();
				}

			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}