package com.skcc.reuben.bean;

public class ReubenBean<T> {

	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public ReubenBean(T bean) {
		this.type = (Class<T>) bean.getClass();
		
	}
}
