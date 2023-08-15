import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperPanel extends JPanel {
	private MinesweeperGrid grid;
	private int hoveredCell;
	private boolean showProbabilities;
	private final int DIMENS = 800;
	
	public MinesweeperPanel(int size, int mines) {
		grid = new MinesweeperGrid(size, mines);
		hoveredCell = -1;
		showProbabilities = false;
		MouseListener listener = new MouseListener();
		KeyListener listener2 = new KeyListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		addKeyListener(listener2);
		setLayout(new GridLayout(grid.getSize(), grid.getSize(), 0, 0));
		setFocusable(true);
		requestFocusInWindow();
		updateGrid();
	}
	
	public void updateGrid() {
		removeAll();
		
		if (grid.getIsGameOver()) hoveredCell = -1;
		
		for (int i = 0; i < grid.getSize(); i++) {
			for (int j = 0; j < grid.getSize(); j++) {
				JLabel pane = new JLabel();
				if ((i+j)%2 == 0) {
					pane.setBackground(new Color(157, 209, 83));
					if (i * grid.getSize() + j == hoveredCell) pane.setBackground(new Color(198, 224, 137));
					if (grid.getGrid()[i][j].getIsCleared()) {
						pane.setBackground(new Color(228, 186, 152));
						pane.setText(""+grid.getGrid()[i][j].getAdjacentMines());
						switch (grid.getGrid()[i][j].getAdjacentMines()) {
						case 0:
							pane.setText("");
							break;
						case 1:
							pane.setForeground(new Color(0, 16, 244));
							break;
						case 2:
							pane.setForeground(new Color(0, 115, 23));
							break;
						case 3:
							pane.setForeground(new Color(255, 0, 27));
							break;
						case 4:
							pane.setForeground(new Color(0, 8, 113));
							break;
						case 5:
							pane.setForeground(new Color(122, 0, 13));
							break;
						case 6:
							pane.setForeground(new Color(0, 117, 116));
							break;
						case 7:
							pane.setForeground(new Color(0, 0, 0));
							break;
						case 8:
							pane.setForeground(new Color(117, 117, 117));
							break;
						}
					}
				}
				else {
					pane.setBackground(new Color(148, 203, 77));
					if (i * grid.getSize() + j == hoveredCell) pane.setBackground(new Color(192, 220, 131));
					if (grid.getGrid()[i][j].getIsCleared()) {
						pane.setBackground(new Color(212, 175, 145));
						pane.setText(""+grid.getGrid()[i][j].getAdjacentMines());
						switch (grid.getGrid()[i][j].getAdjacentMines()) {
						case 0:
							pane.setText("");
							break;
						case 1:
							pane.setForeground(new Color(0, 16, 244));
							break;
						case 2:
							pane.setForeground(new Color(0, 115, 23));
							break;
						case 3:
							pane.setForeground(new Color(255, 0, 27));
							break;
						case 4:
							pane.setForeground(new Color(0, 8, 113));
							break;
						case 5:
							pane.setForeground(new Color(122, 0, 13));
							break;
						case 6:
							pane.setForeground(new Color(0, 117, 116));
							break;
						case 7:
							pane.setForeground(new Color(0, 0, 0));
							break;
						case 8:
							pane.setForeground(new Color(117, 117, 117));
							break;
						}
					}
				}
				pane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int)(DIMENS/grid.getSize() * 0.6)));
				if (showProbabilities && grid.getGrid()[i][j].getProbability() >= 0) {
					//System.out.println("Cell at " + (i*grid.getSize() + j) + " has probability " + grid.getGrid()[i][j].getProbability());
					pane.setText(""+grid.getGrid()[i][j].getProbability());
					pane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int)(DIMENS/grid.getSize() * 0.3)));
					pane.setForeground(Color.BLACK);
				}
				
				if (grid.getGrid()[i][j].getIsFlagged()) {
					pane.setText("⚑");
					pane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int)(DIMENS/grid.getSize() * 1.2)));
					pane.setForeground(Color.RED);
				}
				if (grid.getIsGameOver() && grid.getGrid()[i][j].getIsMine()) {
					pane.setText("•");
					
					Random rand = new Random();
					int r = rand.nextInt(236) + 20;
					int g = rand.nextInt(236) + 20;
					int b = rand.nextInt(236) + 20;
					
					pane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int)(DIMENS/grid.getSize() * 1.2)));
					pane.setBackground(new Color(r, g, b));
					pane.setForeground(new Color(r-20, g-20, b-20));
				}
				pane.setHorizontalAlignment(SwingConstants.CENTER);
				pane.setVerticalAlignment(SwingConstants.CENTER);
				pane.setOpaque(true);
				add(pane);
			}
		}
		revalidate();
		repaint();
	}
	
	public int getDIMENS() {
		return DIMENS;
	}
	
	private class MouseListener extends MouseAdapter {
		boolean leftDown = false;
		boolean rightDown = false;
		
		public void mousePressed(MouseEvent e) {
			if (grid.getIsGameOver()) return;
			
			if (e.getButton() == 1) {
				leftDown = true;
			}
			if (e.getButton() == 3) {
				rightDown = true;
			}
			
			if (leftDown && rightDown) {
				grid.chord((int)(e.getPoint().getY() / DIMENS * grid.getSize()), (int)(e.getPoint().getX() / DIMENS * grid.getSize()));
			}
			else if (leftDown) {
				if (!grid.getAreMinesGenerated()) {
					grid.generateMines((int)(e.getPoint().getY() / DIMENS * grid.getSize()), (int)(e.getPoint().getX() / DIMENS * grid.getSize()));
				}
				
				grid.clear((int)(e.getPoint().getY() / DIMENS * grid.getSize()), (int)(e.getPoint().getX() / DIMENS * grid.getSize()));
			}
			else if (rightDown) {
				grid.flag((int)(e.getPoint().getY() / DIMENS * grid.getSize()), (int)(e.getPoint().getX() / DIMENS * grid.getSize()));
			}
			updateGrid();
		}
		
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == 1) {
				leftDown = false;
			}
			if (e.getButton() == 3) {
				rightDown = false;
			}
		}
		
		public void mouseMoved(MouseEvent e) {
			if (grid.getIsGameOver()) return;
			
			hoveredCell = ((int)(e.getPoint().getY() / DIMENS * grid.getSize()) * grid.getSize()) + (int)(e.getPoint().getX() / DIMENS * grid.getSize());
			updateGrid();
		}
		
		public void mouseDragged(MouseEvent e) {
			if (grid.getIsGameOver()) return;
			
			hoveredCell = ((int)(e.getPoint().getY() / DIMENS * grid.getSize()) * grid.getSize()) + (int)(e.getPoint().getX() / DIMENS * grid.getSize());
			updateGrid();
		}
		
		public void mouseExited(MouseEvent e) {
			if (grid.getIsGameOver()) return;
			
			hoveredCell = -1;
			updateGrid();
		}
	}
	
	private class KeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
				showProbabilities = !showProbabilities;
				updateGrid();
			}
			
		}
	}
}

