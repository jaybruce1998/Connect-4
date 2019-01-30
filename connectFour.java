import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
public class connectFour
{
   static int[][] board=new int[6][7];
   static int[][] testBoard=new int[6][7];
   static int option, col;
   static String typed, name1, name2, message;
   static ArrayList<Integer> emptyColumns=new ArrayList<Integer>();
   static ArrayList<Integer> badColumns=new ArrayList<Integer>();
   static boolean foundSpace;
   static JFrame frame = new JFrame();
   private static mouseListener ml=new mouseListener();
   static ConnectFourPanel cp=new ConnectFourPanel(board);
   public connectFour()
   {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addMouseListener(ml);
      frame.add(cp);
      frame.setSize(716, 660);
      frame.setFocusable(true);
   }
   public static void fillBoard()
   {
      for(int r=0; r<board.length; r++)
         for(int c=0; c<board[0].length; c++)
         {
            board[r][c]=2;
            testBoard[r][c]=2;     
         }
   }
   public static void showBoard()
   {
      System.out.println(" 0 1 2 3 4 5 6");
      for(int r=0; r<board.length; r++)
      {
         System.out.print("|");
         for(int c=0; c<board[0].length; c++)
            if(board[r][c]==0)
               System.out.print(name1.charAt(0)+"|");
            else
               if(board[r][c]==1)
                  System.out.print(name2.charAt(0)+"|");
               else
                  System.out.print(" |");
         System.out.println();
         System.out.println("---------------");
      }
   }
   public static void doMove(int player, int c)
   {
      foundSpace=false;
      for(int r=board.length-1; !foundSpace; r--)
         if(board[r][c]==2)
         {
            foundSpace=true;
            board[r][c]=player;
            testBoard[r][c]=player;
         }
   }
   public static boolean badMove(int col)
   {
      int row;
      foundSpace=false;
      row=0;
      for(int r=board.length-1; !foundSpace; r--)
         if(board[r][col]==2)
         {
            foundSpace=true;
            row=r;
            testBoard[r][col]=1;
         }
      if(playerWon(1, testBoard))
      {
         testBoard[row][col]=2;
         return false;
      }
      for(int r=0; r<board.length; r++)
         for(int c=3; c<board[0].length; c++)
            if(nInARow(0, r, c, 3, testBoard)>-1)
            {
               testBoard[row][col]=2;
               return true;
            }
      for(int r=3; r<board.length; r++)
         for(int c=0; c<board[0].length; c++)
            if(nInAColumn(0, r, c, 3, testBoard)>-1)
            {
               testBoard[row][col]=2;
               return true;
            }
      for(int r=3; r<board.length; r++)
         for(int c=3; c<board[0].length; c++)
            if(nInALeftDiagonal(0, r, c, 3, testBoard)>-1)
            {
               testBoard[row][col]=2;
               return true;
            }
      for(int r=3; r<board.length; r++)
         for(int c=0; c<board[0].length-3; c++)
            if(nInARightDiagonal(0, r, c, 3, testBoard)>-1)
            {
               testBoard[row][col]=2;
               return true;
            }
      testBoard[row][col]=2;
      return false;
   }
   public static void doTurn(int player, boolean AITurn)
   {
      col=7;
      if(player<1)
         message="It is "+name1+"'s turn.";
      else
         message="It is "+name2+"'s turn.";
      frame.repaint();
      if(AITurn)
      {
         badColumns.clear();
         col=getAICol();
         while(badMove(col)&&badColumns.size()<7)
         {
            badColumns.add(col);
            if(badColumns.size()>6)
               col=randomIndex();
            else
            {
               while(badColumns.contains(col)||board[0][col]!=2)
                  if(col<6)
                     col++;
                  else
                     col=0;
            }
         }
      }
      else
         while(col<0||col>6||board[0][col]!=2)
            cp.repaint();
      doMove(player, col);
   }
   public static int nInARow(int player, int r, int col, int n, int game[][])
   {
      emptyColumns.clear();
      for(int c=col-3; c<col+1; c++)
         if(game[r][c]==2&&(r==5||game[r+1][c]!=2))
            emptyColumns.add(c);
         else
            if(game[r][c]!=player)
               return -1;
      if(emptyColumns.size()==4-n)
         return emptyColumns.get((int)(Math.random()*(4-n)));
      return -1;
   }
   public static int nInAColumn(int player, int row, int c, int n, int game[][])
   {
      emptyColumns.clear();
      for(int r=row-3; r<row+1; r++)
         if(game[r][c]==2&&(r==5||game[r+1][c]!=2))
            emptyColumns.add(c);
         else
            if(game[r][c]!=player)
               return -1;
      if(emptyColumns.size()==4-n)
         return emptyColumns.get((int)(Math.random()*(4-n)));
      return -1;
   }
   public static int nInALeftDiagonal(int player, int r, int c, int n, int game[][])
   {
      emptyColumns.clear();
      for(int i=0; i<4; i++)
         if(game[r-i][c-i]==2&&(r==5||game[r-i+1][c-i]!=2))
            emptyColumns.add(c-i);
         else
            if(game[r-i][c-i]!=player)
               return -1;
      if(emptyColumns.size()==4-n)
         return emptyColumns.get((int)(Math.random()*(4-n)));
      return -1;
   }
   public static int nInARightDiagonal(int player, int r, int c, int n, int game[][])
   {
      emptyColumns.clear();
      for(int i=0; i<4; i++)
         if(game[r-i][c+i]==2&&(r==5||game[r-i+1][c+i]!=2))
            emptyColumns.add(c+i);
         else
            if(game[r-i][c+i]!=player)
               return -1;
      if(emptyColumns.size()==4-n)
         return emptyColumns.get((int)(Math.random()*(4-n)));
      return -1;
   }
   public static int randomIndex()
   {
      int c;
      c=(int)(Math.random()*board[0].length);
      while(board[0][c]!=2)
         if(c<6)
            c++;
         else
            c=0;
      return c;
   }
   public static int getAICol()
   {
      for(int r=0; r<board.length; r++)
         for(int c=3; c<board[0].length; c++)
            if(nInARow(1, r, c, 3, board)>-1)
               return nInARow(1, r, c, 3, board);
      for(int r=3; r<board.length; r++)
         for(int c=0; c<board[0].length; c++)
            if(nInAColumn(1, r, c, 3, board)>-1)
               return nInAColumn(1, r, c, 3, board);
      for(int r=3; r<board.length; r++)
         for(int c=3; c<board[0].length; c++)
            if(nInALeftDiagonal(1, r, c, 3, board)>-1)
               return nInALeftDiagonal(1, r, c, 3, board);
      for(int r=3; r<board.length; r++)
         for(int c=0; c<board[0].length-3; c++)
            if(nInARightDiagonal(1, r, c, 3, board)>-1)
               return nInARightDiagonal(1, r, c, 3, board);
      for(int i=3; i>0; i--)
      {
         for(int r=0; r<board.length; r++)
            for(int c=3; c<board[0].length; c++)
               if(nInARow(0, r, c, i, board)>-1)
                  return nInARow(0, r, c, i, board);
         for(int r=3; r<board.length; r++)
            for(int c=0; c<board[0].length; c++)
               if(nInAColumn(0, r, c, i, board)>-1)
                  return nInAColumn(0, r, c, i, board);
         for(int r=3; r<board.length; r++)
            for(int c=3; c<board[0].length; c++)
               if(nInALeftDiagonal(0, r, c, i, board)>-1)
                  return nInALeftDiagonal(0, r, c, i, board);
         for(int r=3; r<board.length; r++)
            for(int c=0; c<board[0].length-3; c++)
               if(nInARightDiagonal(0, r, c, i, board)>-1)
                  return nInARightDiagonal(0, r, c, i, board);
      }
      return randomIndex();
   }
   public static boolean fourInARow(int player, int r, int col, int[][] game)
   {
      for(int c=col-3; c<col+1; c++)
         if(game[r][c]!=player)
            return false;
      return true;
   }
   public static boolean fourInAColumn(int player, int row, int c, int[][] game)
   {
      for(int r=row-3; r<row+1; r++)
         if(game[r][c]!=player)
            return false;
      return true;
   }
   public static boolean fourInALeftDiagonal(int player, int r, int c, int[][] game)
   {
      for(int i=0; i<4; i++)
         if(game[r-i][c-i]!=player)
            return false;
      return true;
   }
   public static boolean fourInARightDiagonal(int player, int r, int c, int[][] game)
   {
      for(int i=0; i<4; i++)
         if(game[r-i][c+i]!=player)
            return false;
      return true;
   }
   public static boolean playerWon(int player, int[][] game)
   {
      for(int r=0; r<game.length; r++)
         for(int c=3; c<game[0].length; c++)
            if(fourInARow(player, r, c, game))
               return true;
      for(int r=3; r<game.length; r++)
         for(int c=0; c<game[0].length; c++)
            if(fourInAColumn(player, r, c, game))
               return true;
      for(int r=3; r<game.length; r++)
         for(int c=3; c<game[0].length; c++)
            if(fourInALeftDiagonal(player, r, c, game))
               return true;
      for(int r=3; r<game.length; r++)
         for(int c=0; c<game[0].length-3; c++)
            if(fourInARightDiagonal(player, r, c, game))
               return true;
      return false;
   }
   public static boolean boardIsFull()
   {
      for(int c=0; c<board[0].length; c++)
         if(board[0][c]==2)
            return false;
      return true;
   }
   public static void setCol(int c)
   {
      col=c;
   }
   public static String getMessage()
   {
      return message;
   }
   public static void playGame(boolean playingAgainstAI)
   {
      fillBoard();
      name1 = JOptionPane.showInputDialog("What is player one's name?");
      if(playingAgainstAI)
         name2="The AI";
      else
         name2 = JOptionPane.showInputDialog("What is player two's name?");
      while(!(playerWon(0, board)||playerWon(1, board)||boardIsFull()))
      {
         doTurn(0, false);
         if(!playerWon(0, board))
            doTurn(1, playingAgainstAI);
      }
      if(playerWon(0, board))
         message=name1+" won!";
      else
         if(playerWon(1, board))
            message=name2+" won!";
         else
            message="It's a tie!";
      cp.repaint();
      frame.repaint();
   }
   public static void main(String[] args)
   {
      new connectFour();
      while(option!=3)
      {
         option=0;
         while(option<1||option>3)
         {
            typed=null;
            typed = JOptionPane.showInputDialog("What would you like to do?\n1) Play against a friend.\n2) Play against the AI.\n3) Exit.");
            if(typed==null||typed.equals(""))
               System.exit(0);
            if(typed.equals("1")||typed.equals("2")||typed.equals("3"))
               option=Integer.parseInt(typed);
            message="";
            if(option==1)
            {
               frame.setVisible(true);
               playGame(false);
            }
            if(option==2)
            {
               frame.setVisible(true);
               playGame(true);
            }
         }
      }
      System.exit(0);
   }
}