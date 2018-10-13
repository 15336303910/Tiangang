package cn.plou.web.common.exception;

/**
 * @Project : tg-services
 * @File : InvalidValueException.java
 * @Author : WangJiWei
 * @Date : 2017年11月27日上午10:23:53
 *
 * @Comments : 无效值异常
 * 
 */
public class InvalidValueException extends RuntimeException {

    private static final long serialVersionUID = -6159559484976543473L;

    public InvalidValueException(String message, Throwable cause) {
	super(message, cause);
    }

    public InvalidValueException(String message) {
	super(message);
    }

    public InvalidValueException(Throwable cause) {
	super(cause);
    }

    public InvalidValueException() {
	super();
    }
}
