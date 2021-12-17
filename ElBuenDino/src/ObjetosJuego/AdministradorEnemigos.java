package ObjetosJuego;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Utilidades.Recursos;

public class AdministradorEnemigos {
	
	private BufferedImage pincheChico;
	private BufferedImage pinchesChicos3;
	private Random rand;
	
	private List<Enemigos> enemigos;
	private Personaje personaje;
	
	public AdministradorEnemigos(Personaje mainCharacter) {
		rand = new Random();
		pincheChico = Recursos.getResouceImage("data/pincheChico.png");
		pinchesChicos3 = Recursos.getResouceImage("data/pinchesChicos3.png");
		enemigos = new ArrayList<Enemigos>();
		this.personaje = mainCharacter;
		enemigos.add(createEnemigo());
	}
	
	public void update() {
		for(Enemigos e : enemigos) {
			e.update();
		}
		Enemigos enemy = enemigos.get(0);
		if(enemy.isOutOfScreen()) {
			personaje.upScore();
			enemigos.clear();
			enemigos.add(createEnemigo());
		}
	}
	
	public void draw(Graphics g) {
		for(Enemigos e : enemigos) {
			e.draw(g);
		}
	}
	
	private Enemigos createEnemigo() {
		// if (enemyType = getRandom)
		int type = rand.nextInt(2);
		if(type == 0) {
			return new PinchesMalos(personaje, 800, pincheChico.getWidth() - 8, pincheChico.getHeight() - 8, pincheChico);
		} else {
			return new PinchesMalos(personaje, 800, pinchesChicos3.getWidth() - 10, pinchesChicos3.getHeight() - 10, pinchesChicos3);
		}
	}
	
	public boolean isCollision() {
		for(Enemigos e : enemigos) {
			if (personaje.getBound().intersects(e.getBound())) {
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		enemigos.clear();
		enemigos.add(createEnemigo());
	}
	
}
