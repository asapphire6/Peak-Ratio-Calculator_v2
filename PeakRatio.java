package prCalculator_2;

public class PeakRatio {
	private Double ratio;
	private double absValue1, absValue2;
	private String label, fileName;
	
	public PeakRatio(Peak peak1, Peak peak2) {
		this.absValue1 = peak1.getAbsValue();
		this.absValue2 = peak2.getAbsValue();
		this.fileName = peak1.getFileName();
		this.ratio = (Math.round((absValue1 / absValue2) * 10000.0) / 10000.0);		// gives a result rounded up to 4 decimal places
		this.label = Integer.toString(((Number)peak1.getWaveNumber()).intValue()) + ":" + Integer.toString(((Number)peak2.getWaveNumber()).intValue());
	}

	public Double getRatio() {
		return ratio;
	}

	public double getAbsValue1() {
		return absValue1;
	}

	public double getAbsValue2() {
		return absValue2;
	}

	public String getLabel() {
		return label;
	}

	public String getFileName() {
		return fileName;
	}
	
	
}
