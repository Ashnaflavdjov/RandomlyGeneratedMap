import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class App extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 1280, HEIGHT = 720;
    public static JFrame frame;
    public static GameMap map;
    public static Player player;

    private boolean isRunning = false;
    private Thread thread;
    private BufferedImage image;

    public App() {

        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame = new JFrame("JooJ");
        frame.add(this);
        frame.setResizable(true);
        // frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        map = new GameMap(1000, 1000);

        player = new Player(map.firstRoom.x, map.firstRoom.y);
    }

    private void Tick() {

        map.Tick();
        player.Tick();
    }

    private void Render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {

            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setFont(new Font("arial", Font.BOLD, 17));

        map.Render(g);

        player.Render(g);

        g.dispose();
        g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

        bs.show();
    }

    public synchronized void start() {

        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {

        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        App game = new App();
        game.start();
    }

    @Override
    public void run() {

        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        // int frames = 0;
        double timer = System.currentTimeMillis();

        while (isRunning) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {

                Tick();
                Render();
                // frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {

                // frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            player.MoveX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            player.MoveX(1);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {

            player.MoveY(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            player.MoveY(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}