package pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener { 
	final int MENU = 0;
    final int GAME = 1;
    final int END = 2;
    int currentState = MENU;
    Font titleFont;
    Font titleFont1;
    Font titleFont2;
    Timer frameDraw;
    Timer alienSpawn;
    Rocketship rocket = new Rocketship(250,700,50,50);
    ObjectManager manage = new ObjectManager(rocket);
    public static BufferedImage image;
    public static BufferedImage image2;
    public static BufferedImage image3;
    public static BufferedImage image4;
    public static BufferedImage image5;
    
   	public static boolean needImage = true;
   	public static boolean gotImage = false;	
	@Override
	public void paintComponent(Graphics g){
		if(currentState == MENU){
		    drawMenuState(g);
		}else if(currentState == GAME){
		    drawGameState(g);
		}else if(currentState == END){
		    drawEndState(g);
		}
	}
	GamePanel() {
    	titleFont = new Font("Arial", Font.PLAIN, 48);
    	titleFont1 = new Font("Arial", Font.PLAIN, 26);
    	titleFont2 = new Font("Arial", Font.PLAIN, 26);
    	frameDraw = new Timer(1000/60,this);
        frameDraw.start();
        image = loadImage ("space.jpg");
        image2 = loadImage("loss.png");
        image3 = loadImage("boss.png");
        image4 = loadImage("title.png");
        image5 = loadImage("end.png");
    }
	void startGame() {
		 alienSpawn = new Timer(1000 , manage);
		 alienSpawn.start();
	}
	void updateMenuState() {
    	if (rocket.isActive==false) {
			currentState = END;
		}
    }
    void updateGameState() {
    	rocket.move();
    	manage.update();
    }
    void updateEndState() {
    	
    }
    void drawMenuState(Graphics g) {
    	if (gotImage) {
    		g.drawImage(image, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    		g.drawImage(image4, 0, 150, runner.WIDTH, 100, null);
    	} else {
    		g.setColor(Color.BLACK);
    		g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	}
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press ENTER to start", 120, 400);
    	g.setFont(titleFont2);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press SPACE for instructions", 75, 600);
    }
    void drawGameState(Graphics g) {
    	if (gotImage) {
    		g.drawImage(image, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    		g.drawImage(image3, 0, 0, runner.WIDTH, 200, null);
    	} else {
    		g.setColor(Color.BLACK);
    		g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	}
    	manage.draw(g);
    }
    void drawEndState(Graphics g)  {
    	if (gotImage) {
    		g.drawImage(image2, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    		g.drawImage(image5, 0, 50, runner.WIDTH, 150, null);
    	} else {
    		g.setColor(Color.RED);
    		g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	}
    	int lifeScore = manage.getlife() + 1;
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("You killed enemies", 100, 400);
    	g.setFont(titleFont2);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press ENTER to restart", 75, 600);
    	g.drawString("Life: " + lifeScore, 75, 650);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(currentState == MENU){
		    updateMenuState();
		}else if(currentState == GAME){
		    updateGameState();
		}else if(currentState == END){
		    updateEndState();
		}
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (currentState == END) {
		    	alienSpawn.stop();
		        currentState = MENU;
		        rocket = new Rocketship(250,700,50,50);
				manage = new ObjectManager(rocket);
		    } else if(currentState == MENU) {
		    	startGame();
		    	currentState++;
		    } else {
		        currentState++;
		    }
		}
		if (currentState == GAME) {
			int life = manage.getlife();
			if (life < 0) {
				currentState++;
			}
			if (e.getKeyCode()==KeyEvent.VK_UP) {
			    rocket.Up = true;
			    if (rocket.y <= 0) {
					rocket.y = 0;
				}
			} if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			    rocket.Down = true;
			    if (rocket.y >= runner.HEIGHT-rocket.height-25) {
					rocket.y = runner.HEIGHT-rocket.height-25;
				}
			} if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			    rocket.Left = true;
			    if (rocket.x <= 0) {
					rocket.x = 0;
				}
			} if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			    rocket.Right = true;
			    if (rocket.x >= runner.WIDTH-rocket.width) {
					rocket.x = runner.WIDTH-rocket.width;
				}			
			} if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				manage.addProjectile(rocket.getProjectile());
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_UP) {
		    rocket.Up = false;
		} if (e.getKeyCode()==KeyEvent.VK_DOWN) {
		    rocket.Down = false;
		   
		} if (e.getKeyCode()==KeyEvent.VK_LEFT) {
		    rocket.Left = false;
		    
		} if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
		    rocket.Right = false;	
		}
	}
	BufferedImage loadImage(String imageFile) { 
			try {
	        	gotImage = true;
	        	return ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	        } catch (Exception e) {
	            
	        }
	    
	    return null;
	}
}
