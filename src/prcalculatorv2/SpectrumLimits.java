package prcalculatorv2;

import java.util.ArrayList;

public class SpectrumLimits {
	
	public static double minValue(ArrayList<Double> absValues) {
		double minValue = 1;
		
		for(double v : absValues) {
			if (v < minValue) {
				minValue = v;
			}
		}
		return minValue;
	}
	
	public static double maxValue(ArrayList<Double> absValues) {
		double maxValue = 0;
		
		for(double v : absValues) {
			if (v > maxValue) {
				maxValue = v;
			}
		}
		return maxValue;
	}
}
