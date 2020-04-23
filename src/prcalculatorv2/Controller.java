package prCalculator_2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.JFreeChart;

public class Controller {

	private Viewer theView;
	private Calculator theModel;
	
	public Controller(Viewer theView, Calculator theModel) {
	    this.theView = theView;
	    this.theModel = theModel;
	    this.theView.addLoadButtonListener(new loadButtonListener());
	    this.theView.addMatchButtonListener(new matchButtonListener());
	    this.theView.addCalculateButtonListener(new calculateButtonListener());
	    this.theView.addListListener(new listListener());
	    this.theView.addSaveButtonListener(new saveButtonListener());
	    this.theView.addSDCheckboxListener(new stDevCheckBoxListener());
	}
		
	class loadButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		  	theModel.FileImport();
			theView.spectrumList.setModel(theModel.getFileNames());		
		}
	}
	
	class matchButtonListener implements ActionListener{														// maybe this button is not necessary?
		@Override																								// could just display labels after calculate
		public void actionPerformed(ActionEvent e) {															// button is pressed... 
		  for(int i : theView.spectrumList.getSelectedIndices()) {
		 	Peak matchedPeak1 = theModel.matchPeaks(theView.getFirstPeakLocation(),i);
		  	Peak matchedPeak2 = theModel.matchPeaks(theView.getSecondPeakLocation(),i);
			theView.chartLabels(matchedPeak1.getWaveNumber(), matchedPeak2.getWaveNumber());
		  } 
		}
	}
	
	class calculateButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			for(int i : theView.spectrumList.getSelectedIndices()) {
				//	Calculate peak ratios for the selected spectra
				Peak firstPeak = theModel.matchPeaks(theView.getFirstPeakLocation(), i);
				Peak secondPeak = theModel.matchPeaks(theView.getSecondPeakLocation(), i);
				theModel.setPeakRatios(theModel.calculatePR(firstPeak, secondPeak));

			// Pass the list of peak ratios to the quickCheck panel to display in the View
			theView.quickCheckList.setModel(theModel.getPeakRatioListModel(theModel.getPeakRatios()));
			}
		}
	}
	
	class listListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting() == false) {			    	
			    	if(theView.spectrumList.getSelectedIndices().length == 1) {
			    		JFreeChart plot = theModel.getPlot(theView.spectrumList.getSelectedIndex());
			    		theView.displayPlot(plot);
			    	}
			    }
		}	
	}
	
	class saveButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Table table = new Table();
				File tableFile = table.tableToFile(theModel.getPeakRatios());
				java.awt.Desktop.getDesktop().edit(tableFile);
			} catch (IOException e1) {
				System.out.println("Error opening text editor");
			}
		}
		
	}
	
	class stDevCheckBoxListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
		
	}
}
