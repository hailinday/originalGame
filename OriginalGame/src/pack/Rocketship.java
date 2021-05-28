package pack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Rocketship extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	public boolean Up = false;
	public boolean Down = false;
	public boolean Left = false;
	public boolean Right = false;
	Rocketship (int x, int y, int width, int height) {
		super(x,y,width,height);
		speed = 5;
		if (needImage) {
		    loadImage ("rocket.png");
		}
	}
	void draw(Graphics g) {
        if (gotImage) {
        	g.drawImage(image, x, y, width, height, null);
        } else {
        	g.setColor(Color.BLUE);
        	g.fillRect(x, y, width, height);
        }
	}
	void move() {
		if (Up) {
			y-=speed;
		}
		if (Down) {
			y+=speed;
		}
		if (Left) {
			x-=speed;
		}
		if (Right) {
			x+=speed;
		}
		super.update();
	}
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
	//public Projectile getProjectile() {
   //     return new Projectile(x+width/2, y, 10, 10);
	//} 
}
