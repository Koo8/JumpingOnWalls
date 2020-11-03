import java.awt.*;

public class Wall {
    int x,y, width, height;
    Rectangle hitbox;
    int startX;
    Color color;

    public Wall(Color color, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.color = color;
        //OO VERY IMPORTANT: each wall has its own startX, this should stay unchanged, only the caremaX is changing to move the wall.
        startX = x;
        this.width = width;
        this.height = height;

        hitbox = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x, y, width, height);
        gtd.setColor(color);
        gtd.fillRect(x+1, y+1, width -2, height-2);
    }


    public int set(int cameraX) {
        x = startX + cameraX;
        // move hitbox.x to x always
        hitbox.x = x;
        return x;
    }
}
