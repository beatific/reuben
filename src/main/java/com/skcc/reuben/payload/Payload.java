package com.skcc.reuben.payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.bouncycastle.util.Arrays;

public class Payload {

	protected final Object[] values;

	private static final Payload EMPTY = new Payload();

	private Payload() {
		this.values = new Object[0];
	}
	
	private Payload(Object value) {
		List<Object> list = new ArrayList<Object>();
		list.add(value);
		values = list.toArray();
	}

	private Payload(Object[] values) {
		
		this.values = Objects.requireNonNull(values);
	}
	
	public Object[] get() {

		return values;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <P> Payload of(P[] value) {
		return value == null || value.length == 0 ? EMPTY : new Payload(value);
	}
}
