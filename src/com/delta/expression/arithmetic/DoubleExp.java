package com.delta.expression.arithmetic;

import java.util.Map;

import com.delta.expression.Expression;
import com.delta.expression.Operator;

public class DoubleExp extends Operator<Double> {

	public DoubleExp(Expression<Double> a, Expression<Double> b){
		super(a,b);
	}
	
	@Override
	public Double evaluate(Map<String, Double> table) {
		return Math.pow(getArgument(0).evaluate(table), 
						getArgument(1).evaluate(table));
	}

	@Override
	public int getRank() {
		return 2;
	}
}
