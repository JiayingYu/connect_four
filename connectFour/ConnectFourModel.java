package connectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectFourModel {
	private Board board;
	private Status status = Status.READY;
	private Colors player1 = Colors.RED;
	private Colors player2 = Colors.BLUE;
	private Colors computer;
	Colors curPlayer = player1;
	private boolean enableComputer = false;

	private List<ConnectFourListener> listeners = new ArrayList<ConnectFourListener>();

	public ConnectFourModel() {
		board = new Board();
	}

	void enableComputer(boolean enableComputer) {
		this.enableComputer = enableComputer;
	}

	void start() {
		status = Status.ONGOING;
		fireStatusChanged();
		
		if (enableComputer) {
			Random rd = new Random();
			if (rd.nextInt() % 2 == 0) {
				computer = curPlayer;
				computerMove();
			}
		}
	}

	void reset() {
		board = new Board();
		fireUpdateBoardView();
		status = Status.READY;
		fireStatusChanged();
	}

	private void changeTurn() {
		curPlayer = curPlayer == Colors.RED ? Colors.BLUE : Colors.RED;
		fireChangeTurn();

	}

	void makeMove(int col) {
		checkStatus();
		if (!board.validateStep(col)) {
			throw new IllegalArgumentException("Step not valid");
		}

		int row = board.availableSpace(col);
		board.dropPiece(row, col, curPlayer);
		fireUpdateBoardView();

		checkStatusChange(row, col);
		
		if (status == Status.ONGOING) {
			changeTurn();
			if (enableComputer) {
				computerMove();
			}
		}
	}

	private void checkStatusChange(int row, int col) {
		if (board.checkDraw()) {
			status = Status.DRAW;
			fireStatusChanged();
			fireGameOver();
		}

		if (board.checkConnectFour(curPlayer, row, col)) {
			status = curPlayer == Colors.RED ? Status.RED_WIN : Status.BLUE_WIN;
			fireStatusChanged();
			fireGameOver();
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
		fireUpdateBoardView();

		checkStatusChange(nextMove[0], nextMove[1]);

		if (status == Status.ONGOING)
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
					return new int[] { row, col };
				}
			}
		}
		return new int[] { -1, -1 };
	}

	private void checkStatus() {
		if (status != Status.ONGOING)
			throw new IllegalArgumentException("Game is over. No more steps");
	}

	public void register(ConnectFourListener listener) {
		listeners.add(listener);
	}

	public void unregister(ConnectFourListener listener) {
		listeners.remove(listener);
	}

	private void fireStatusChanged() {
		for (ConnectFourListener l : listeners) {
			l.statusChanged(status);
		}
	}

	private void fireUpdateBoardView() {
		for (ConnectFourListener l : listeners) {
			l.updateBoardView(board.board);
		}
	}

	private void fireChangeTurn() {
		for (ConnectFourListener l : listeners) {
			l.ChangeTurn(curPlayer);
		}
	}
	
	private void fireGameOver() {
		for (ConnectFourListener l : listeners) {
			l.gameOver(status);
		}
	}
}
