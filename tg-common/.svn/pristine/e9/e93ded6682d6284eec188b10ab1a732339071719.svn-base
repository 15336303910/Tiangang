package cn.plou.component.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Project : tg-ftp-server
 * @File : CustomThreadFactory.java
 * @Author : WangJiWei
 * @Date : 2018年6月15日下午4:21:26
 *
 * @Comments :
 * 
 */
public class CustomThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public CustomThreadFactory() {
	this("default");
    }

    public CustomThreadFactory(String prefix) {
	namePrefix = prefix + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
	Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
	if (t.isDaemon()) {
	    t.setDaemon(false);
	}
	if (t.getPriority() != Thread.NORM_PRIORITY) {
	    t.setPriority(Thread.NORM_PRIORITY);
	}
	return t;
    }
}
