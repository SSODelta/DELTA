package com.delta.expression.arithmetic;

import java.util.Map;

import com.delta.expression.Constant;

public class DoubleConstant extends Constant<Double> {

	public DoubleConstant(Double d){
		super(d);
	}
	
	@Override
	public Double evaluate(Map<String, Double> table) {
		return super.evaluate();
	}

}
