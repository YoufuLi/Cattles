package com.cattles.util;

import java.io.File;
import java.util.UUID;

/**
 * 
 * @author xiong rong 用法：工具类
 */
public class Tool {

	/**
	 * 
	 * author:Youfu Li
	 * 功能：生成唯一标识符，用于集群的名称，每一次创建集群，名字不一样
	 * @param uuid
	 */
	public static String genUUID() {
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		return uuid;
	}
	
	/**
	 * 
	 * author:Youfu Li 功能：判断文件是否存在
	 * 
	 * @param path
	 */
	public static boolean isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果存在删除
		if (file.exists()) {
			boolean result = file.delete();
			return result;
		}
		// 如果不存在，返回true
		return true;
	}
}
