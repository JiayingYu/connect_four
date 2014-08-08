package connectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectFourModel {
	private Board board;
	private Status status = Status.ONGOING;
	private Color player1 = Color.RED;
	private Color player2 = Color.BLUE;
	private Color curPlayer;
	private boolean enableComputer = true;
	private List<ConnectFourListener> listeners = new ArrayList<ConnectFourListener>();

	public ConnectFourModel(boolean enableComputer) {
		board = new Board();
		this.enableComputer = enableComputer;
		randomStartPlayer();
	}
	
	public void register(ConnectFourListener listener) {
		listeners.add(listener);
	}
	
	public void unregister(ConnectFourListener listener) {
		listeners.remove(listener);	}

	private void randomStartPlayer() {
		Random rd = new Random();
		if (rd.nextInt(2) == 0) {
			curPlayer = player1;
		} else {
			curPlayer = player2;
		}
	}
	
	private void start() {
		board = new Board();
		status = Status.ONGOING;
	}

	private void changeTurn() {
		curPlayer = curPlayer == Color.RED ? Color.BLUE : Color.RED;

	}

	private void makeMove(int col) {
		checkStatus();
		if (!board.validateStep(col)) {
			throw new IllegalArgumentException("Step not valid");
		}

		int row = board.availableSpace(col);
		board.dropPiece(row, col, curPlayer);

		if (board.checkDraw()) {
			status = Status.DRAW;
			return;
		}

		if (board.checkConnectFour(curPlayer, row, col)) {
			status = (curPlayer == Color.RED ? Status.RED_WIN : Status.BLUE_WIN);
			return;
		}

		changeTurn();

		if (enableComputer) {
			computerMove();
		}
	}

	private void computerMove() {
		int[] nextMove;
		checkStatus();

		int[] explore = exploreNextMove();
		if (explore[0] != -1) {
			nextMove = explore;
		} else {
			nextMove = computeRandomMove();
		}

		board.dropPiece(nextMove[0], nextMove[1], curPlayer);
		
		if (board.checkDraw()) {
			status = Status.DRAW;
			return;
		}

		if (board.checkConnectFour(curPlayer, nextMove[0], nextMove[1])) {
			status = (curPlayer == Color.RED ? Status.RED_WIN : Status.BLUE_WIN);
			return;
		}
		changeTurn();
	}

	private int[] computeRandomMove() {
		Random rd = new Random();
		int col = rd.nextInt(7);
		int row;
		while (!board.validateStep(col)) {
			col = rd.nextInt(7);
		}
		row = board.availableSpace(col);
		int[] coordinate = { row, col };
		return coordinate;
	}

	private int[] exploreNextMove() {
		for (int col = 0; col < 7; col++) {
			if (board.validateStep(col)) {
				int row = board.availableSpace(col);
				if (board.checkConnectFour(player1, row, col)
						|| board.checkConnectFour(player2, row, col)) {
					return new int[] {row, col};
				}
			}
		}
		return new int[] {-1, -1};
	}

	private void checkStatus() {
		if (status != Status.ONGOING)
			throw new IllegalArgumentException("Game is over. No more steps");
	}


}
