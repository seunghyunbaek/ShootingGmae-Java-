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
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Size ����
		setFocusable(true); // Ű �̺�Ʈ�� ��Ŀ���� ���� �� �ִ� ������Ʈ�� ���� ���϶� �켱������ �Է¹ޱ� ���� ����
		requestFocus(); // Ű �̺�Ʈ�� ���� ������Ʈ�� ������ ����
	}
	
	//FUNCTIONS
	public void addNotify() {  // Ű �Է��� �����ϴ� �Լ�
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
		
		g2.drawImage(image, 0, 0, null); // Render�� ��ҵ��� ��� �׸���.
		g2.dispose(); // �׷��� ���ؽ�Ʈ�� �����ϰ� ������� ��� �ý��� ���ҽ��� ������.
	}

		
	
}
