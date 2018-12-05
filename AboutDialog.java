package team;

import javax.swing.*;           // 스윙 패키지 선언
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog     // 스윙의 JDialog 상속
  implements ActionListener, WindowListener
{
  // 스윙 컴포넌트 선언
  JPanel aboutPanel;  
  JButton ok;
  JLabel titleLabel, nameLabel;
  Nemonemo parent;
  
  public AboutDialog(Nemonemo parent)
  {
    super(parent, "Nemonemo Logic", true);  // 다이얼로그(대화상자)의 타이틀(제목) 설정
    this.parent=parent;
    parent.timer.stop=true;
    this.setSize(240,190);                            // 다이얼로그의 크기 설정
    this.addWindowListener(this);
    this.setLayout(new BorderLayout(15,15));
    this.setFont(new Font("SansSerif", Font.BOLD, 14));

    createAboutPanel();
  }  

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==ok){
      parent.timer.stop=false;
      this.dispose();
    }
  }  
  
  public void createAboutPanel()
  {
    aboutPanel= new JPanel();
    aboutPanel.setLayout(null);
    
    // About Game 정보 출력
    titleLabel= new JLabel("ネモネモロジック 2018/12/06");
    aboutPanel.add(titleLabel);
    titleLabel.setBounds(40,30,200,25);
    
    nameLabel= new JLabel(" by 촉음과 아이들");
    aboutPanel.add(nameLabel);
    nameLabel.setBounds(60,60,200,25);

    // 다이얼로그 종료 버튼
    ok= new JButton("Okay");
    ok.addActionListener(this);
    aboutPanel.add(ok);
    ok.setBounds(80,110,80,25);

    this.add("Center", aboutPanel);
  }
        
  // the methods of the WindowListener object
  public void windowClosing(WindowEvent e){ 
	  parent.timer.stop=false;
	  this.dispose(); }
  public void windowOpened(WindowEvent e){}
  public void windowClosed(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}  
}

