import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseChecker extends MouseAdapter {

    GamePanel panel;
    public MouseChecker(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        panel.mouseClicked(e);
    }
}
