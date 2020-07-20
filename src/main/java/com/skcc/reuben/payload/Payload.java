package com.skcc.reuben.payload;

import java.util.Objects;

public abstract class Payload {

	public enum Type {
		EMPTY, SINGLE, MULTI;
	}
	
	protected final Object value;
	protected Type type;
	
	private Payload() {
        this.value = null;
    }
	
	private Payload(Object value) {
        this.value = Objects.requireNonNull(value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Payload of(T[] value) {
    	return value == null || value.length == 0 ? new Empty() : value.length == 1 ? new Single(value[0]) : new Multi(value);
    }
    
	public abstract <T> T get();
    
    private static class Single<T> extends Payload {

    	
		public Single(T t) {
			super(t);
			super.type = Type.SINGLE;
		}
		
		@SuppressWarnings("unchecked")
		public T get() {
			return (T)super.value;
		}
    	
    }
    
    private static class Multi<T> extends Payload {
    	
		public Multi(T[] t) {
			super(t);
			super.type = Type.MULTI;
		}
    	
    	@SuppressWarnings("unchecked")
		public T[] get() {
			return (T[])super.value;
		}
    }
    
    private static class Empty extends Payload {
    	
    	public Empty() {
    		super();
    		super.type = Type.EMPTY;
		}
    	
    	public <T> T get() {
    		throw new UnsupportedOperationException("Empty Payload does not support method 'get'.");
    	}
    }
    
}
