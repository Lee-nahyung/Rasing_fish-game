import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Random;

public class Fish extends Thread {
    int x;
    int y;
    JPanel app;
    int life;
    AquaPanel aquaPanel;  // AquaPanel �ν��Ͻ� ����

    public Fish(JPanel _app, int x, int y, AquaPanel aquaPanel) {
        app = _app;
        this.x = x;
        this.y = y;
        this.aquaPanel = aquaPanel;  // �ν��Ͻ� �ʱ�ȭ
        life = 10;
    }

    public void run() {
        while (life > 0) {
            int next_x = new Random().nextInt(600) + 30;
            int next_y = new Random().nextInt(350) + 30;

            synchronized (this) {
                while (!(x == next_x && y == next_y)) {
                    if (x > next_x) x -= 1;
                    else if (x < next_x) x += 1;
                    else if (y > next_y) y -= 1;
                    else if (y < next_y) y += 1;

                    app.repaint();

                    try {
                        if (Enemy.life > 0 || AquaPanel.green > 15) {
                            sleep(5);
                        } else {
                            sleep(10);
                        }
                    } catch (Exception ex) {
                    }
                }
                life--;
                app.repaint();
            }

            if (life <= 0) {
                removeFish();
            }
        }
    }

    private void removeFish() {
        x = -50;
        y = -50;
        synchronized (AquaPanel.class) {
            aquaPanel.decrementFishCount();  // AquaPanel �ν��Ͻ��� ���� ȣ��
        }
        SwingUtilities.invokeLater(() -> app.repaint());  // UI ������Ʈ
    }

    public synchronized void Life() {
        this.life = 10;
    }

    public synchronized void decreaseLife() {
        if (life > 0) {
            life--;
            if (life <= 0) {
                removeFish();
            }
        }
    }
}