package ObjetosJuego;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Utilidades.Recursos;

public class AdministradorEnemigos {
	
	private BufferedImage pincheChico;
	private BufferedImage cactus2;
	private Random rand;
	
	private List<Enemigos> enemigos;
	private Personaje personaje;
	
	public AdministradorEnemigos(Personaje mainCharacter) {
		rand = new Random();
		pincheChico = Recursos.getResouceImage("data/pincheChico.png");
		cactus2 = Recursos.getResouceImage("data/cactus2.png");
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
			return new Cactus(personaje, 800, pincheChico.getWidth() - 8, pincheChico.getHeight() - 8, pincheChico);
		} else {
			return new Cactus(personaje, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
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
