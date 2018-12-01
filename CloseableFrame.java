package team;

import javax.swing.*;  // 스윙 패키지 선언
import javax.swing.event.*;
import java.awt.event.*;  // WindowEvent 클래스 사용을 위한 awt 패키지 선언

public class CloseableFrame extends JFrame   // 스윙의 JFrame 상속
  implements WindowListener
{
  public CloseableFrame(){ this.addWindowListener(this); }
  public CloseableFrame(String title)
  {
    super(title);
    this.addWindowListener(this);
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
