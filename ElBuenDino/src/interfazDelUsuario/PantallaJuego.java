package interfazDelUsuario;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ObjetosJuego.AdministradorEnemigos;
import ObjetosJuego.Base;
import ObjetosJuego.Nubes;
import ObjetosJuego.Personaje;
import Utilidades.Recursos;

public class PantallaJuego extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	
	private Base base;
	private Personaje personaje;
	private AdministradorEnemigos administradorEnemigos;
	private Nubes nubes;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_GAME_STATE;

	private BufferedImage barritaE;
	private BufferedImage probaOtraVez;

	public PantallaJuego() {
		personaje = new Personaje();
		base = new Base(Ventana.SCREEN_WIDTH, personaje);
		personaje.setSpeedX(4);
		barritaE = Recursos.getResouceImage("data/barraEspaciadoraz.png");
		probaOtraVez = Recursos.getResouceImage("data/ProbaOtraVez_2.png");
		administradorEnemigos = new AdministradorEnemigos(personaje);
		nubes = new Nubes(Ventana.SCREEN_WIDTH, personaje);
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE) {
			nubes.update();
			base.update();
			personaje.update();
			administradorEnemigos.update();
			if (administradorEnemigos.isCollision()) {
				personaje.playDeadSound();
				gameState = GAME_OVER_STATE;
				personaje.dead(true);
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.decode("#570580"));
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
		case START_GAME_STATE:
			personaje.draw(g);
			break;
		case GAME_PLAYING_STATE:
		case GAME_OVER_STATE:
			nubes.draw(g);
			base.draw(g);
			administradorEnemigos.draw(g);
			personaje.draw(g);
			g.setColor(Color.WHITE);
			g.drawString("Puntuación " + personaje.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(probaOtraVez, 200, 30, null);
				g.drawImage(barritaE, 283, 50, null);
				
			}
			break;
		}
	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case START_GAME_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
				}
				break;
			case GAME_PLAYING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					personaje.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					personaje.down(true);
				}
				break;
			case GAME_OVER_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
					resetGame();
				}
				break;

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == GAME_PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				personaje.down(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	private void resetGame() {
		administradorEnemigos.reset();
		personaje.dead(false);
		personaje.reset();
	}

}
