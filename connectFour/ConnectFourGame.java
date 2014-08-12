package connectFour;

public class ConnectFourGame {
	public static void main(String[] args) {
		ConnectFourModel model = new ConnectFourModel();
		ConnectFourView gameView = new ConnectFourView(model);
		model.register(gameView);
		model.start();
	}
}
