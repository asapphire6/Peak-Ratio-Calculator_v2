package prcalculatorv2;

import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.data.xy.XYDataset;

public class plotLabelGenerator {

static class LabelGenerator implements XYItemLabelGenerator {
		
		double[] matchedPeakLocations = new double[2];
		
		public LabelGenerator(double matchedPeakLocation1, double matchedPeakLocation2) {
			this.matchedPeakLocations[0] = matchedPeakLocation1;
			this.matchedPeakLocations[1] = matchedPeakLocation2;
		}
		
		@Override
		public String generateLabel(XYDataset dataset, int series, int item) {
			Number labelNumber = dataset.getX(series, item);
			int lNum = labelNumber.intValue();
			String label = null;
			for(int i = 0; i < matchedPeakLocations.length; i++) {
				int filter = ((Number)matchedPeakLocations[i]).intValue();
					if(lNum == filter) {
					label = Integer.toString(lNum);	
					}
			}
		return label;
		}
	}
}
