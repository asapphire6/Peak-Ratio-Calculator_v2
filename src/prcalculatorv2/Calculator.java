package prCalculator_2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.JFreeChart;

public class Calculator {
	
 	private static DefaultListModel<String> fileNames = new DefaultListModel<>();
	private static ArrayList<Spectrum> data = new ArrayList<>();
	private static List<PeakRatio> peakRatios = new ArrayList<>();
	
	public void setPeakRatios(PeakRatio pr){
		peakRatios.add(pr);
	}
	
	public List<PeakRatio> getPeakRatios() {
		return peakRatios;
	}

	public ArrayList<Spectrum> FileImport(){
		
		// This class imports selected files into an array of Spectrum objects
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "dpt", "txt");
		chooser.setMultiSelectionEnabled(true);
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		
//		ArrayList<Spectrum> data = new ArrayList<Spectrum>();
				
	    switch (returnVal) {
	    	case JFileChooser.APPROVE_OPTION : 
	    			File[] selectedFiles = chooser.getSelectedFiles();
	    			for(File file : selectedFiles) {
	    				List<String> spectrum = new ArrayList<>();
	    				ArrayList<Double> waveNumbers = new ArrayList<>();
	    				ArrayList<Double> absValues = new ArrayList<>();
	    				try {spectrum = Files.readAllLines(Paths.get(file.getAbsolutePath()),Charset.defaultCharset());
	    				} 
	    				catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    				
	    				char[] testLine = spectrum.get(0).toCharArray();
//	    				System.out.println(testLine);
	    				int columns = 0;
	    				
	    				// Check if the file is comma-separated
	    				char comma = ',';
	    				for(Character currentChar : testLine) {
	    					if(currentChar.equals(comma)) {
	    						columns = 1;
	    					}
	    				}
	    					    				
	    				// Check if the file loaded has 1 or 2 columns
	    				char previousChar = ' ';
	    				for(Character currentChar : testLine) {
	    					if (Character.isWhitespace(currentChar) == false && Character.isWhitespace(previousChar) == true) {
	    						columns++;
	    					}
	    					previousChar = currentChar;
	    				}
	    				
	    				//Split the columns and parse values as doubles into Spectrum objects
	    				if(columns == 2) {
	    					for (String value : spectrum) {
	    						value = value.trim();
	    						String[] splitString = value.split("[\\s,]+");	
	    						double waveNum = Double.parseDouble(splitString[0]);
	    						double absVal = Double.parseDouble(splitString[1]);
	    						waveNumbers.add(waveNum);
	    						absValues.add(absVal);		    				
	    					} 
	    					Spectrum s = new Spectrum(file,waveNumbers, absValues);
	    					data.add(s);	// need to add an if statement to account for loading extra files later
	    				} else if (columns == 1){
	    					for (String value : spectrum) {
	    						double absVal = Double.parseDouble(value);
	    						absValues.add(absVal);
	    					}
	    					Spectrum s = new Spectrum(file, absValues);
	    					data.add(s);
	    				} else {
	    					String errorMsg = "Error: Wrong file format";
	    	    			System.out.println(errorMsg); // need to create error message dialog window here
	    	    			break;
	    				}
	    			} break;
	    			
	     	case JFileChooser.CANCEL_OPTION : 
	    			System.out.println("Import cancelled"); 
	    			data = null; 
	    			break;
	    			
	    	case JFileChooser.ERROR_OPTION :
	    			String errorMsg = "Import error occurred";
	    			System.out.println(errorMsg); // need to create error message dialog window here
	    			data = null; 
	    			break;
	    }
		return data;
	}
	
	public DefaultListModel<String> getFileNames() {
		String name = new String();
		for(int i = 0; i < data.size(); i++) {
			name = data.get(i).getFile().getName();
			name = name.substring(0,name.lastIndexOf("."));
			fileNames.addElement(name);
		}
		return fileNames;
	}
	
	public DefaultListModel<String> getPeakRatioListModel(List<PeakRatio> peakRatio){
		DefaultListModel<String> peakRatioList = new DefaultListModel<>();
		for(PeakRatio p : peakRatio) {
			peakRatioList.addElement(p.getRatio().toString());
		}
		return peakRatioList;
	}
	
	//This method creates a list of Strings of selected fileNames
	public ArrayList<String> createFileList(int[] selectedFileIndeces) {
		ArrayList<String> fileList = new ArrayList<>();
		for(int i : selectedFileIndeces) {
			fileList.add(fileNames.get(i)); 
		}
		return fileList;
	}

	public PeakRatio calculatePR (Peak matchedPeak1, Peak matchedPeak2) {
		return new PeakRatio(matchedPeak1, matchedPeak2);
	}
	
	public Peak matchPeaks(double userLocation, int selectionIndex) {
		// this method returns the peak in a given spectrum 
		// that's the closest match to the user-entered value
		double difference = 0;
		Peak matchedPeak = new Peak();				// will need to check for empty objects later
		// get peaks
		List<Peak> peaks = data.get(selectionIndex).findPeaks();
		double smallestDifference = Math.abs(peaks.get(0).getWaveNumber() - userLocation);		// needs to be initialized here for the if condition later
		for(Peak p : peaks) {
			difference = Math.abs(p.getWaveNumber() - userLocation);
			if(difference < smallestDifference) {
				smallestDifference = difference;
				matchedPeak = p;
			}
		}
		return matchedPeak;
	}
	
	public JFreeChart getPlot(int selectionIndex) {
		Plot plot = new Plot();
		JFreeChart chart = plot.plotSpectrum(data.get(selectionIndex));		
		return chart;
	}
}
