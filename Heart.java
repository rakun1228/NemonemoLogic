package team;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Heart extends Canvas {
	private static int heartCnt=5;
	private int life;
	GameOver over;
	boolean re=false;
	
	Nemonemo parent;   // Nemonemo 클래스의 객체를 저장

	Image offScr;           // 더블버퍼링을 위한 가상 화면
	Graphics offG;

	public Heart(Nemonemo parent)
	  {
	    this.parent= parent;  // Nemonemo 클래스의 객체를 보관
	    setSize(121,81);
	    setBackground(Color.WHITE);
	    setLife(3);
	  }
	
	public void setLife(int a) {
		life=a;
	}
	
	public void plusHeart() {
		if(life<heartCnt)
			life++;
		else
			return;
		parent.heart.repaint();
	}
	
	public void minusHeart() {
		if(life==1) {
			life--;
			repaint();
			over = new GameOver(parent);
	    	parent.board.clearBoard();
			over.setVisible(true);
			}
		else 
			if(life>1)
			life--;
		else
			return;
		
		repaint();
	}
	
	public void paint(Graphics g) {
		if(re) {
			setLife(3);
			re=false;
		}
		offScr= createImage(121, 81);  // 가상 화면 생성
	    offG  = offScr.getGraphics();
	    
	    int i;
	    
	    offG.setColor(Color.BLACK);
	    offG.drawRect(0, 0, 120, 80);
		offG.drawString("Life",35,40);
		
		for(i=0;i<heartCnt;i++) {
			if(i<life) {
				offG.setColor(Color.RED);
				offG.drawString("♥",i*15+20,65);
			}
			else {
				offG.setColor(Color.GRAY);
				offG.drawString("♥",i*15+20,65);
			}
		}
		g.drawImage(offScr, 0, 0, this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

}
