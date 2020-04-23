package prCalculator_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.ui.TextAnchor;


public class Viewer extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 985949068132967899L;
		JScrollPane scrollPane = new JScrollPane();
		JList<String> spectrumList = new JList<>();
		JButton loadButton = new JButton("Load");
		JPanel spectrumPanel = new JPanel();
		JLabel lblPeak_1 = new JLabel("Peak 1");
		JLabel lblPeak_2 = new JLabel("Peak 2");
		NumberFormat peakFormat = NumberFormat.getIntegerInstance();
		JFormattedTextField peakTextField_1 = new JFormattedTextField(peakFormat);
		JFormattedTextField peakTextField_2 = new JFormattedTextField(peakFormat);
		JButton btnMatchPeaks = new JButton("Match Peaks");
		JLabel label = new JLabel("File viewer");
		JLabel label_1 = new JLabel("<html>Enter approx.<br>peak locations</html>");
		JButton btnReset = new JButton("Reset");
		JScrollPane scrollPane_1 = new JScrollPane();
		JList<String> quickCheckList = new JList<String>();
		Label label_2 = new Label("Peak Ratio");
		JButton btnSaveToFile = new JButton("SAVE");
		JCheckBox chckbxStDev = new JCheckBox("Standard deviation");
		Label errorLabel = new Label("");
		JButton btnUnload = new JButton("Unload");
		JButton btnClearAll = new JButton("Clear All");
		JButton btnCalculate = new JButton("CALCULATE");
	
		JFreeChart plot;

	public Viewer() {
	
		//frame = new JFrame("Peak Ratio Calculator");
		this.setResizable(false);
		this.setTitle("Peak Ratio Calculator");
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.setBounds(100, 100, 784, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
								
		scrollPane.setBounds(105, 31, 252, 147);
		this.getContentPane().add(scrollPane);
		
		// This list shows all the files that were loaded. Files can be further selected from the list
		// to plot and calculate peak ratios.
		scrollPane.setViewportView(spectrumList);
				
		// This button opens a file explorer window and imports data from selected files into an Array of Objects of the Spectrum class
		loadButton.setBounds(10, 50, 85, 23);
		this.getContentPane().add(loadButton);
				
		spectrumPanel.setBounds(10, 189, 748, 392);
		spectrumPanel.setBackground(Color.WHITE);
		this.getContentPane().add(spectrumPanel);
		spectrumPanel.setLayout(new BorderLayout(0, 0));
		
		lblPeak_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPeak_1.setBounds(384, 50, 46, 14);
		this.getContentPane().add(lblPeak_1);
		
		
		lblPeak_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPeak_2.setBounds(384, 87, 46, 14);
		this.getContentPane().add(lblPeak_2);
		
		btnMatchPeaks.setBounds(384, 155, 109, 23);
		this.getContentPane().add(btnMatchPeaks);
				
		// Sets the format of the input text fields to only accept integers of 3 or 4 digits
		peakFormat.setMinimumIntegerDigits(3);
		peakFormat.setMaximumIntegerDigits(4);
			
		peakTextField_1.setBounds(431, 46, 62, 23);
		this.getContentPane().add(peakTextField_1);
		
		peakTextField_2.setBounds(431, 84, 62, 23);
		this.getContentPane().add(peakTextField_2);
		
	    label.setBounds(105, 3, 80, 22);
	    label.setFont(new Font("Tahoma", Font.BOLD, 12));
	    this.getContentPane().add(label);
	    
	    label_1.setBounds(384, 3, 109, 41);
	    this.getContentPane().add(label_1);
	    
	    btnReset.setBounds(384, 121, 109, 23);
	    this.getContentPane().add(btnReset);
	    
	    scrollPane_1.setBounds(680, 31, 78, 147);
	    this.getContentPane().add(scrollPane_1);
	    
	    scrollPane_1.setViewportView(quickCheckList);
	    
	    label_2.setBounds(680, 3, 85, 22);
	    label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
	    this.getContentPane().add(label_2);
	    
	    btnSaveToFile.setBounds(542, 155, 89, 23);
	    this.getContentPane().add(btnSaveToFile);
	    
	    chckbxStDev.setBounds(532, 50, 122, 23);
	    this.getContentPane().add(chckbxStDev);
	    
	    errorLabel.setBounds(10, 586, 585, 23);
	    this.getContentPane().add(errorLabel);
	    
		btnUnload.setEnabled(false);
	    btnUnload.setBounds(10, 84, 85, 23);
	    this.getContentPane().add(btnUnload);
	    
	    btnClearAll.setEnabled(true);
	    btnClearAll.setBounds(10, 118, 85, 23);
	    this.getContentPane().add(btnClearAll);
	    
		btnCalculate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCalculate.setBounds(532, 84, 114, 57);
		this.getContentPane().add(btnCalculate);
		
		// Set a new focus traversal policy which goes up-down, left-right
		Vector<Component> order = new Vector<Component>(5);
		order.add(loadButton);
		order.add(peakTextField_1);
		order.add(peakTextField_2);
		order.add(btnMatchPeaks);
		order.add(btnCalculate);
	}
	
	public void addLoadButtonListener(ActionListener listenForLoadButton){
	    loadButton.addActionListener(listenForLoadButton);
	}
	
	public void addMatchButtonListener(ActionListener listenForMatchButton){
		btnMatchPeaks.addActionListener(listenForMatchButton);
	}
	
	public void addCalculateButtonListener(ActionListener listenForCalculateButton){
		btnCalculate.addActionListener(listenForCalculateButton);
	}
		
	public void addListListener(ListSelectionListener listenForListSelection) {	
		spectrumList.addListSelectionListener(listenForListSelection);
    }
	
	public void addSaveButtonListener(ActionListener listenForSaveButton) {
		btnSaveToFile.addActionListener(listenForSaveButton);
	}
	
	public void addSDCheckboxListener(ActionListener listenForSDCheckBox) {
		chckbxStDev.addActionListener(listenForSDCheckBox);
	}
	
	public double getFirstPeakLocation(){
		return ((Number)peakTextField_1.getValue()).doubleValue();
	}
	
	public double getSecondPeakLocation() {
		return ((Number)peakTextField_2.getValue()).doubleValue();
	}
	
	public void chartLabels(double matchedPeakLocation1, double matchedPeakLocation2) {
		try {
			// Make labels visible
			XYItemRenderer renderer = plot.getXYPlot().getRenderer();
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelFont(new Font("Tahoma", Font.PLAIN, 11));
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.CENTER_LEFT,TextAnchor.CENTER_LEFT, -1.57));			// Rotate the labels 90 degrees counterclockwise and centre at the tip of each peak
			renderer.setBaseItemLabelGenerator(new plotLabelGenerator.LabelGenerator(matchedPeakLocation1, matchedPeakLocation2));
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayPlot(JFreeChart chart) {
		plot = chart; 							// this is really stupid but I can't figure out a different way of doing this 
												// (can't access currently displayed chart here and if I pass a new chart in the controller then the generateLabel 
												// method of the LabelGenerator is never called)
		ChartPanel chartPanel = new ChartPanel(plot);
		// Remove the mouse listener which resets the plot if a mouse click event is
		// registered on the chart
		chartPanel.removeMouseListener(chartPanel.getMouseListeners()[1]);
		spectrumPanel.add(chartPanel);
		this.setVisible(true);
	}
}

