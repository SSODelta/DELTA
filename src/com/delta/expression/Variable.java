package com.delta.expression;

import java.util.Map;

public abstract class Variable<E> extends Expression<E> {

	private String tag;
	
	public Variable(String tag){
		this.tag = tag;
	}
	
	@Override
	public E evaluate(Map<String, E> table) {
		return table.get(tag);
	}

}
