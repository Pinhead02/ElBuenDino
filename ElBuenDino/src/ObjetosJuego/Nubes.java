package ObjetosJuego;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import interfazDelUsuario.Ventana;
import Utilidades.Recursos;

public class Nubes {
	private List<ImageCloud> listNube;
	private BufferedImage nube;
	
	private Personaje personaje;
	
	public Nubes(int width, Personaje mainCharacter) {
		this.personaje = mainCharacter;
		nube = Recursos.getResouceImage("data/warGrande.png");
		listNube = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 0;
		imageCloud.posY = 30;
		listNube.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 150;
		imageCloud.posY = 40;
		listNube.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 300;
		imageCloud.posY = 50;
		listNube.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 450;
		imageCloud.posY = 20;
		listNube.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 60;
		listNube.add(imageCloud);
	}
	
	public void update(){
		Iterator<ImageCloud> itr = listNube.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.posX -= personaje.getSpeedX()/8;
		while(itr.hasNext()) {
			ImageCloud element = itr.next();
			element.posX -= personaje.getSpeedX()/8;
		}
		if(firstElement.posX < -nube.getWidth()) {
			listNube.remove(firstElement);
			firstElement.posX = Ventana.SCREEN_WIDTH;
			listNube.add(firstElement);
		}
	}
	
	public void draw(Graphics g) {
		for(ImageCloud imgLand : listNube) {
			g.drawImage(nube, (int) imgLand.posX, imgLand.posY, null);
		}
	}
	
	private class ImageCloud {
		float posX;
		int posY;
	}
}

