package cn.plou.common.constant;

/**
 * @Project : tg-micro
 * @File : LogicSymbol.java
 * @Author : WangJiWei
 * @Date : 2018年5月31日下午1:42:13
 *
 * @Comments :
 * 
 */
public enum LogicSymbol {
    /***/
    AND("&&"), OR("||");

    private final String symbol;

    LogicSymbol(String sy) {
	this.symbol = sy;
    }

    public String getSymbol() {
	return symbol;
    }

    public static LogicSymbol format(String str) {
	if (AND.symbol.equals(str)) {
	    return AND;
	} else if (OR.symbol.equals(str)) {
	    return OR;
	} else {
	    return null;
	}
    }

    @Override
    public String toString() {
	return symbol;
    }
}
