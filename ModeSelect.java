package team;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModeSelect extends JDialog implements ActionListener, WindowListener {
	 // 스윙 컴포넌트 선언
//	  JPanel normalMode,randomMode; 
	  JPanel panel;
	  JButton n10,r10,r15,r20,r25,r30;
	  JLabel normalMode,randomMode;
	  Nemonemo parent;
	  
	  public ModeSelect(Nemonemo parent)
	  {
	    super(parent, "Select Mode", true);  // 다이얼로그(대화상자)의 타이틀(제목) 설정
	    this.parent=parent;
	    this.setSize(450, 230);                            // 다이얼로그의 크기 설정
	    this.addWindowListener(this);
	    setModeSelect();
	    
	  }  
	  
	  private void setModeSelect() {
		   
	    this.setLayout(null);

	    normalMode = new JLabel("Normal Mode");
	    normalMode.setHorizontalAlignment(JLabel.CENTER);
	    normalMode.setBackground(Color.DARK_GRAY);
	    randomMode = new JLabel("Random Mode");
	    randomMode.setHorizontalAlignment(JLabel.CENTER);
	    randomMode.setBackground(Color.LIGHT_GRAY);
	    
	    add(normalMode);
	    normalMode.setSize(120,90);
	    normalMode.setBounds(0, 0, 120, 90);
	    add(randomMode);
	    randomMode.setSize(120, 90);
	    randomMode.setBounds(0, 90, 120, 90);
	    
	    n10= new JButton("10");
	    n10.addActionListener(this);
	    r10= new JButton("10");
	    r10.addActionListener(this);
	    r15= new JButton("15");
	    r15.addActionListener(this);
	    r20= new JButton("20");
	    r20.addActionListener(this);
	    r25= new JButton("25");
	    r25.addActionListener(this);
	    r30= new JButton("30");
	    r30.addActionListener(this);
	    
	    add(n10);
	    add(r10);
	    add(r15);
	    add(r20);
	    add(r25);
	    add(r30);
	    /*
	    n10
	    r10
	    r15
	    r20
	    r25
	    r30*/
	    
	    int buttonSize=50;
	    n10.setSize(buttonSize, buttonSize);
	    r10.setSize(buttonSize, buttonSize);
	    r15.setSize(buttonSize, buttonSize);
	    r20.setSize(buttonSize, buttonSize);
	    r25.setSize(buttonSize, buttonSize);
	    r30.setSize(buttonSize, buttonSize);
	    
	    n10.setBounds(140, 20, buttonSize, buttonSize);
	    r10.setBounds(140, 115, buttonSize, buttonSize);
	    r15.setBounds(190, 115, buttonSize, buttonSize);
	    r20.setBounds(240, 115, buttonSize, buttonSize);
	    r25.setBounds(290, 115, buttonSize, buttonSize);
	    r30.setBounds(340, 115, buttonSize, buttonSize);
	    

	}

	public void actionPerformed(ActionEvent e)
	  {
	    if(e.getSource()==n10){
	    	parent.size=10;
	    	parent.Mode=0;
	    	this.dispose();
	    }
	    else if(e.getSource()==r10){
	    	parent.size=10;
	    	parent.Mode=1;
	    	this.dispose();
		}
	    else if(e.getSource()==r15){
	    	parent.size=15;
	    	parent.Mode=1;
	    	this.dispose();
		}
	    else if(e.getSource()==r20){
	    	parent.size=20;
	    	parent.Mode=1;
	    	this.dispose();
		}
	    else if(e.getSource()==r25){
	    	parent.size=25;
	    	parent.Mode=1;
	    	this.dispose();
		}
	    else if(e.getSource()==r30){
	    	parent.size=30;
	    	parent.Mode=1;
	    	this.dispose();
		}
	    
	  }  
	 
	  
	  // the methods of the WindowListener object
	  public void windowClosing(WindowEvent e){ this.dispose(); }
	  public void windowOpened(WindowEvent e){}
	  public void windowClosed(WindowEvent e){}
	  public void windowIconified(WindowEvent e){}
	  public void windowDeiconified(WindowEvent e){}
	  public void windowActivated(WindowEvent e){}
	  public void windowDeactivated(WindowEvent e){}  
}
