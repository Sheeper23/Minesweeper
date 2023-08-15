import javax.swing.*;

public class MinesweeperWindow extends JFrame {
	private MinesweeperPanel gridPanel;
	
	public MinesweeperWindow(int size, int mines) {
		super("Minesweeper");
		gridPanel = new MinesweeperPanel(size, mines);
		
		
		pack();
		int titlebar = getInsets().top;
		
		
		setSize(gridPanel.getDIMENS(), gridPanel.getDIMENS() + titlebar);
		add(gridPanel);
		
		
		
        setLocation(450, 50);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
		
		
	}
}
