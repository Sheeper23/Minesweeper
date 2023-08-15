import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MinesweeperMainMenu extends JFrame {
	private BufferedImage logoImage;
	private JLabel logo;
	private JPanel logoPanel;
	private JPanel paramPanel;
	private JComboBox<String> presets;
	private JTextField size;
	private JTextField mines;
	private JButton submit;
	private MinesweeperWindow window;
	
	public MinesweeperMainMenu() {
		super("Minesweeper: Main Menu");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setLocation(650, 200);
		setSize(600, 400);
		
		try {
			logoImage = ImageIO.read(new URL("https://mir-s3-cdn-cf.behance.net/project_modules/disp/db4c5b58268415.59f628033bd08.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logo = new JLabel(new ImageIcon(new ImageIcon(logoImage).getImage().getScaledInstance(450, 150, Image.SCALE_DEFAULT)), JLabel.CENTER);
		logoPanel = new JPanel();
		
		logoPanel.setBackground(Color.GREEN);
		logoPanel.setMaximumSize(new Dimension(600, 200));
		
		paramPanel = new JPanel();
		paramPanel.setBackground(Color.GREEN);
		paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
		
		String[] options = {"Custom", "Dev Intended", "Mini Minesweeper", "Mega Minesweeper"};
		presets = new JComboBox<String>(options);
		presets.setMaximumSize(new Dimension(600, 50));
		
		size = new JTextField("Width/Height");
		mines = new JTextField("Number of Mines");
		
		size.setMaximumSize(new Dimension(150, 50));
		mines.setMaximumSize(new Dimension(150, 50));
		
		size.setEditable(true);
		mines.setEditable(true);
		
		JPanel textBoxes = new JPanel();
		textBoxes.setBackground(Color.GREEN);
		textBoxes.setLayout(new BoxLayout(textBoxes, BoxLayout.X_AXIS));
		textBoxes.add(size);
		textBoxes.add(mines);
		
		submit = new JButton("Play");
		
		
		size.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (size.getText().equals("Width/Height")) size.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (size.getText().equals("")) size.setText("Width/Height");
				
			}
			
		});
		
		mines.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (mines.getText().equals("Number of Mines")) mines.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (mines.getText().equals("")) mines.setText("Number of Mines");
				
			}
			
		});
		
		presets.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String choice = (String)presets.getSelectedItem();
				
				if (choice.equals("Custom")) {
					size.setEditable(true);
					mines.setEditable(true);
					
					
					size.setText("Width/Height");
					mines.setText("Number of Mines");
				}
				else if (choice.equals("Dev Intended")) {
					size.setEditable(false);
					mines.setEditable(false);
					
					size.setText("16");
					mines.setText("40");
				}
				else if (choice.equals("Mini Minesweeper")) {
					size.setEditable(false);
					mines.setEditable(false);
					
					size.setText("8");
					mines.setText("10");
				}
				else if (choice.equals("Mega Minesweeper")) {
					size.setEditable(false);
					mines.setEditable(false);
					
					size.setText("32");
					mines.setText("160");
				}
			}
			
		});
		
		submit.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {  
				    Integer.parseInt(size.getText());
				    Integer.parseInt(mines.getText());
				  } catch(NumberFormatException f){
				    return;  
				}  
				
				
				window = new MinesweeperWindow(Integer.parseInt(size.getText()), Integer.parseInt(mines.getText()));
				
				
			}
		});
		
		
		logoPanel.add(logo);
		
		paramPanel.add(presets);
		paramPanel.add(textBoxes);
		paramPanel.add(submit);
		
		
		add(logoPanel);
		add(paramPanel);
		
		
		
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.GREEN);
		
	}
}
