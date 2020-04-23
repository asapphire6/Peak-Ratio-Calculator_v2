package prcalculatorv2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Table {

	public File tableToFile (List<PeakRatio> list) throws IOException {
		String line = null;
		FileWriter fileWriter = new FileWriter("Untitled.txt");
		BufferedWriter buffer = new BufferedWriter(fileWriter);
		try {
			int lineLength = longestFileNameLength(list);
			buffer.write(String.format("%" + lineLength + "s", "File Names") + "       " + "Peaks" + "       " + "Ratios");
			buffer.newLine();
			buffer.write(String.format("%" + lineLength + "s","-------------") + "    " + "-----------" + "   " + "--------");
			buffer.newLine();
			buffer.newLine();
			
			for (int i = 0; i < list.size(); i++) {
				// Formats the file names so that they are all justified to the right
				String fname = String.format("%" + lineLength + "s", list.get(i).getFileName());
				line = (fname + "    " + "(" + list.get(i).getLabel() + ")" + "    " + list.get(i).getRatio());
				buffer.write(line);
				buffer.newLine();
			}
				buffer.close();
		} catch(IOException e)
			{System.out.println("A write error has occurred");}
		File file = new File("Untitled.txt");
	return file;
	}
	
	// This method finds the longest fileName in the selected file list and returns its length
	private int longestFileNameLength(List<PeakRatio> peakRatios) {
		int length = 0;
		for(PeakRatio p : peakRatios) {
			if(p.getFileName().length() > length) {
				length = p.getFileName().length();
			}
		}
		return length;
	}
}