package kr.or.hyun;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame window = new JFrame("First Game");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 
		
		window.setContentPane(new GamePanel()); // 레이아웃 설정
		
		window.pack(); // 구성요소의 기본 크기를 기반으로 창내에 구성요소를 압축한다. 
		window.setVisible(true); // Frame을 보여준다.
	}
	
}
