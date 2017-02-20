package com.yiyun.framework.exception.handle;

public class HasOneException extends AddException{

	private static final long serialVersionUID = -3675717028521301093L;

	public HasOneException() {
        super();
    }

    public HasOneException(String message) {
        super("保存重复异常："+message);
    }

    public HasOneException(Throwable cause) {
        super(cause);
    }

    public HasOneException(String message, Throwable cause) {
        super("保存重复异常："+message, cause);
    }

}
