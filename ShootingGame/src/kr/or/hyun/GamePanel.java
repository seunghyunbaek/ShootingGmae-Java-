package kr.or.hyun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	// FIELDS
	private static int WIDTH  = 400;
	private static int HEIGHT = 400;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private Thread thread;
	private boolean running;
	
	// CONSTRUCTOR
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Size 설정
		setFocusable(true); // 키 이벤트의 포커스를 받을 수 있는 컴포넌트가 여러 개일때 우선적으로 입력받기 위해 설정
		requestFocus(); // 키 이벤트를 받을 컴포넌트를 강제로 설정
	}
	
	//FUNCTIONS
	public void addNotify() {  // 키 입력을 감지하는 함수
		super.addNotify();
		
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run() {
		
		running = true;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		g = (Graphics2D) image.getGraphics();
		
		// Game Loop
		while (running) {
			gameUpdate();
			gameRender();
			gameDraw();
		}
		
	}
	
	private void gameUpdate() { 
		
	}
	
	private void gameRender() {
		
		g.setColor(Color.MAGENTA);
		g.fillRect(10, 10, 100, 40);
		
		g.setColor(Color.CYAN);
		g.drawString("Test String", 10, 70);
		
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		
		g2.drawImage(image, 0, 0, null); // Render의 요소들을 모두 그린다.
		g2.dispose(); // 그래픽 컨텍스트를 제거하고 사용중인 모든 시스템 리소스를 해제함.
	}

		
	
}
