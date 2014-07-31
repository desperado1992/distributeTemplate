package com.system.distribute.context;

import java.util.EventListener;





/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:04:54 
 * @function:生命周期
 */
public interface Lifecycle {

	
    public void start()
        throws Exception;

   
    
    public void stop()
        throws Exception;

    
    public boolean isRunning();

   
    public boolean isStarted();

    
    public boolean isStarting();

   
    public boolean isStopping();

    
    public boolean isStopped();

   
    public boolean isFailed();
    

    public void addLifeCycleListener(Lifecycle.Listener listener);

    public void removeLifeCycleListener(Lifecycle.Listener listener);
    

  
    public interface Listener extends EventListener
    {
        public void lifeCycleStarting(Lifecycle event);
        public void lifeCycleStarted(Lifecycle event);
        public void lifeCycleFailure(Lifecycle event,Throwable cause);
        public void lifeCycleStopping(Lifecycle event);
        public void lifeCycleStopped(Lifecycle event);
    }
	
	
}
