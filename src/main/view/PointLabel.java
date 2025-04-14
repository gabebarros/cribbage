package main.view;

import javax.swing.JLabel;
import main.controller.Observer;

public class PointLabel extends JLabel implements Observer {

	@Override
	public void newNumber(int number) {
		this.setText("Points: " + number);	
	}

}
