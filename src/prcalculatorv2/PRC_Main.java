package prCalculator_2;

public class PRC_Main {
	public static void main(String[] args) {

		        Viewer theView = new Viewer();
		        Calculator theModel = new Calculator();
		        Controller theController = new Controller(theView,theModel);
		        theView.setVisible(true);
		    }
// Add code to prevent multiple instances of the app being open at once using JUnique library (already added)
}
