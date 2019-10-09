package prCalculator_2;

public class Peak {

		private double waveNumber, absValue;
		private String fileName;
		private int peakIndex;
		
		public Peak(int i, Spectrum s) {
			this.waveNumber = s.getWaveNumbers().get(i);
			this.absValue = s.getAllAbsValues().get(i);
			this.fileName = s.getFile().getName();
			this.peakIndex = i;
		}
		
		public Peak() {
			this.waveNumber = 0;
			this.absValue = 0;
			this.fileName = "";
		}

		public double getWaveNumber() {
			return waveNumber;
		}

		public double getAbsValue() {
			return absValue;
		}

		public String getFileName() {
			return fileName;
		}	
		
		public int getPeakIndex() {
			return peakIndex;
		}
}
	

