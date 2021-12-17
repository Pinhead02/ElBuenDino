package interfazDelUsuario;


import javax.swing.JFrame;

public class Ventana extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private PantallaJuego pantallaJuego;
	
	public Ventana() {
		super("ElBuenDino -GarciaBasualdoGiusti-");
		setSize(SCREEN_WIDTH, 175);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		pantallaJuego = new PantallaJuego();
		addKeyListener(pantallaJuego);
		add(pantallaJuego);
	}
	
	public void startGame() {
		setVisible(true);
		pantallaJuego.startGame();
	}
	
	public static void main(String args[]) {
		(new Ventana()).startGame();
	}
}
