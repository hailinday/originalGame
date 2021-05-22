package pack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        //if (needImage) {
        //    loadImage ("space.png");
        //}
    }
	void updateMenuState() {
		
    }
    void updateGameState() {
    	
    }
    void updateEndState() {
    	
    }
    void drawMenuState(Graphics g) {
    	g.setColor(Color.BLUE);
    	g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press ENTER to start", 100, 400);
    	g.setFont(titleFont2);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press SPACE for instructions", 75, 600);
    }
    void drawGameState(Graphics g) {
    //	if (gotImage) {
    //		g.drawImage(image, 0, 0, runner.WIDTH, runner.HEIGHT, null);
    //	} else {
    //		g.setColor(Color.BLACK);
      //  	g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    //	}
    //	manage.draw(g);
    }
    void drawEndState(Graphics g)  {
    	g.setColor(Color.RED);
    	g.fillRect(0, 0, runner.WIDTH, runner.HEIGHT);
    	g.setFont(titleFont1);
    	g.setColor(Color.YELLOW);
    	g.drawString("You killed enemies", 100, 400);
    	g.setFont(titleFont2);
    	g.setColor(Color.YELLOW);
    	g.drawString("Press ENTER to restart", 75, 600);
    	//g.drawString("Score:" + manage.getScore(), 75, 650);
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
		        currentState = MENU;
		    } else {
		        currentState++;
		    }
		}   
		if (currentState == GAME) {
			if (e.getKeyCode()==KeyEvent.VK_UP) {
			    //rocket.up();
			   // if (rocket.y <= 0) {
				//	rocket.y = 0;
				//}
			} else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			  //  rocket.down();
			   // if (rocket.y >= LeagueInvaders.HEIGHT-rocket.height-25) {
				//	rocket.y = LeagueInvaders.HEIGHT-rocket.height-25;
				//}
			} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			   // rocket.left();
			    //if (rocket.x <= 0) {
				//	rocket.x = 0;
				//}
			} else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			    //rocket.right();
			    //if (rocket.x >= LeagueInvaders.WIDTH-rocket.width) {
				//	rocket.x = LeagueInvaders.WIDTH-rocket.width;
				//}			
			} else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				//manage.addProjectile(rocket.getProjectile());
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
