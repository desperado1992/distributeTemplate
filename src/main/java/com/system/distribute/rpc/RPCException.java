package com.system.distribute.rpc;

public class RPCException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -880402602425769465L;
	private RPCSession session;

	public RPCException(RPCSession session,Throwable cause) {
		super(cause);
		this.session = session;
	}
    
    
	
	
}
