package ObjetosJuego;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import Utilidades.Animaciones;
import Utilidades.Recursos;

public class Personaje {

	public static final int LAND_POSY = 80;
	public static final float GRAVITY = 0.4f;
	
	private static final int NORMAL_RUN = 0;
	private static final int JUMPING = 1;
	private static final int DOWN_RUN = 2;
	private static final int DEATH = 3;
	
	private float posY;
	private float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;
	private final double playerWidth = 1;
	private final double playerRotationSpeed = speedX / (playerWidth * 0.5); 

	
	public int score = 0;
	
	private int state = NORMAL_RUN;
	
	private Animaciones normalRunAnim;
	private BufferedImage jumping;
	private Animaciones downRunAnim;
	private BufferedImage deathImage;
	
	private AudioClip jumpSound;
	private AudioClip deadSound;
	private AudioClip scoreUpSound;
	
	public Personaje() {
		posX = 50;
		posY = LAND_POSY;
		rectBound = new Rectangle();
		normalRunAnim = new Animaciones(90);
		normalRunAnim.addFrame(Recursos.getResouceImage("data/quake.png"));
		normalRunAnim.addFrame(Recursos.getResouceImage("data/quake.png"));
		jumping = Recursos.getResouceImage("data/quake.png");
		downRunAnim = new Animaciones(90);
		downRunAnim.addFrame(Recursos.getResouceImage("data/quake.png"));
		downRunAnim.addFrame(Recursos.getResouceImage("data/quake.png"));
		deathImage = Recursos.getResouceImage("data/quake.png");
		
		try {
			jumpSound =  Applet.newAudioClip(new URL("file","","data/jump.wav"));
			deadSound =  Applet.newAudioClip(new URL("file","","data/dead.wav"));
			scoreUpSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void draw(Graphics g) {
		switch(state) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) posX, (int) posY, null);
				break;
			case DOWN_RUN:
				g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
				break;
			case DEATH:
				g.drawImage(deathImage, (int) posX, (int) posY, null);
				break;
		}
	}
	
	public void update() {
		normalRunAnim.updateFrame();
		downRunAnim.updateFrame();
		if(posY >= LAND_POSY) {
			posY = LAND_POSY;
			if(state != DOWN_RUN) {
				state = NORMAL_RUN;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}
	
	public void jump() {
		if(posY >= LAND_POSY) {
			if(jumpSound != null) {
				jumpSound.play();
			}
			speedY = -7.5f;
			posY += speedY;
			state = JUMPING;
		}
	}
	
	public void down(boolean isDown) {
		if(state == JUMPING) {
			return;
		}
		if(isDown) {
			state = DOWN_RUN;
		} else {
			state = NORMAL_RUN;
		}
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		if(state == DOWN_RUN) {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY + 20;
			rectBound.width = downRunAnim.getFrame().getWidth() - 10;
			rectBound.height = downRunAnim.getFrame().getHeight();
		} else {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY;
			rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
			rectBound.height = normalRunAnim.getFrame().getHeight();
		}
		return rectBound;
	}
	
	public void dead(boolean isDeath) {
		if(isDeath) {
			state = DEATH;
		} else {
			state = NORMAL_RUN;
		}
	}
	
	public void reset() {
		posY = LAND_POSY;
	}
	
	public void playDeadSound() {
		deadSound.play();
	}
	
	public void upScore() {
		score += 20;
		if(score % 100 == 0) {
			scoreUpSound.play();
		}
	}
	
}

