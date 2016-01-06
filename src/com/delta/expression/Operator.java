package com.delta.expression;

public abstract class Operator<E> extends Expression<E> implements Comparable<Operator<E>>{
	
	private Expression<E>[] args;
	
	@SafeVarargs
	protected Operator(Expression<E>... args){
		this.args = args;
	}
	
	protected Expression<E> getArgument(int i){
		return args[i];
	}
	
	public abstract int getRank();
	
	@Override
	public final int compareTo(Operator<E> o){
		return getRank()-o.getRank();
	}
	
}
