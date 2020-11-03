import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This game use Jpanel, not canvas, JPanel is passive painting, canvas is active Painting
 * https://docs.oracle.com/javase/tutorial/extra/fullscreen/rendering.html
 * https://www.oracle.com/java/technologies/painting.html
 * How to play: w a d keys move player, w keys is good for jumping when collide only.
 * collision detection against all directions with walls
 * if player falls off screen, reset the game
 * wall terrain can be updated constantly
 * player xPosition is the same, the walls move as if the player is running.
 * click R button to reset
 *
 * OO TODO?? --- set left movement to a limited distance ****
 * set different level
 * set coin for award
 * set enemies to attack
 */

public class GamePanel extends JPanel {

    Player player;
    ArrayList<Wall> walls = new ArrayList<>();
    Timer gameTimer;
    int cameraX;
    int offset;
    Rectangle resetRec, homeRec;
    int life  = 0;

    public GamePanel() {
       // setPreferredSize(new Dimension(700, 700));   // pair with frame.pack() to set the JPanel to a preferred size

        // player
        player = new Player(200, 150, this);
        resetRec = new Rectangle(500, 25 + 30, 50,50);  // 30 is from frame.getInset().top for title bar height
        homeRec = new Rectangle(600, 25 + 30, 50, 50);

        // if add listener on JPanel, must add focusable
        // addKeyListener(new KeyChecker(this));
        //setFocusable(true);

        reset();

        // Timer
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                // control the wall movement by cameraX variable
                for(Wall wall: walls) {
                    wall.set(cameraX);
                }
                // add continuous terrain on condition
                if (walls.get(walls.size() -1).x < 800) {  // if the last wall.x is less than 800;
                    offset += 700; // offset relative to the very original position.
                    System.out.println(walls.size() + " reach END, before recreating new wall \n\n ----------------------- ");

                    makewalls(offset);
                    System.out.println(walls.size() + " in recreating wall");

                }
                // remove walls if pass -800 x position
                for (int i = 0; i <walls.size() ; i++) {
                    if (walls.get(i).x<-800) walls.remove(i);
                }

                
                player.set();  // all updates controlled by player.set()
                repaint();
            }
        }, 0, 17);

    }

    // when player falls off the screen
    public void reset() {
//        System.out.println(" R E S E T  @@@@@@@@@@@@");
        player.x = 200;
        player.y = 150;
        // reset speed to 0
        player.xSpeed = 0;
        player.ySpeed = 0;

        cameraX = 150;

        // rebuild walls
        offset = 0;
        walls.clear();
        makewalls(offset);

        life++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);
        for (Wall wall : walls) {
            wall.draw(gtd);
        }

        // draw buttons
        gtd.setColor(Color.BLACK);
        gtd.drawRect(500, 25, 50,50);
        gtd.drawRect(575, 25, 50, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(501, 26, 48,48);
        gtd.fillRect(576, 26, 48, 48);
        gtd.setColor(Color.BLACK);
        gtd.drawString("R", 522, 55);
        gtd.drawString("H",597, 55);

        // show life
        gtd.setColor(Color.RED);
    }

    public void makewalls(int theOffset) {
        Random rand = new Random();
        int index = rand.nextInt(5);

        if (index == 0) {
            for (int i = 0; i < 14; i++) {
                walls.add(new Wall(Color.BLUE,theOffset + i * 50, 600, 50, 50));
            }
        } else if (index == 1) {
            for (int i= 0; i<5; i++) {
                walls.add(new Wall(Color.CYAN,theOffset + i * 50, 600, 50, 100));
                walls.add(new Wall(Color.CYAN,theOffset + i*50+400, 600, 50, 50));
            }
        } else if (index == 2) {
            for (int i = 0; i< 6; i++) {
                walls.add(new Wall(Color.green, theOffset + i*50, 600, 50, 50));
                walls.add(new Wall(Color.green,theOffset +400 + i*50, 500, 50,50 ));
            }
        } else if (index == 3){
            walls.add(new Wall(Color.YELLOW,theOffset + 50, 600, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 150, 500, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 200, 500, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 250, 500, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 300, 500, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 450, 600, 50, 50));
            walls.add(new Wall(Color.YELLOW,theOffset + 500, 600, 50, 50));
        } else if (index == 4) {
            for(int i = 0; i < 3; i++) {
                walls.add(new Wall(Color.ORANGE, theOffset+i*50, 600, 50, 50));
                walls.add(new Wall(Color.ORANGE, theOffset+i*50, 450, 50, 50));
            }
            for (int i= 6; i<14; i++) {
                walls.add(new Wall(Color.orange, theOffset+i*50, 600,50, 50));
            }
            for (int i = 8; i<12; i++){
                walls.add(new Wall(Color.ORANGE,theOffset+i*50, 550, 50,50));
            }
            for (int i = 9; i <11 ; i++) {
                walls.add(new Wall(Color.orange, theOffset+i*50, 500, 50,50));
            }
        }

    }

    // listener
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') player.keyLeft = true;
        if (e.getKeyChar() == 'd') {
            player.keyRight = true;
        }
        if (e.getKeyChar() == 'w') player.keyUp = true;
        if (e.getKeyChar() == 's') player.keyDown = true;
        if (e.getKeyChar() == 'r') reset();

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') player.keyLeft = false;
        if (e.getKeyChar() == 'd') player.keyRight = false;
        if (e.getKeyChar() == 'w') player.keyUp = false;
        if (e.getKeyChar() == 's') player.keyDown = false;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " , " + e.getY());
        if(resetRec.contains(new Point(e.getX(), e.getY()))) {
            reset();
        }
    }
}
