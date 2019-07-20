import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;

public class SecretMessagesGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtKey;
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public String encode( String message, int keyVal ) {
		String output = "";
		char key = (char) keyVal;
		for (int x = 0; x < message.length(); x++) {
			char input = message.charAt(x);
			if (input >= 'A' && input <= 'Z') {
				input += key;
				if (input > 'Z') {
					input -= 26;
				}
				if (input < 'A') {
					input += 26;
				}
			} else if (input >= 'a' && input <= 'z') {
				input += key;
				if (input > 'z') {
					input -= 26;
				}
				if (input < 'a') {
					input += 26;
				}
			} else if (input >= '0' && input<= '9') {
				input += (keyVal % 10);
				if (input > '9') {
					input -= 10;
				}
				if (input < '0') {
					input += 10;
				}
			}
			output += input;
		}
		return output;
	}
	public SecretMessagesGUI() {
		getContentPane().setBackground(new Color(47, 79, 79));
		setTitle("Secret Message App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 564, 140);
		getContentPane().add(scrollPane);
		
		txtIn = new JTextArea();
		scrollPane.setViewportView(txtIn);
		txtIn.setWrapStyleWord(true);
		txtIn.setLineWrap(true);
		txtIn.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 210, 564, 140);
		getContentPane().add(scrollPane_1);
		
		txtOut = new JTextArea();
		scrollPane_1.setViewportView(txtOut);
		txtOut.setWrapStyleWord(true);
		txtOut.setLineWrap(true);
		txtOut.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		
		txtKey = new JTextField();
		txtKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					int key = Integer.parseInt(txtKey.getText());
					slider.setValue(key);
				} catch (Exception ex) {
				}
			}
		});
		txtKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtKey.setText("5");
		txtKey.setBounds(269, 176, 46, 20);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setBounds(213, 179, 46, 14);
		getContentPane().add(lblKey);
		
		JButton btnEncodedecode = new JButton("Encode/Decode");
		btnEncodedecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = txtIn.getText();
					int key = Integer.parseInt( txtKey.getText() );
					String output = encode(message, key);
					txtOut.setText(output);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Please enter a whole number between -26 and 26 for the encryption key.");
					txtKey.requestFocus();
					txtKey.selectAll();
				}
			}
		});
		btnEncodedecode.setBounds(325, 175, 119, 23);
		getContentPane().add(btnEncodedecode);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtKey.setText( "" + slider.getValue() );
				String message = txtIn.getText();
				int key = slider.getValue();
				String output = encode(message, key);
				txtOut.setText(output);
			}
		});
		slider.setValue(5);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(13);
		slider.setForeground(new Color(240, 240, 240));
		slider.setMinimum(-26);
		slider.setMaximum(26);
		slider.setPaintLabels(true);
		slider.setBackground(new Color(47, 79, 79));
		slider.setBounds(20, 165, 200, 45);
		getContentPane().add(slider);
		
		JButton btnMoveUp = new JButton("Move Up ^");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = txtOut.getText();
				txtIn.setText(message);
				int key = slider.getValue();
				int revKey = (key * -1);
				txtKey.setText("" + revKey);
				slider.setValue(revKey);
				String output = encode(message, revKey);
				txtOut.setText(output);
			}
		});
		btnMoveUp.setBounds(454, 176, 89, 23);
		getContentPane().add(btnMoveUp);
	}

	public static void main(String[] args) {
		SecretMessagesGUI theApp = new SecretMessagesGUI();
		theApp.setSize(new java.awt.Dimension(600,400));
		theApp.setVisible(true);
	}
}
