package com.weixin.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 文件夹操作类
 * 
 * 1.复制文件夹及内容
 * 2.删除文件夹及内容
 * 3.main测试方法
 */
public class FolderOperator {
	
	public static void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + temp.getName().toString());
					byte[] b = new byte[5120];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		if ((file.isFile()) && (file.exists())) {
			file.delete();
			return true;
		}
		return false;
	}

	public static boolean deleteFolder(String path) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;
		File[] fileList = dirFile.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				flag = deleteFile(fileList[i].getAbsolutePath());
				if (flag)
					continue;
				break;
			}
			if (fileList[i].isDirectory()) {
				flag = deleteFolder(fileList[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		return dirFile.delete();
	}

	public static void main(String[] args) {
		if (FolderOperator.deleteFolder("d:\\hello"))
			System.out.println("delete succeed!");
		else
			System.out.println("delete failed!");
	}
}