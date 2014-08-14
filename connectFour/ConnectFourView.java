package connectFour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConnectFourView implements ConnectFourListener{
	
	private final ConnectFourModel model;
	
	private static final int ROW = 6;
	private static final int COL = 7;
	
	private JFrame frame;
	
	private JPanel ctrPanel;
	private JButton startBtn;
	private JButton resetBtn;
	private JCheckBox AIBox;
	
	private JPanel msgPanel;
	private JLabel infoLabel;
	private JLabel turnLabel;
	
	private JPanel boardPanel;
	private RoundButton[][] boardBtns = new RoundButton[ROW][COL];
			
	public ConnectFourView(ConnectFourModel gameModel) {
		model = gameModel;
		
		//control panel
		ctrPanel = new JPanel(new GridLayout(3, 1));
		startBtn = new JButton("Start");
		startBtn.addActionListener(new StartListener());
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ResetListener());
		AIBox = new JCheckBox("Play with Computer");
		AIBox.addActionListener(new AIListener());
		ctrPanel.add(startBtn);
		ctrPanel.add(resetBtn);
		ctrPanel.add(AIBox);
		
		//message panel
		msgPanel = new JPanel(new GridLayout(1, 2));
		infoLabel = new JLabel("Ready to go. Press Start to play.");
		turnLabel = new JLabel("Turn: " + model.curPlayer.toString());
		msgPanel.add(infoLabel);
		msgPanel.add(turnLabel);
		
		//boardPanel
		boardPanel = new JPanel(new GridLayout(ROW, COL));
		ButtonListener bl = new ButtonListener();
		for (int r = ROW - 1; r >= 0; r--) {
			for (int c = 0; c < COL; c++) {
				final RoundButton newPiece = new RoundButton(r, c);
				newPiece.addActionListener(bl);
				newPiece.setEnabled(false);
				boardBtns[r][c] = newPiece;
				boardPanel.add(newPiece);
			}
		}
		
		frame = new JFrame("Connect Four Game");
		frame.setLayout(new BorderLayout());
		frame.add(msgPanel, BorderLayout.NORTH);
		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(ctrPanel, BorderLayout.EAST);
		frame.pack();
		frame.setLocale(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(null, "Press Start to play");
	}
	
	@Override
	public void updateBoardView(Colors[][] board) {
		for (int r = 0; r < ROW; r++) {
			for (int c = 0; c < COL; c ++) {
				switch(board[r][c]) {
				case BLUE: 
					boardBtns[r][c].setBackground(Color.BLUE); break;
				case RED: 
					boardBtns[r][c].setBackground(Color.RED); break;
				case EMPTY: 
					boardBtns[r][c].setBackground(Color.WHITE); break;
				}
				boardBtns[r][c].repaint();
			}
		}
	}

	@Override
	public void statusChanged(Status status) {
		switch (status) {
		case READY:
			infoLabel.setText("Ready to go. Press Start to play."); break;
		case ONGOING: 
			infoLabel.setText("Game is on. Choose a column to drop your piece"); break;
		case RED_WIN: 
			infoLabel.setText("Red wins"); 
			break;
		case BLUE_WIN: 
			infoLabel.setText("Blue wins");
			break;
		case DRAW: 
			infoLabel.setText("It's a draw");
			break;
		default: break;
		}		
	}

	@Override
	public void ChangeTurn(Colors curPlayer) {
		turnLabel.setText("Turn: " + curPlayer.toString());
	}
		
	@Override
	public void gameOver(Status status) {
		JOptionPane.showMessageDialog(null, status.toString());
		setBoardEnabled(false);
		startBtn.setEnabled(false);
	}
	
	void setBoardEnabled(boolean enabled) {
		for (int r = 0; r < ROW; r++) {
			for (int c = 0; c < COL; c++) {
				boardBtns[r][c].setEnabled(enabled);
			}
		}
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int col = ((RoundButton) e.getSource()).col;
			model.makeMove(col);
		}
	}
	
	class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setBoardEnabled(true);
			startBtn.setEnabled(false);
			AIBox.setEnabled(false);
			model.start();
		}
	}
	
	class ResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.reset();
			startBtn.setEnabled(true);
			AIBox.setEnabled(true);
			setBoardEnabled(false);
		}
	}
	
	class AIListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.enableComputer(AIBox.isSelected() ? true: false);
		}
	}
}


