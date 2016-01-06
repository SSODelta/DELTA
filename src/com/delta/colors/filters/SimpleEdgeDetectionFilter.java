package com.delta.colors.filters;

public class SimpleEdgeDetectionFilter extends ConvolutionFilter {

	public SimpleEdgeDetectionFilter() {
		super(new double[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}});
	}

}
