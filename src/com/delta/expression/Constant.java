package com.delta.expression;

public abstract class Constant<E> extends Expression<E> {
	
	private E value;

	protected Constant(E value){
		this.value = value;
	}
	
	@Override
	public E evaluate() {
		return value;
	}
	
	
}
