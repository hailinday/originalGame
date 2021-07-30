package pack;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.util.Arrays;

public class ObjectManager implements ActionListener {
	Rocketship rocket;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	ArrayList<laser> laser = new ArrayList<laser>();
	ArrayList<laser2> laser2 = new ArrayList<laser2>();
	ArrayList<med> med = new ArrayList<med>();
	Random random = new Random();
	int life = 4;
	int boss = 0;

	ObjectManager(Rocketship rocket) {
		this.rocket = rocket;
	}

	void addProjectile(Projectile x) {
		projectiles.add(x);
	}

	void addAliens() {
		aliens.add(new Alien(random.nextInt(runner.WIDTH), 0, 50, 50));
	}

	void addLaser() {
		laser.add(new laser(random.nextInt(runner.WIDTH), -800, 75, 800));
	}

	void addLaser2() {
		laser2.add(new laser2(-500, random.nextInt(runner.HEIGHT), 500, 75));
	}
	void addMed() {
		med.add(new med(random.nextInt(runner.WIDTH), 0, 50, 50));
	}
	
	void update() {
		rocket.update();
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).update();
			if (aliens.get(i).y >= runner.HEIGHT) {
				aliens.get(i).isActive = false;
			}
		}
		for (int i = 0; i < laser.size(); i++) {
			laser.get(i).update();
			if (laser.get(i).y >= runner.HEIGHT) {
				laser.get(i).isActive = false;
			}
		}
		for (int i = 0; i < laser2.size(); i++) {
			laser2.get(i).update();
			if (laser2.get(i).y >= runner.WIDTH) {
				laser2.get(i).isActive = false;
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
			if (projectiles.get(i).y <= 0) {
				projectiles.get(i).isActive = false;
				boss += 1;
			}
		}
		for (int i = 0; i < med.size(); i++) {
			med.get(i).update();
			if (med.get(i).y >= runner.HEIGHT) {
				med.get(i).isActive = false;
			}
		}
		
		checkCollision();
		purgeObjects();
	}

	void draw(Graphics g) {
		rocket.draw(g);
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).draw(g);
		}
		for (int i = 0; i < laser.size(); i++) {
			laser.get(i).draw(g);
		}
		for (int i = 0; i < laser2.size(); i++) {
			laser2.get(i).draw(g);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
		for (int i = 0; i < med.size(); i++) {
			med.get(i).draw(g);
		}
	}

	void purgeObjects() {
		for (int i = 0; i < aliens.size(); i++) {
			if (aliens.get(i).isActive == false) {
				aliens.remove(i);
			}
		}
		for (int i = 0; i < laser.size(); i++) {
			if (laser.get(i).isActive == false) {
				laser.remove(i);
			}
		}
		for (int i = 0; i < laser2.size(); i++) {
			if (laser2.get(i).isActive == false) {
				laser2.remove(i);
			}
		}
		for (int j = 0; j < projectiles.size(); j++) {
			if (projectiles.get(j).isActive == false) {
				projectiles.remove(j);
			}
		}
		for (int i = 0; i < med.size(); i++) {
			if (med.get(i).isActive == false) {
				med.remove(i);
			}
		}
	}

	void checkCollision() {
		for (int i = 0; i < aliens.size(); i++) {
			if (rocket.collisionBox.intersects(aliens.get(i).collisionBox) && aliens.get(i).isActive) {
				aliens.get(i).isActive = false;
				life -= 1;
				playSound("hit.wav");
			}
			for (int j = 0; j < projectiles.size(); j++) {
				if (projectiles.get(j).collisionBox.intersects(aliens.get(i).collisionBox) && projectiles.get(j).isActive) {
					aliens.get(i).isActive = false;
					projectiles.get(j).isActive = false;
				}
			}
		}
		for (int h = 0; h < laser.size(); h++) {
			if (rocket.collisionBox.intersects(laser.get(h).collisionBox) && laser.get(h).isActive) {
				laser.get(h).isActive = false;
				life -= 1;
				playSound("hit.wav");
			}
		}
		for (int e = 0; e < laser2.size(); e++) {
			if (rocket.collisionBox.intersects(laser2.get(e).collisionBox) && laser2.get(e).isActive) {
				laser2.get(e).isActive = false;
				life -= 1;
			}
		}
		for (int i = 0; i < med.size(); i++) {
			if (rocket.collisionBox.intersects(med.get(i).collisionBox) && med.get(i).isActive) {
				med.get(i).isActive = false;
				life += 1;
				playSound("med.wav");
			}
		}

	}

	int getlife() {
		return life;
	}
	int getBoss() {
		return boss;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== GamePanel.medSpawn) {
			addMed();
		}
		if(e.getSource()==GamePanel.rocketSpawn) {
			addAliens();
			addLaser();
			addLaser2();
		}
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