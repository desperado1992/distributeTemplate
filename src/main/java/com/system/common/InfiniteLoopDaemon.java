package com.system.common;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-2 上午11:36:12 
 * @function:后台进程 挂起 wait notify
 */
public abstract class InfiniteLoopDaemon extends Thread{
    
	private boolean isStop=false;
	
	{
		setDaemon(true);
		start();
	}
	
	@Override public void run() {
		while (!isStop) loop();
	}
	//轮训 起 使用线程池处理
	protected abstract void loop();
	
	public void stopThread() {
		isStop=true;
	}
}
