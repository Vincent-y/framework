package com.java.jedis;

/**
 * <p> jedis 异常捕捉
 * <p>@author DRAGON
 * <p>@date 2015年6月3日
 * <p>@version 1.0
 */
public class RedisException extends Exception {

	private static final long serialVersionUID = -8249769002758605981L;

	public RedisException() {
		super();
	}

	public RedisException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedisException(String message) {
		super(message);
	}

	public RedisException(Throwable cause) {
		super(cause);
	}


}
