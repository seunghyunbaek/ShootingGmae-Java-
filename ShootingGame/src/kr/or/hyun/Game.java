package kr.or.hyun;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame window = new JFrame("First Game");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 
		
		window.setContentPane(new GamePanel()); // ���̾ƿ� ����
		
		window.pack(); // ��������� �⺻ ũ�⸦ ������� â���� ������Ҹ� �����Ѵ�. 
		window.setVisible(true); // Frame�� �����ش�.
	}
	
}
