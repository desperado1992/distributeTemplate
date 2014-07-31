package com.system.distribute.sqlparser;

public class Condition {

	
	private String x;
	
	
	private Relative relative;//关系
	
	
	private String y;
	
	/**
	 * 暂时 先实现 相识 与相等的判断
	 * @author Administrator
	 *
	 */
	public static enum Relative{
		
		EQUAL,//等于 
		
		LIKE;//相识 
		
	}

	public Condition(String x, Relative relative, String y) {
		super();
		this.x = x;
		this.relative = relative;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Condition [x=" + x + ", relative=" + relative + ", y=" + y
				+ "]";
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public Relative getRelative() {
		return relative;
	}

	public void setRelative(Relative relative) {
		this.relative = relative;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	
	
	
	
}
