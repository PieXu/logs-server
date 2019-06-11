package com.distri.log;

public class Test {

	public static void main(String[] args) {
		int a = 1,b=2; // 10 01 
		a^=b; // a 00
		b^=a; // 10
		a^=b; // 01
		System.out.println("a:"+a + " ==== b:" + b);
	}
}
