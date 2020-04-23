package prcalculatorv2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Spectrum {

	private ArrayList<Double> waveNumbers, absValues;
	private File file;
	
	public ArrayList<Double> getWaveNumbers() {
		return waveNumbers;
	}

	public ArrayList<Double> getAllAbsValues(){
		return absValues;
	}
	
	public void setAllAbsValues(ArrayList<Double> a){
		this.absValues = a;
	}
	
	public double getAbsValue(double matchedPeakLocation1) {
		double absValue = absValues.get((int) matchedPeakLocation1);
		return absValue;
	}

	public File getFile() {
		return file;
	}

	public Spectrum (File file, ArrayList<Double> waveNumbers, ArrayList<Double> absValues) {
		this.file = file;
		this.waveNumbers = waveNumbers;
		this.absValues = absValues;
	}
	
	public Spectrum (File file, ArrayList<Double> absValues) {
		this.file = file;
		this.absValues = absValues;
	}
	
	
	private ArrayList<Integer> findPeakIndeces() {
		// this method returns a list of index locations corresponding to peaks in the spectrum
		// the locations can then be used as indeces to obtain specific peak absorbance values and wavenumbers
		
		ArrayList<Integer> peakIndeces = new ArrayList<>();
		ArrayList<Double> absValuesBC = lowerBaseline();
	
		// Find peaks in the spectrum
		for (int i = 30; i < waveNumbers.size()-30; i++) {
			// Calculate minimum peak prominences (from baseline - 4.6% and compared to other peaks - 0.7%, to remove noise)
			double baselineProminence = SpectrumLimits.maxValue(absValuesBC)*0.046;	
			double peakProminence = SpectrumLimits.maxValue(absValuesBC)*0.008;
			if(absValuesBC.get(i-1) < absValuesBC.get(i) && absValuesBC.get(i) > absValuesBC.get(i+1) 
					&& absValuesBC.get(i) > baselineProminence) {
				double diff1 = Math.abs(absValuesBC.get(i) - absValuesBC.get(i-30));
				double diff2 = Math.abs(absValuesBC.get(i) - absValuesBC.get(i+30));
				if(diff1 > peakProminence || diff2 > peakProminence) {
					peakIndeces.add(i);
				}
			}
		}
		return peakIndeces;
	}
	
	public List<Peak> findPeaks(){
		List<Peak> peakList = new ArrayList<>();
		for (int i : findPeakIndeces()) {
			peakList.add(new Peak(i, this));
		}
		return peakList;
	}

	public ArrayList<Double> lowerBaseline() {
		// Lower the baseline to the average absorbance value for the 2200-2000 cm-1 region
		double baseline = calculateBaseline(findBaselineIdx());
		ArrayList<Double> correctedSpectrum = new ArrayList<>();
		for(double v : absValues) {
			correctedSpectrum.add(v - baseline);
		}
		return correctedSpectrum;
	}
	
	// Returns the indeces of the region between 2200-2000 cm-1
	private ArrayList<Integer> findBaselineIdx() {
		ArrayList<Integer> regionIdx = new ArrayList<>();
		for (int i = 0; i < waveNumbers.size(); i++) {
			if(waveNumbers.get(i) <= 2200 && waveNumbers.get(i) >= 2000) {
				regionIdx.add(i);
			}
		}
		return regionIdx;
	}
	
	private double calculateBaseline(ArrayList<Integer> regionIdx) {
		double sum = 0;												// need to find a way around negative values?
		for (int i : regionIdx) {
			sum += absValues.get(i);
		}
		double avrgBaseline = sum/regionIdx.size();
		return avrgBaseline;
	}
	
	// Applies a 0-1 normalization to the entire spectrum using the intensity value of the highest peak
//	private ArrayList<Double> normalizeSpectrum() {
//		ArrayList<Double> normalizedSpectrum = new ArrayList<>();
//		for(double v : absValues) {
//			normalizedSpectrum.add(v / SpectrumLimits.maxValue(absValues));
//		}
//		return normalizedSpectrum;
//	}
//	
}
