import java.awt.*;

public class Player {
    int x, y;
    int width, height;
    GamePanel panel;
    double xSpeed, ySpeed;
    Rectangle hitBox;
    boolean keyLeft, keyRight, keyUp, keyDown;

    public Player(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void set() {

        // xSpeed and ySpeed are controlled by keyborad listener
        if (keyLeft && keyRight || !keyLeft && !keyRight) xSpeed *= 0.8;
        else if (keyLeft && !keyRight) xSpeed--;
        else if (!keyLeft && keyRight) xSpeed++;

        if (-0.75 < xSpeed && xSpeed < 0.75) {
            xSpeed = 0;
        }


        if (xSpeed > 7) xSpeed = 7;
        if (xSpeed < -7) xSpeed = -7;

        // for jump
        if (keyUp) {
            // touch horizontal wall check
            hitBox.y++;   // move hitbox down 1 pixel
            for (Wall wall : panel.walls) {
                if (wall.hitbox.intersects(hitBox))
                    ySpeed = -12; // this line makes the player jump when touching a wall underneath
            }
            hitBox.y--; // correct hitbox.y to original
        }

        // gravity
        ySpeed += 0.5;

        // horizontal collision - use xSpeed, which could be plus or minus, to make horizontal collision
        // happens when move right or when move left.

        hitBox.x += xSpeed; // use xSpeed not 1 is to use xSpeed's "plus" or "minus" before the number.
        for (Wall wall : panel.walls) {
            if (wall.hitbox.intersects(hitBox)) {
                hitBox.x -= xSpeed; // so that not in collision zone
               //// System.out.println(" outside while loop " + hitBox.x);
                // constantly hitbox.x changed from 99 to 99+1, from inside while to outside while loop,
                //xspeed is set to be 0 all these time, x is stay ias 99+1 all the time.
                while (!wall.hitbox.intersects(hitBox)) { // while it is not in collision zone..
                    hitBox.x += Math.signum(xSpeed); // keep on moving the hitbox in xSpeed direction by 1... till it collides again, so that out of the while loop
                  ////  System.out.println("inside while loop" + hitBox.x);
                }
                hitBox.x -= Math.signum(xSpeed); // move the hitbox away from the collision zone in the opposite of the xSpeed by 1, now it is out of the collision zone again,
                // THIS keeps the player stay the same x position
                // **** this line moves the cameraX to meet player.x so that player.x stays the same
                panel.cameraX += x - hitBox.x;
                // remove this line to keep player.x staying the same position
                // x = hitBox.x;  // x is kept 1 pixel away from being inside the collision zone
                xSpeed = 0; // xspeed is 0 - to keep the hitbox.x to be 99+1 all the time
            }
        }
        //***** below variables such as hitbox.x and hitbox.y and cameraX and player.y are all changed relative to xSpeed and ySpeed
        // vertical collision
        hitBox.y += ySpeed; // use xSpeed not 1 is to use xSpeed's "plus" or "minus" before the number.
        for (Wall wall : panel.walls) {
            if (wall.hitbox.intersects(hitBox)) {
                hitBox.y -= ySpeed;
                while (!wall.hitbox.intersects(hitBox)) {
                    hitBox.y += Math.signum(ySpeed);
                }
                hitBox.y-= Math.signum(ySpeed);
                ySpeed = 0; // xspeed is 0 - to keep the hitbox.x to be 99+1 all the time
                y = hitBox.y;
            }
        }
        // oo THIS moves the walls. player.x location stay the same, cameraX is moving
        panel.cameraX -= xSpeed;
        // player.y location
        y += ySpeed;


        // hitBox always follow the player
        hitBox.x = x;
        hitBox.y = y;


        // When player falls off screen
        if (y > 800) panel.reset();
    }

    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(x, y, width, height);

//        gtd.setColor(Color.RED);
//        gtd.drawRect(x, y, width, height);

//        // display player location
//        gtd.setColor(Color.BLACK);
//        gtd.drawString(String.valueOf(x), 100, 100);
    }
}
