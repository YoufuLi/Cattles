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
	 * author:xiong rong 
	 * 功能：生成唯一标识符，用于集群的名称，每一次创建集群，名字不一样
	 * @param args
	 */
	public static String genUUID() {
		String s = UUID.randomUUID().toString().substring(0, 8);
		return s;
	}

	/**
	 * 
	 * author:xiong rong 
	 * 功能：虚拟机池子创建虚拟机次数不同，得到的名字不一样，这样做的方便检查每次池子创建的虚拟机是否是运行状态
	 * 参数vmCreateNum：创建的虚拟机次数，是第几次创建虚拟机
	 */
//	public static String genVmName() {
//		String str = OpennebulaPlatform.VM_NAME_PRE + OpennebulaPlatform.CREATE_VM_NUM++;
//		return str;
//	}
	
	/**
	 * 
	 * author:xiong rong 功能：判断文件是否存在
	 * 
	 * @param args
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
