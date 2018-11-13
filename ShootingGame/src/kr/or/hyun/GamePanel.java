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

	}

	private void gameRender() {

		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.drawString("FPS : " + averageFPS, 10, 20);

	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();

		g2.drawImage(image, 0, 0, null); // Render�� ��ҵ��� ��� �׸���.
		g2.dispose(); // �׷��� ���ؽ�Ʈ�� �����ϰ� ������� ��� �ý��� ���ҽ��� ������.
	}

}
