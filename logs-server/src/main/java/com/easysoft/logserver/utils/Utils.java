package com.easysoft.logserver.utils;

public class Utils {
	public static void main(String[] args) {
		System.out.println("Java 类格式版本号:" + System.getProperty("java.class.version"));
		System.out.println("Java 运行时环境规范名称:" + System.getProperty("java.specification.name"));
		System.out.println("Java 运行时环境规范供应商:" + System.getProperty("java.specification.vendor"));
		System.out.println("Java 运行时环境版本:" + System.getProperty("java.version"));
		System.out.println("Java 运行时环境供应商:" + System.getProperty("java.vendor"));
		System.out.println("Java 供应商的 URL:" + System.getProperty("java.vendor.url"));
		System.out.println("Java 安装目录:" + System.getProperty("java.home"));
		System.out.println("Java 虚拟机规范版本:" + System.getProperty("java.vm.specification.version"));
		System.out.println("Java 虚拟机规范供应商:" + System.getProperty("java.vm.specification.vendor"));
		System.out.println("Java 虚拟机规范名称:" + System.getProperty("java.vm.specification.name"));
		System.out.println("Java 虚拟机实现版本:" + System.getProperty("java.vm.version"));
		System.out.println("Java 虚拟机实现供应商:" + System.getProperty("java.vm.vendor"));
		System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
		System.out.println("用户的主目录:" + System.getProperty("user.home"));
		System.out.println("行分隔符（在 UNIX 系统中是“/n”）:" + System.getProperty("line.separator"));
		System.out.println("路径分隔符（在 UNIX 系统中是“:”）:" + System.getProperty("path.separator"));
		System.out.println("文件分隔符（在 UNIX 系统中是“/”）:" + System.getProperty("file.separator"));
		System.out.println("操作系统的版本:" + System.getProperty("os.version"));
		System.out.println("操作系统的架构:" + System.getProperty("os.arch"));
		System.out.println("操作系统的名称:" + System.getProperty("os.name"));
		System.out.println("一个或多个扩展目录的路径:" + System.getProperty("java.ext.dirs"));
		System.out.println("要使用的 JIT 编译器的名称:" + System.getProperty("java.compiler"));
		System.out.println("默认的临时文件路径:" + System.getProperty("java.io.tmpdir"));
		System.out.println("Java 虚拟机实现名称:" + System.getProperty("java.vm.name"));
		System.out.println("Java 运行时环境规范版本:" + System.getProperty("java.specification.version"));
		System.out.println("加载库时搜索的路径列表:" + System.getProperty("java.library.path"));
		System.out.println("类路径:" + System.getProperty("java.class.path"));
	}

}
