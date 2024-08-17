import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class BrickBreaker extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int paddleX = 250;
    private int ballX = 300, ballY = 300;
    private int ballDX = 2, ballDY = -2;
    private final int PADDLE_WIDTH = 100, PADDLE_HEIGHT = 10;
    private final int BALL_SIZE = 20;
    private final List<Rectangle> bricks = new ArrayList<>();
    private final int BRICK_WIDTH = 60, BRICK_HEIGHT = 20;

    public BrickBreaker() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initBricks();
        timer = new Timer(5, this);
        timer.start();
    }

    private void initBricks() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks.add(new Rectangle(j * (BRICK_WIDTH + 10) + 30, i * (BRICK_HEIGHT + 10) + 50, BRICK_WIDTH, BRICK_HEIGHT));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, getHeight() - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
        g.setColor(Color.RED);
        for (Rectangle brick : bricks) {
            g.fillRect(brick.x, brick.y, brick.width, brick.height);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballDX;
        ballY += ballDY;

        if (ballX < 0 || ballX > getWidth() - BALL_SIZE) {
            ballDX = -ballDX;
        }
        if (ballY < 0) {
            ballDY = -ballDY;
        }
        if (ballY > getHeight() - BALL_SIZE) {
            ballX = 300;
            ballY = 300;
            ballDX = 2;
            ballDY = -2;
        }

        Rectangle ballRect = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
        Rectangle paddleRect = new Rectangle(paddleX, getHeight() - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);

        if (ballRect.intersects(paddleRect)) {
            ballDY = -ballDY;
        }

        for (int i = 0; i < bricks.size(); i++) {
            Rectangle brick = bricks.get(i);
            if (ballRect.intersects(brick)) {
                ballDY = -ballDY;
                bricks.remove(i);
                break;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddleX -= 10;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddleX += 10;
        }
        paddleX = Math.max(0, Math.min(paddleX, getWidth() - PADDLE_WIDTH));
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreaker game = new BrickBreaker();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
