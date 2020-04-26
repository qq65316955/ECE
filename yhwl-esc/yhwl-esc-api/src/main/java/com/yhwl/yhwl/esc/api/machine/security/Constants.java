package com.yhwl.yhwl.esc.api.machine.security;

public class Constants {
	/**
	 * shiro采用加密算法
	 */
	public static final String HASH_ALGORITHM = "SHA-1";
	/**
	 * 生成Hash值的迭代次数 
	 */
	public static final int HASH_INTERATIONS = 1024;
	/**
	 * 生成盐的长度
	 */
	public static final int SALT_SIZE = 16;

}
