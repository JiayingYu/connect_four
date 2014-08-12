package connectFour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourView implements ConnectFourListener{
	private final ConnectFourModel model;
	private JFrame frame;
	private static final int ROW = 6;
	private static final int COL = 7;
	
	private JPanel jpMessage;
	private JLabel jlMessage;
	private JLabel jlTurn;
	private JPanel jpBoard;
	private JPanel jpControl;
	private JButton jbtStart;
	private JButton jbtReset;
	private JCheckBox jcbAI;
	private JRButton[][] boardBtns = new JRButton[ROW][COL];
			
	private ConnectFourView(ConnectFourModel gameModel) {
		model = gameModel;
		
		jbtStart = new JButton("Start");
		jbtStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.start();
			}
		});
		
		jbtReset = new JButton("Reset");
		jbtReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.reset();
			}
		});
		
		jcbAI = new JCheckBox("Play with Computer");
		jcbAI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.enableComputer(jcbAI.isSelected() ? true: false);
			}
		});
		
		jpControl = new JPanel(new GridLayout(3, 1));
		jpControl.add(jbtStart);
		jpControl.add(jbtReset);
		jpControl.add(jcbAI);
		
		jpMessage = new JPanel(new GridLayout(1, 2));
		jlMessage = new JLabel("Connect Four");
		jlTurn = new JLabel(model.curPlayer.toString());
		jpMessage.add(jpMessage);
		jpMessage.add(jlTurn);
		
		jpBoard = new JPanel(new GridLayout(ROW, COL));
		for (int i = ROW - 1; i >= 0; i--) {
			for (int j = 0; j < COL; j++) {
				final JRButton piece = new JRButton(i, j);
				piece.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						model.makeMove(piece.col);
					}
				});
				boardBtns[i][j] = piece;
				jpBoard.add(piece);
			}
		}
		
		frame = new JFrame("Connect Four Game");
		frame.setLayout(new BorderLayout());
		frame.add(jpMessage, BorderLayout.NORTH);
		frame.add(jpBoard, BorderLayout.CENTER);
		frame.add(jpControl, BorderLayout.EAST);
		frame.setSize(700, 600);
		frame.setLocale(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void updateBoardView(Colors[][] board) {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j ++) {
				switch(board[i][j]) {
				case BLUE: boardBtns[i][j].setBackground(Color.BLUE); break;
				case RED: boardBtns[i][j].setBackground(Color.RED); break;
				case EMPTY: boardBtns[i][j].setBackground(Color.WHITE); break;
				}
			}
		}
	}

	@Override
	public void statusChanged(Status status) {
		switch (status) {
		case READY: jlMessage.setText("Ready to go. Press start to play."); break;
		case ONGOING: jlMessage.setText("Game is on. Click on column to drop your piece"); break;
		case RED_WIN: jlMessage.setText("Red wins"); break;
		case BLUE_WIN: jlMessage.setText("Blue wins"); break;
		case DRAW: jlMessage.setText("It's a draw"); break;
		default: break;
		}
			
	}

	@Override
	public void ChangeTurn(Colors curPlayer) {
		jlTurn.setText(curPlayer.toString());
	}

}
