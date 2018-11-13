package kr.or.hyun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;

	// CONSTRUCTOR
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Size 설정
		setFocusable(true); // 키 이벤트의 포커스를 받을 수 있는 컴포넌트가 여러 개일때 우선적으로 입력받기 위해 설정
		requestFocus(); // 키 이벤트를 받을 컴포넌트를 강제로 설정
	}

	// FUNCTIONS
	public void addNotify() { // 키 입력을 감지하는 함수
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
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		for(int i = 0; i < 5; i++) {
			enemies.add(new Enemy(1, 1));
		}
		
		// Game Loop
		while (running) {
			startTime = System.nanoTime(); // 1초 == 10의 -3승: 밀리 == 10의 -6승 : 마이크로 == 10의 -9승 : 나노

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
		
		// player update
		player.update();
		
		// bullet update
		for(int i = 0; i < bullets.size(); i++) {
			boolean remove = bullets.get(i).update();
			if(remove) {
				bullets.remove(i);
				i--;
			}
		}
		
		// enemy update
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		
		// bullet-enemy collision
		for(int i = 0; i < bullets.size(); i++) {
			
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
			for(int j = 0; j < enemies.size(); j++) {
				Enemy e = enemies.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx*dx - dy*dy);
				
				if(dist <= br + er) {
					e.hit();
					bullets.remove(i);
					i--;
					break;
				}
			}
		}
		
		// check dead enemies
		for(int i=0; i < enemies.size(); i++) {
			if(enemies.get(i).isDead()) {
				enemies.remove(i);
				i--;
			}
		}
		
	}

	private void gameRender() {

		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.drawString("FPS : " + averageFPS, 10, 20);
		g.drawString("num bullets : " + bullets.size(), 10, 40);
		
		// draw player
		player.draw(g);
		
		// draw bullets
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();

		g2.drawImage(image, 0, 0, null); // Render의 요소들을 모두 그린다.
		g2.dispose(); // 그래픽 컨텍스트를 제거하고 사용중인 모든 시스템 리소스를 해제함.
	}
	
	public void keyTyped(KeyEvent key) {}
	
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) player.setLeft(true);
		if(keyCode == KeyEvent.VK_RIGHT) player.setRight(true);
		if(keyCode == KeyEvent.VK_UP) player.setUp(true);
		if(keyCode == KeyEvent.VK_DOWN) player.setDown(true);
		if(keyCode == KeyEvent.VK_Z) player.setFiring(true);
	}
	
	public void keyReleased(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) player.setLeft(false);
		if(keyCode == KeyEvent.VK_RIGHT) player.setRight(false);
		if(keyCode == KeyEvent.VK_UP) player.setUp(false);
		if(keyCode == KeyEvent.VK_DOWN) player.setDown(false);
		if(keyCode == KeyEvent.VK_Z) player.setFiring(false);
	}

}
