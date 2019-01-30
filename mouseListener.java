import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class mouseListener extends MouseAdapter
{
   public mouseListener()
   {
   
   }
   public void mousePressed(MouseEvent e)
   {
      connectFour.setCol(e.getX()/100);
   }
}