package ObjetosJuego;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Utilidades.Recursos;

public class Base {
	
	public static final int LAND_POSY = 105;
	
	private List<ImageLand> listLand;
	private BufferedImage fondoV1;
	private BufferedImage fondoV2;
	private BufferedImage fondoV3;
	
	private Personaje personaje;
	
	public Base(int width, Personaje mainCharacter) {
		this.personaje = mainCharacter;
		fondoV1 = Recursos.getResouceImage("data/fondoV1.png");
		fondoV2 = Recursos.getResouceImage("data/fondoV2.png");
		fondoV3 = Recursos.getResouceImage("data/fondoV3.png");
		int numberOfImageLand = width / fondoV1.getWidth() + 2;
		listLand = new ArrayList<ImageLand>();
		for(int i = 0; i < numberOfImageLand; i++) {
			ImageLand imageLand = new ImageLand();
			imageLand.posX = i * fondoV1.getWidth();
			setImageLand(imageLand);
			listLand.add(imageLand);
		}
	}
	
	public void update(){
		Iterator<ImageLand> itr = listLand.iterator();
		ImageLand firstElement = itr.next();
		firstElement.posX -= personaje.getSpeedX();
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {
			ImageLand element = itr.next();
			element.posX = previousPosX + fondoV1.getWidth();
			previousPosX = element.posX;
		}
		if(firstElement.posX < -fondoV1.getWidth()) {
			listLand.remove(firstElement);
			firstElement.posX = previousPosX + fondoV1.getWidth();
			setImageLand(firstElement);
			listLand.add(firstElement);
		}
	}
	
	private void setImageLand(ImageLand imgLand) {
		int typeLand = getTypeOfLand();
		if(typeLand == 1) {
			imgLand.image = fondoV1;
		} else if(typeLand == 3) {
			imgLand.image = fondoV3;
		} else {
			imgLand.image = fondoV2;
		}
	}
	
	public void draw(Graphics g) {
		for(ImageLand imgLand : listLand) {
			g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
		}
	}
	
	private int getTypeOfLand() {
		Random rand = new Random();
		int type = rand.nextInt(10);
		if(type == 1) {
			return 1;
		} else if(type == 9) {
			return 3;
		} else {
			return 2;
		}
	}
	
	private class ImageLand {
		float posX;
		BufferedImage image;
	}
	
}
