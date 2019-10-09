package prCalculator_2;

import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plot {
	
	public JFreeChart plotSpectrum(Spectrum s) {
		XYSeries dataSeries = new XYSeries("");
		s.setAllAbsValues(s.lowerBaseline());		// this seems a bit roundabout; revisit later?
		
		//Creates a dataset to use in the plot
		for(int i = 0; i < s.getWaveNumbers().size(); i++) {
			XYDataItem values = new XYDataItem(s.getWaveNumbers().get(i), s.getAllAbsValues().get(i));
			dataSeries.add(values);
		}
		XYSeriesCollection dataSet = new XYSeriesCollection();
		 dataSet.addSeries(dataSeries);
		 
		// Creates and customizes the plot
		JFreeChart chart = ChartFactory.createXYLineChart(null, "Wavenumber", "Absorbance", dataSet, PlotOrientation.VERTICAL,false, false, false);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		ValueAxis domainAxis = plot.getDomainAxis();
		Font axisFont = new Font("Tahoma", Font.BOLD, 11);
		Font tickLabelFont = new Font("Tahoma", Font.PLAIN, 9);
		domainAxis.setLabelFont(axisFont);
		domainAxis.setTickLabelFont(tickLabelFont);
		rangeAxis.setLabelFont(axisFont);
		rangeAxis.setTickLabelFont(tickLabelFont);
		domainAxis.setInverted(true);
		domainAxis.setRange(400,4000);
		
		// Add an extra margin to the plot to accomodate peak labels
		double extraMargin = (rangeAxis.getUpperMargin())*3;
		rangeAxis.setUpperMargin(extraMargin);
				
		return chart;
   }
}
