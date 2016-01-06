package com.delta.expression;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression<E> {
	
	public abstract E evaluate(Map<String, E> table);
	
	public E evaluate(){
		return evaluate(new HashMap<String, E>());
	}
}
