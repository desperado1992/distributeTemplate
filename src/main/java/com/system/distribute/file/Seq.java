package com.system.distribute.file;



import java.util.concurrent.atomic.AtomicLong;

public class Seq {
	


	    private static AtomicLong seq = new AtomicLong(0);

	    public static Long cur() {
	        return seq.get();
	    }

	    public static Long nextId() {
	        return seq.incrementAndGet();
	    }

	   

     
	
}
