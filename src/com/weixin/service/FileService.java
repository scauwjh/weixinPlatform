package com.weixin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月14日 下午2:19:55 
 * 
 *
 */
public class FileService {
	
	/**
	 * 
	 * @param html 要生成的内容
	 * @param path 存放文件的完整地址（含完整的文件名）
	 * @return boolean
	 */
	public boolean createHtml(String html, String path){
		File htmlFile = null;
		FileOutputStream out = null;
		boolean flag = false;
		try {
			htmlFile = new File(path);
			htmlFile.mkdirs();
			out = new FileOutputStream(htmlFile);
			out.write(html.getBytes("UTF-8"));
			out.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally{
			out = null;
			htmlFile = null;
		}
		return flag;
	}
	
	public boolean fileUpload(List<FileItem> list){
		String filePath = null;
		try {
			Iterator<FileItem> iter = list.iterator();
			String file = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					file = item.getName();
					String fileName = item.getFieldName();

					if ((file == "") || (file == null))
						continue;
					String tmpPath = filePath;

					File folder = new File(tmpPath);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					if (filePath.endsWith("/"))
						tmpPath = tmpPath + fileName;
					else
						tmpPath = tmpPath + "/" + fileName;
					item.write(new File(tmpPath));
				} else {
					String field = item.getFieldName();
					if (!field.equals("path"))
						continue;
					filePath = item.getString();
				}

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
