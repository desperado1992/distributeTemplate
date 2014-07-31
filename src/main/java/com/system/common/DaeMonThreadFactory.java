package com.system.common;

import java.util.concurrent.ThreadFactory;

public class DaeMonThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
	
}
