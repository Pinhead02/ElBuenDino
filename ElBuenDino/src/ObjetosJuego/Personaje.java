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

	public static final int LAND_POSY = 60;
	public static final float GRAVITY = 0.30f;

	private static final int CORRE = 0;
	private static final int SALTAR = 1;
	private static final int MUERE = 3;

	private float posY;
	private float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;

	public int score = 0;

	private int state = CORRE;

	private Animaciones corre;
	private BufferedImage salta;
	private BufferedImage explosionn;

	private AudioClip jumpSound;
	private AudioClip deadSound;
	private AudioClip scoreUpSound;

	public Personaje() {
		posX = 50;
		posY = LAND_POSY;
		rectBound = new Rectangle();
		corre = new Animaciones(90);
		corre.addFrame(Recursos.getResouceImage("data/robot1.png"));
		corre.addFrame(Recursos.getResouceImage("data/robot2.png"));
		salta = Recursos.getResouceImage("data/robot4.png");
		explosionn = Recursos.getResouceImage("data/explo.png");

		try {
			jumpSound = Applet.newAudioClip(new URL("file", "", "data/sonidoMario.wav"));
			deadSound = Applet.newAudioClip(new URL("file", "", "data/explosion.wav"));
			scoreUpSound = Applet.newAudioClip(new URL("file", "", "data/sonidoPuntitos.wav"));
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
		switch (state) {
		case CORRE:
			g.drawImage(corre.getFrame(), (int) posX, (int) posY, null);
			break;
		case SALTAR:
			g.drawImage(salta, (int) posX, (int) posY, null);
			break;
		case MUERE:
			g.drawImage(explosionn, (int) posX, (int) posY, null);
			break;
		}
	}

	public void update() {
		corre.updateFrame();
		if (posY >= LAND_POSY) {
			posY = LAND_POSY;
			{
				state = CORRE;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}

	public void jump() {
		if (posY >= LAND_POSY) {
			if (jumpSound != null) {
				jumpSound.play();
			}
			speedY = -7.5f;
			posY += speedY;
			state = SALTAR;
		}
	}

	public void down(boolean isDown) {
		if (state == SALTAR) {
			return;
		}
		
			state = CORRE;
		}

	public Rectangle getBound() {
		rectBound = new Rectangle();

		rectBound.x = (int) posX + 5;
		rectBound.y = (int) posY;
		rectBound.width = corre.getFrame().getWidth() - 10;
		rectBound.height = corre.getFrame().getHeight();
		return rectBound;
	}

	public void dead(boolean isDeath) {
		if (isDeath) {
			state = MUERE;
		} else {
			state = CORRE;
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
		if (score % 100 == 0) {
			scoreUpSound.play();
		}
	}

}
