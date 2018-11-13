package kr.or.hyun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	// FIELDS
	private static int WIDTH = 400;
	private static int HEIGHT = 400;

	private BufferedImage image;
	private Graphics2D g;

	private Thread thread;
	private boolean running;

	private int FPS = 30;
	private double averageFPS;

	long targetTime = 1000 / FPS;

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

	}

	private void gameRender() {

		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.drawString("FPS : " + averageFPS, 10, 20);

	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();

		g2.drawImage(image, 0, 0, null); // Render의 요소들을 모두 그린다.
		g2.dispose(); // 그래픽 컨텍스트를 제거하고 사용중인 모든 시스템 리소스를 해제함.
	}

}
