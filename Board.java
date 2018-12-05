package team;

// Color 상수 등을 위한 awt 패키지 선언
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Board extends Canvas  // Canvas 클래스를 상속
  implements MouseListener, MouseMotionListener
{
  Nemonemo parent;   // Nemonemo 클래스의 객체를 저장
  
  boolean drag= false; // 마우스 드래그(끌기) 상태인지 여부
  int startX, startY;      // 마우스 드래그를 시작한 좌표
  int endX, endY;        // 마우스 드래그를 끝마친 좌표
  boolean com;
  int comboCount=0;
  boolean over;		//게임오버되어서 도중출력 끝내게
  
  Image offScr;   // 더블버퍼링을 위한 가상 화면
  Graphics offG;

  public Board(Nemonemo parent)
  {
    this.parent= parent;            // Nemonemo 클래스의 객체를 보관
    this.addMouseListener(this);  // 마우스 사용을 위한 리스너 선언
    this.addMouseMotionListener(this);
  }

  public void paint(Graphics g)
  {
    if(parent.timer.end) return;
	offScr= createImage(parent.size*20+1, parent.size*20+1); // 가상 화면 생성
  	offG  = offScr.getGraphics();

    for(int j=0; j<parent.size; j++)
      for(int i=0; i<parent.size; i++)
      {
        if(parent.endFlag){  // 게임이 끝난 경우
          if(parent.data.charAt(j*parent.size+i)=='1'){
            offG.fillRect(i*20, j*20, 20, 20); // 칸을 채워서 문제가 풀렸음을 표시
          }
        }else{
          if(parent.temp[j*parent.size+i]==1){
            offG.setColor(Color.blue);         // 게임 진행중일 때는 O표시
            offG.fillOval(i*20, j*20, 20, 20);
          }else if(parent.temp[j*parent.size+i]==2){
            offG.setColor(Color.red);          // 게임 진행중일 때는 X표시
            offG.drawLine(i*20, j*20, i*20+20, j*20+20);
            offG.drawLine(i*20, j*20+20, i*20+20, j*20);
          }else if(parent.temp[j*parent.size+i]==3) {
        	  offG.setColor(Color.blue);
        	  offG.fillOval(i*20, j*20, 20, 20);
          }
          else if(parent.temp[j*parent.size+i]==4){
        	  offG.setColor(Color.gray);
        	  offG.fillRect(i*20, j*20, 20, 20);
          }
        }
      }

    if(drag){ // 마우스를 드래그한 경우
      offG.setColor(Color.yellow);
      if(startX==endX){
        if(startY<endY){
          offG.fillRect(20*startX,20*startY,20,20*(endY-startY+1));
          offG.setColor(Color.red);
          offG.drawString(String.valueOf(endY-startY+1),endX*20+2,(endY+1)*20-2);
        }else{
          offG.fillRect(20*endX,20*endY,20,20*(startY-endY+1));
          offG.setColor(Color.red);
          offG.drawString(String.valueOf(startY-endY+1),endX*20+2,(endY+1)*20-2);
        }
      }
      else if(startY==endY){
        if(startX<endX){
          offG.fillRect(20*startX,20*startY,20*(endX-startX+1),20);
          offG.setColor(Color.red);
          offG.drawString(String.valueOf(endX-startX+1),endX*20+2,(endY+1)*20-2);
        }else{
          offG.fillRect(20*endX,20*endY,20*(startX-endX+1),20);
          offG.setColor(Color.red);
          offG.drawString(String.valueOf(startX-endX+1),endX*20+2,(endY+1)*20-2);
        } 
      }
    }

    for(int j=0; j<parent.size; j++)  // 격자 출력
      for(int i=0; i<parent.size; i++)
      {
        offG.setColor(Color.black);
        offG.drawRect(i*20, j*20, 20, 20);
      }

    offG.setColor(Color.black);

    for(int i=0; i<=20*parent.size; i+=20*5)
    {
      offG.drawLine(i-1, 0, i-1, 20*parent.size);
      offG.drawLine(i+1, 0, i+1, 20*parent.size);
    }

    for(int i=0; i<=20*parent.size; i+=20*5)
    {
      offG.drawLine(0, i-1, 20*parent.size, i-1);
      offG.drawLine(0, i+1, 20*parent.size, i+1);
    }

    g.drawImage(offScr, 0, 0, this); // 가상 화면을 실제 화면으로 복사
  }

  public void update(Graphics g)
  {
    paint(g);
  }

  public void mousePressed(MouseEvent e) // 플레이어가 마우스 버튼을 누른 경우
  {
    int x= e.getX();
    int y= e.getY();

    if((x/20)>=parent.size) return;
    if((y/20)>=parent.size) return;
    if(parent.endFlag) return;

    startX= x/20;
    startY= y/20;
  }

  public void mouseReleased(MouseEvent e) // 플레이어가 마우스 버튼을 놓은 경우
  {
    int x= e.getX();
    int y= e.getY();

    if((x/20)>=parent.size) return;
    if((y/20)>=parent.size) return;
    if(parent.endFlag) return;
    if(parent.timer.stop) return;
    	
    if(
    		(e.getModifiers() & InputEvent.BUTTON3_MASK)!=0
    		){ // Right Button
      setTemp(x,y,2);
    }else{ // Left Button
      setTemp(x,y,1);
    }

    
    parent.display(); // 퍼즐이 풀렸는지 검사
    this.drag= false;
    repaint();
  }

  public void mouseMoved(MouseEvent e)  // 마우스가 움직인 경우
  {
    int x= e.getX();
    int y= e.getY();

    if((x/20)>=parent.size) return;
    if((y/20)>=parent.size) return;

    parent.showLocation(x/20,y/20);  // 컬럼과 로우에 마우스 커서의 위치를 표시
    repaint();
  }

  public void mouseExited(MouseEvent e)  // 마우스가 보드를 벗어난 경우
  {
    int x= e.getX();
    int y= e.getY();

    parent.showLocation(-1,-1);  // 컬럼과 로우에 마우스 커서의 위치를 표시X
    this.drag= false;
    repaint();
  }

  public void mouseClicked(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}

  public void mouseDragged(MouseEvent e)  // 마우스를 드래그한 경우
  {
    int x= e.getX();
    int y= e.getY();

    if((x/20)>=parent.size) return;
    if((y/20)>=parent.size) return;

    parent.showLocation(x/20,y/20);  // 컬럼과 로우에 마우스 커서의 위치를 표시

    this.drag= true;
    endX= x/20;
    endY= y/20;
    repaint();
  }
  public void resetBoard() {
	  for(int i=0; i<parent.size*parent.size; i++) 
		  parent.temp[i]= 0;
  }
  
  public void checkTemp(int x, int y) {
	  if(parent.temp[x+y*parent.size]==1&&parent.data.charAt(x+y*parent.size)=='0') {//답은 0인데 1표시
		  comboCount=0;
		  com=false;
		  parent.temp[x+y*parent.size]=4;
		  parent.heart.minusHeart();
		  parent.heart.repaint();
	  }
	  else if(parent.temp[x+y*parent.size]==1&&parent.data.charAt(x+y*parent.size)=='1') { //답 1에 1표
		  parent.temp[x+y*parent.size]=3;
		  if(++comboCount==10) { //콤보 10
			  comboCount=0;
			  parent.heart.plusHeart();
		  }
		  else {
			  com=true;
		  }
	  }
//	  else if(parent.temp[x+y*10]==1&&parent.data.charAt(x+y*10)-48==1)
  }
  public void setTemp(int x, int y, int value)  // 플레이어의 입력을 temp 배열에 저장
  {
    int i;
    if(drag){	//drag O
      if(startX==endX){
        if(startY<endY){ //위에서 아래로
          for(i=startY; !over&&i<=endY; i++) {
        	  if(parent.temp[startX+i*parent.size]>=3) continue;
        	  if(parent.temp[startX+i*parent.size]==value)
        		  parent.temp[startX+i*parent.size]=0;
        	  else
        		  parent.temp[startX+i*parent.size]= value;
        	  checkTemp(startX,i);
        }
        }else if(startY>endY){ //아래에서 위로
          for(i=endY; !over&&i<=startY; i++) {
        	  if(parent.temp[startX+i*parent.size]>=3) continue;
        	  if(parent.temp[startX+i*parent.size]== value)
        		  parent.temp[startX+i*parent.size]=0;
        	  else
        		  parent.temp[startX+i*parent.size]=value;
        	  checkTemp(startX,i);
          }
        }else{ //제자리 클릭
        	if(parent.temp[startX+startY*parent.size]>=3) return;
        	if(parent.temp[startX+startY*parent.size]!=0)
            parent.temp[startX+startY*parent.size]= 0;
          else
            parent.temp[startX+startY*parent.size]= value;
          checkTemp(startX,startY);
        }
      }
      else if(startY==endY){   //시작x와 끝x다르고 y는 같음
        if(startX<endX){ //왼>오
          for(i=startX; !over&&i<=endX; i++) {
        	  if(parent.temp[i+startY*parent.size]>=3) continue;
        	  if(parent.temp[i+startY*parent.size]== value)
        		  parent.temp[i+startY*parent.size]=0;
    		  else
    			  parent.temp[i+startY*parent.size]=value;
        	  checkTemp(i,startY);
          }
        }else if(startX>endX){ //오>왼
          for(i=endX; !over&&i<=startX; i++) {
        	  if(parent.temp[i+startY*parent.size]>=3) continue;
        	  if(parent.temp[i+startY*parent.size]== value)
        		  parent.temp[i+startY*parent.size]=0;
    		  else
    			  parent.temp[i+startY*parent.size]=value;
        	  checkTemp(i,startY);
          }
        }else{ //제자리
          if(parent.temp[startX+startY*parent.size]>=3) return;
          if(parent.temp[startX+startY*parent.size]!=0)
            parent.temp[startX+startY*parent.size]= 0;
          else
            parent.temp[startX+startY*parent.size]= value;
          if(!over)
          checkTemp(startX,startY);
        }
      }
    }else{	//drag X
      if(parent.temp[x/20+y/20*parent.size]>=3) return;
      if(parent.temp[x/20+y/20*parent.size]!=0)
        parent.temp[x/20+y/20*parent.size]= 0;
      else
        parent.temp[x/20+y/20*parent.size]= value;
      checkTemp(x/20,y/20);
    }
    over=false;
  }
}

