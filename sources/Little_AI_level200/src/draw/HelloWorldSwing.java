package draw;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HelloWorldSwing {

	public static void placeComponenet(JPanel panel) {
		
		panel.setLayout(null);
		JLabel userLabel = new JLabel("User:");
		userLabel.setBounds(10,20,80,25);
		panel.add(userLabel);
		
		JTextField userText = new JTextField(20);
		userText.setBounds(100,20,165,25);
		panel.add(userText);
		
		JLabel passLabel = new JLabel("password");
		passLabel.setBounds(100,50,165,25);
		panel.add(passLabel);
		
		JPasswordField passField = new JPasswordField(20);
		passField.setBounds(100,50,165,25);
		panel.add(passField);
		
		JButton button = new JButton("submit");
		button.setBounds(10,80,80,25);
		panel.add(button);
		
		
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame frame = new JFrame("hello world");
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.add(panel);
		
		placeComponenet(panel);
		frame.setVisible(true);
		
	}

}
