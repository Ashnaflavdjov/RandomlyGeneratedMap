import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public int x, y;

    public Player(int posX, int posY) {

        x = posX;
        y = posY;
    }

    public void Tick() {

        UpdateCamera();
    }

    public void Render(Graphics g) {

        g.setColor(new Color(0f, 0.8f, 0.2f, 0.7f));
        g.fillOval(x * 100 - Camera.x + 25, y * 100 - Camera.y + 25, 50, 50);
    }

    public void UpdateCamera() {

        Camera.x = Camera.clamp((x * 100 - (App.WIDTH / 2) + 5), 0,
                App.map.width * 100 - App.WIDTH);
        Camera.y = Camera.clamp((y * 100 - (App.HEIGHT / 2) + 5), 0,
                App.map.height * 100 - App.HEIGHT);
    }

    public void MoveX(int distance) {

        if (x > 0 || x < App.map.width)
            x += distance;
        if (x < 0)
            x = 0;
        if (x > App.map.width - 1)
            x = App.map.width - 1;
    }

    public void MoveY(int distance) {

        if (y > 0 || y < App.map.height)
            y += distance;
        if (y < 0)
            y = 0;
        if (y > App.map.height - 1)
            y = App.map.height - 1;
    }
}
