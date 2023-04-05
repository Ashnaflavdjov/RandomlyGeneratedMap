import java.awt.Color;
import java.awt.Graphics;

public class Room {

    public int x, y;
    public int width = 100, height = 100;

    Color color = Color.ORANGE;

    public boolean cleared = false;

    public Room(int newX, int newY) {

        x = newX;
        y = newY;
    }

    public void Tick() {

        if (!cleared) {

            if (App.player.x == x && App.player.y == y) {

                color = Color.RED;
                App.map.GenerateRoomsAround(this);
                cleared = true;
            }
        }
    }

    public void Render(Graphics g) {

        g.setColor(color);
        g.fillRect((x * width) - Camera.x, (y * height) - Camera.y, width, height);
        g.setColor(Color.white);
        g.drawString(x + ", " + y, (x * width) + (width / 5) - Camera.x,
                (y * height) + (height / 2) - Camera.y);
    }
}