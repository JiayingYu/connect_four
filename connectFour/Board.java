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
		++pieceNum;
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
		int connection = 1;
		for (int i = 1; i <= row && i <= col; i++) {
			if (board[row - i][col - i] == color) {
				if (++connection == 4) return true;
			} else { break; }
		}
		
		for (int i = 1; i + row < 6 && i + col < 7; i++) {
			if (board[row + i][col + i] == color) {
				if (++connection == 4) return true;
			} else { break; }
		}
		
		connection = 1;
		for (int i = 1; i <= col && row + i < 6; i++) {
			if (board[row + i][col - i] == color) {
				if (++connection == 4) return true;
			} else { break; }
		}
		
		for (int i = 1; i <= row && col + i < 7; i++) {
			if (board[row - i][col + i] == color) {
				if (++connection == 4) return true;
			} else { break; }
		}
		
		return false;
	}		
}
