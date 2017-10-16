//Abstract Window Toolkit
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//UserInterface extends JPanel for window drawing from "javax.swing.*", and implements Mouse recognition from "java.awt.event.*"
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int x = 0, y = 0;
   //creates a magenta box and blue box in the Window at position x: 200, y: 200
    public void paintComponent(Graphics g){

         //https://stackoverflow.com/questions/28724609/what-does-super-paintcomponentg-do
        super.paintComponent(g);

        this.setBackground(Color.DARK_GRAY);
       //implements MouseListener capability
        this.addMouseListener(this);

        //implements MouseMotionListener capability
        this.addMouseMotionListener(this);

        //rgb color fuchsia rectangle
        g.setColor(new Color(255,0,255));

        //drawn first
        g.fillRect(x-20 ,y-20,40,40);

        //rgb color white rectangle
        g.setColor(new Color(255,255,255));

        //drawn second
        g.fillRect(50,50,50,50); //draws rectangle 2
        g.drawString("Chess Engine, baby!", x, y);
        Image chessPieceImage;

        //declares the image ChessPieces.png as a new image.
        chessPieceImage = new ImageIcon("ChessPieces.png").getImage();

        //takes the White King image from the sheet of all pieces. dx and dy mean "Destination", sx and xy mean "Source"
        //each image is 64 pixels away from each other
        g.drawImage(chessPieceImage,x,y,x+64,y+64,0,0,64,64,this);
    }

    //must implement all procedures for Mouse Controls, even if blank, otherwise won't run.

    //MouseListener procedures: https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseListener.html

   //@Override

   //works by clicking on screen and releasing, only in same mouse position only
    public void mouseClicked(MouseEvent e){}

    //detects mouse position when cursor enters the window.
    public void mouseEntered(MouseEvent e){}

    //detects mouse position when cursor exits the window
    public void mouseExited(MouseEvent e){
    }

    //works by clicking on screen, does not detect released state
    public void mousePressed(MouseEvent e){}

    //works by detecting last-known mouse position after releasing left mouse button
    public void mouseReleased(MouseEvent e){}


    //MouseMotionListener procedures: https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseMotionListener.html

    //works with live feedback when mouse is moved (clicking not required)
    public void mouseMoved(MouseEvent e){}

    //works by moving on-screen items only when mouse is held down and moving, real-time feedback.
    public void mouseDragged(MouseEvent e){
        x = e.getX(); //changes x's value to where mouse clicks on window for x
        y = e.getY(); //changes y's value to where mouse clicks on window for y
        repaint(); //repaints / draws square based on x and y new position.
    }
}


//https://www.youtube.com/watch?v=NMAdwramt1g&list=PLQV5mozTHmaffB0rBsD6m9VN1azgo5wXl&index=3


