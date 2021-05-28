package pack;
import javax.swing.JFrame;

public class gameRunner {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 800;
	JFrame frame = new JFrame();
	GamePanel game = new GamePanel();
		
		
	public static void main(String[] args) {
		gameRunner runner = new gameRunner();
		runner.setup();
	}
		
	void setup() {
		//frame.add(game);
		frame.setVisible(true);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(game);
	}
}

