package com.system.common;

public class DaeMonThread extends Thread{
	protected boolean stopped = false;

    public DaeMonThread() {
        super();
        setDaemon(true);
    }

    public DaeMonThread(Runnable runnable) {
        super(runnable);
        setDaemon(true);
    }

    public DaeMonThread(String name) {
        super(name);
        setDaemon(true);
    }

    public boolean isStopped() {
        return stopped;
    }

    public void startThread() {
        start();
    }

    public synchronized void stopThread() {
        stopped = true;
    }
}
