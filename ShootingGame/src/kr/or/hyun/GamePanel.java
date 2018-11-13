package kr.or.hyun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// FIELDS
	public static int WIDTH = 400;
	public static int HEIGHT = 400;

	private BufferedImage image;
	private Graphics2D g;

	private Thread thread;
	private boolean running;

	private int FPS = 30;
	private double averageFPS;

	long targetTime = 1000 / FPS;
	
	private Player player;

	// CONSTRUCTOR
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Size ����
		setFocusable(true); // Ű �̺�Ʈ�� ��Ŀ���� ���� �� �ִ� ������Ʈ�� ���� ���϶� �켱������ �Է¹ޱ� ���� ����
		requestFocus(); // Ű �̺�Ʈ�� ���� ������Ʈ�� ������ ����
	}

	// FUNCTIONS
	public void addNotify() { // Ű �Է��� �����ϴ� �Լ�
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		
		addKeyListener(this);
	}

	public void run() {

		running = true;

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		g = (Graphics2D) image.getGraphics();
		
		long startTime;
		long URDTimeMillis;
		long totalTime = 0;
		long waitTime;

		int frameCount = 0;
		int maxFrameCount = 30;
		
		player = new Player();

		// Game Loop
		while (running) {
			startTime = System.nanoTime(); // 1�� == 10�� -3��: �и� == 10�� -6�� : ����ũ�� == 10�� -9�� : ����

			gameUpdate();
			gameRender();
			gameDraw();

			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;

			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
			}

			totalTime += System.nanoTime() - startTime;
			frameCount++;

			if (frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}

	}

	private void gameUpdate() {
		player.update();
	}

	private void gameRender() {

		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.drawString("FPS : " + averageFPS, 10, 20);
		
		player.draw(g);
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();

		g2.drawImage(image, 0, 0, null); // Render�� ��ҵ��� ��� �׸���.
		g2.dispose(); // �׷��� ���ؽ�Ʈ�� �����ϰ� ������� ��� �ý��� ���ҽ��� ������.
	}
	
	public void keyTyped(KeyEvent key) {
		
	}
	
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) player.setLeft(true);
		if(keyCode == KeyEvent.VK_RIGHT) player.setRight(true);
		if(keyCode == KeyEvent.VK_UP) player.setUp(true);
		if(keyCode == KeyEvent.VK_DOWN) player.setDown(true);
	}
	public void keyReleased(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) player.setLeft(false);
		if(keyCode == KeyEvent.VK_RIGHT) player.setRight(false);
		if(keyCode == KeyEvent.VK_UP) player.setUp(false);
		if(keyCode == KeyEvent.VK_DOWN) player.setDown(false);
	}

}
