package connectFour;

public interface ConnectFourListener {
	void updateBoardView(Colors[][] board);
	void statusChanged(Status status);
	void ChangeTurn(Colors curPlayer);
}
