package team;

import java.awt.*;  // Color 상수 등을 위한 awt 패키지 선언

public class Column extends Canvas  // Canvas 클래스를 상속
{
  Nemonemo parent;   // Nemonemo 클래스의 객체를 저장

  Image offScr;           // 더블버퍼링을 위한 가상 화면
  Graphics offG;

  public Column(Nemonemo parent)
  {
    this.parent= parent;  // Nemonemo 클래스의 객체를 보관
    getColumn();
  }

  public void getColumn()  // 데이터에 맞춰 컬럼의 숫자를 생성
  {
    for(int i=0; i<10; i++)  // 모든 열에 연속한 '1'의 개수를 계산
      parent.numOfColumn[i]= getNumber(i); 
  }

  int getNumber(int start)  // 해당하는 열의 연속한 '1'의 개수를 계산
  {
    int count= 0;  // 연속된 '1'의 개수
    int pos= 0;  // 몇 번째 연속된 '1'의 개수를 나타내는 수인지를 표시

    for(int i=start; i<100; i+=10)  // 같은 열에 속한 data의 값을 비교
    {
      if(parent.data.charAt(i)=='0' && count>0){  // 연속하지 않은 경우('0'인 경우)
        parent.columnNums[start][pos++]= count;
        count= 0;
      }else if(parent.data.charAt(i)=='1' && count>=0){  // 연속한 경우('1'인 경우)
        count++;
      }
    }

    if(count>0) parent.columnNums[start][pos++]= count;
    if(pos==0)  parent.columnNums[start][pos++]= 0;

    return pos;
  }

  public void paint(Graphics g)
  {
    offScr= createImage(201, 121);  // 가상 화면 생성
    offG  = offScr.getGraphics();
    if(parent.mouseX!=-1){
      offG.setColor(Color.yellow);
      offG.fillRect(20*parent.mouseX,0,19,120);  // 마우스 커서가 있는 열의 경우
    }
    offG.setColor(Color.black);

    for(int i=0; i<10; i++)
    {
      offG.drawLine(i*20, 0, i*20, 220);
      for(int j=0; j<parent.numOfColumn[i]; j++)  // 숫자 출력
        if(String.valueOf(parent.columnNums[i][j]).length()<2){
          offG.drawString(String.valueOf(parent.columnNums[i][j]), i*20+9, (100 - parent.numOfColumn[i]*20 + j*20)+39);
        }else{
          offG.drawString(String.valueOf(parent.columnNums[i][j]), i*20+1, (100 - parent.numOfColumn[i]*20 + j*20)+39);
        }
    }

    for(int i=0; i<=200; i+=100)
    {
      offG.drawLine(i-1, 0, i-1, 120);
      offG.drawLine(i+1, 0, i+1, 120);
    }

    offG.drawLine(200, 0, 200, 120);
    offG.drawLine(0, 120, 20*10, 120);

    g.drawImage(offScr, 0, 0, this);
  }

  public void update(Graphics g)
  {
    paint(g);
  }
}

