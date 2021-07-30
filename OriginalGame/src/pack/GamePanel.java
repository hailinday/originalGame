package pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener { 
	Thread music = playSound("music.wav");
	final int MENU = 0;
    final int GAME = 1;
    final int END = 2;
    final int INST = 3;
    final int WIN = 4;
    int currentState = MENU;
    Font titleFont;
    Font titleFont1;
    Font titleFont2;
    boolean isPlaying = false;
    static Timer frameDraw;
    static Timer rocketSpawn;
    static Timer medSpawn;
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
		} else if(currentState == INST) {
			drawInstructionState(g);
		} else if(currentState == WIN) {
			drawWinState(g);
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
		 rocketSpawn = new Timer(1000 , manage);
		 rocketSpawn.start();
		 medSpawn = new Timer(5000 , manage);
		 medSpawn.start();
	}
	void updateMenuState() {
    	if (rocket.isActive==false) {
			currentState = END;
		}
    	
    }
    void updateGameState() {
    	rocket.move();
    	manage.update();
    	int life = manage.getlife();
		if (life < 0) {
			currentState++;
		}
		int boss = manage.getBoss();
		if (boss >= 20) {
			currentState = WIN;
		}
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
    	int lifeScore = manage.getlife() + 1;
    	int bossScore = manage.getBoss();
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("Life: " + lifeScore, 10, 30);
    	g.drawString("Score: " + bossScore, 10, 55);
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
    	int bossScore = manage.getBoss();
    	int lifeScore = manage.getlife() + 1;
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("You killed enemies", 100, 400);
    	g.setFont(titleFont2);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press ENTER to restart", 75, 600);
    	g.drawString("Score: " + bossScore, 75, 650);
    	g.drawString("Life: " + lifeScore, 75, 680);
    }
    void drawInstructionState(Graphics g) {
    	if (gotImage) {
    		g.drawImage(image, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    		g.drawImage(image4, 0, 150, runner.WIDTH, 100, null);
    	} else {
    		g.setColor(Color.BLACK);
    		g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	}
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press WASD to move", 100, 400);
    	g.drawString("and Space to shoot", 100, 450);
    	g.setFont(titleFont2);
    	g.setColor(Color.RED);
    	g.drawString("Click SPACE for the menu", 75, 600);
    }
    void drawWinState(Graphics g) {
    	if (gotImage) {
    		g.drawImage(image, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    		g.drawImage(image4, 0, 150, runner.WIDTH, 100, null);
    	} else {
    		g.setColor(Color.BLACK);
    		g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	}
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("You Won", 100, 400);
    	g.drawString("Congragulations", 100, 450);
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
		if (currentState == MENU) {
			if (e.getKeyCode()==KeyEvent.VK_SPACE) {
				currentState = INST;
			}
		} else if (currentState == INST) {
			if (e.getKeyCode()==KeyEvent.VK_SPACE) {
				currentState = MENU;
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (currentState == WIN) {
				rocketSpawn.stop();
		        currentState = END;
		        rocket = new Rocketship(250,700,50,50);
				manage = new ObjectManager(rocket);
			}
			if (currentState == END) {
		        currentState = MENU;
		        rocket = new Rocketship(250,700,50,50);
				manage = new ObjectManager(rocket);
		    } else if(currentState == MENU) {
		    	startGame();
		    	currentState++;
		    }
		}
		if (currentState == GAME) {
			if (isPlaying == false) {
				isPlaying = true;
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
				playSound("laser.wav");
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

	private static Thread playSound(String soundFile) {
        String path = "src/pack/";
        File sound = new File(path + soundFile);
        
        Thread t = new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(sound));
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (Exception e) {
                System.out.println("Could not play this sound");
            }
        });
        
        if (sound.exists()) {
            t.start();
        } else {
            System.out.println("File does not exist");
        }
        return t;
    }
}
