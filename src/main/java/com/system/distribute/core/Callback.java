package com.system.distribute.core;

public interface Callback<T> {

	public <T> T action(ResultSet res);
	
	
}
