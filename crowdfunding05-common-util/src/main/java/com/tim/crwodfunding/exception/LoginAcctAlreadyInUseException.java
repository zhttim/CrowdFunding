package com.tim.crwodfunding.exception;

/**
 * 保存或更新Admin时账号重复异常
 * @author tim
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyInUseException() {
        super();
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
