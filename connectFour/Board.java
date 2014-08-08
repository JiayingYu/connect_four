package connectFour;

class Board {
	final int row = 6;
	final int col = 7;
	Color[][] board = new Color[row][col];
	private int pieceNum = 0;

	Board() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j] = Color.EMPTY;
			}
		}
	}

	boolean validateStep(int col) {
		if (col >= 0 && col < this.col && board[5][col] == Color.EMPTY)
			return true;
		else
			return false;
			
	}

	void dropPiece(int row, int col, Color color) {
		if (board[row][col] == Color.EMPTY)
			board[row][col] = color;
		else
			throw new IllegalArgumentException("Invalid step! The column is full");
	}
	
	int availableSpace(int col) {
		int row = -1;
		for (int i = 0; i < 6; i++) {
			if (board[i][col] == Color.EMPTY) {
				row = i;
			}
		}
		return row;
	}

	boolean checkDraw() {
		return pieceNum == 42;
	}

	boolean checkConnectFour(Color color, int row, int col) {
		return (verConnection(color, row, col) &&
		horConnection(color, row, col) &&
		diagConnection(color, row, col));
	}

	private boolean verConnection(Color color, int row, int col) {
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

	private boolean horConnection(Color color, int row, int col) {
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

	private boolean diagConnection(Color color, int row, int col) {
		int connection = 1;
		outerloop: for (int i = row - 1; i >= 0; i--) {
			for (int j = col - 1; j >= 0; j--) {
				if (board[i][j] == color) {
					++connection;
					if (connection == 4) {
						return true;
					}
				} else {
					break outerloop;
				}
			}
		}

		outerloop: for (int i = row + 1; i < 6; i++) {
			for (int j = col + 1; j < 7; j++) {
				if (board[i][j] == color) {
					++connection;
					if (connection == 4) {
						return true;
					}
				} else {
					break outerloop;
				}
			}
		}
		return false;
	}
}
