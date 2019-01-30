import javax.swing.*;
import java.awt.*;
public class ConnectFourPanel extends JPanel
{
   ImageIcon[] icons;
   String location;
   int[][] board;
   public ConnectFourPanel(int[][] b)
   {
      super();
      location=System.getProperty("user.dir")+"/";
      board=b;
      icons=new ImageIcon[3];
      for(int i=0; i<icons.length; i++)
         icons[i] = new ImageIcon(location+i+".png");
   }
   public void paintComponent(Graphics g)
   {
      for(int r=0; r<7; r++)
         for(int c=0; c<6; c++)
            g.drawImage(icons[board[c][r]].getImage(), r*100, c*100, 100, 100, null);
      g.drawString(connectFour.getMessage(), 0, 615);
   }
}