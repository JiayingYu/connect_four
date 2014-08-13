package connectFour;

class Board {
	static final int ROW = 6;
	static final int COL = 7;
	Colors[][] board = new Colors[ROW][COL];
	private int pieceNum = 0;

	Board() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				board[i][j] = Colors.EMPTY;
			}
		}
	}

	boolean validateStep(int col) {
		if (col >= 0 && col < COL && board[5][col] == Colors.EMPTY)
			return true;
		else
			return false;
			
	}

	void dropPiece(int row, int col, Colors color) {
		if (board[row][col] == Colors.EMPTY)
			board[row][col] = color;
		else
			throw new IllegalArgumentException("Invalid step! The column is full");
	}
	
	int availableSpace(int col) {
		int row = -1;
		for (int i = 0; i < 6; i++) {
			if (board[i][col] == Colors.EMPTY) {
				row = i;
				return row;
			}
		}
		return row;
	}

	boolean checkDraw() {
		return pieceNum == 42;
	}

	boolean checkConnectFour(Colors color, int row, int col) {
		return (verConnection(color, row, col) ||
		horConnection(color, row, col) ||
		diagConnection(color, row, col));
	}

	private boolean verConnection(Colors color, int row, int col) {
		int connection = 1;
		for (int i = row - 1; i >= 0; i--) {
			if (board[i][col] == color) {
				++connection;
				if (connection == 4) {
					return true;
				}
			} else {
				break;
			}
		}
		return false;
	}

	private boolean horConnection(Colors color, int row, int col) {
		int connection = 1;
		for (int j = col - 1; j >= 0; j--) {
			if (board[row][j] == color) {
				++connection;
				if (connection == 4) {
					return true;
				}
			} else {
				break;
			}
		}

		for (int j = col + 1; j < 7; j++) {
			if (board[row][j] == color) {
				++connection;
				if (connection == 4) {
					return true;
				}
			} else {
				break;
			}
		}
		return false;
	}

	private boolean diagConnection(Colors color, int row, int col) {
//		int connection = 1;
//		outerloop: 
//		for (int r = row - 1; r >= 0; r--) {
//			if (col <= 0) break;
//			for (int c = col - 1; c >= 0; c--) {
//				if (board[r][c] == color) {
//					++connection;
//					if (connection == 4) {
//						return true;
//					}
//				} else {
//					break outerloop;
//				}
//			}
//		}
//
//		outerloop: 
//		for (int r = row + 1; r < 6; r++) {
//			for (int c = col + 1; c < 7; c++) {
//				if (board[r][c] == color) {
//					++connection;
//					if (connection == 4) {
//						return true;
//					}
//				} else {
//					break outerloop;
//				}
//			}
//		}
//		return false;
		int connection = 1;
		for (int i = 1; i <= row && i <= col; i++) {
			
		}
	}
}
