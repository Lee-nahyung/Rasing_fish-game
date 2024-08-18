import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class AquaPanel extends JPanel {
    ImageIcon icon;
    public static int green = 0;
    public static int f_size = 15; // ���� ����� ���� �ǹ� (�ִ� 15)
    public static int initial_fish_count = 15; // �ʱ� ����� �� (�迭 ũ��)

    public static Fish[] fish;
    ImageIcon f;
    int click_x = 0, click_y = 0;
    int attack_x = 0, attack_y = 0;
    BufferedImage feed = null;
    public static int i;

    Enemy enm;
    ImageIcon img;

    ImageIcon over, clear;

    boolean effect = false;
    boolean attack = false;
    ImageIcon food, damage;

    public static int fish_num = 0; // ����� ������ ��Ÿ���� ����

    public AquaPanel() {
        fish = new Fish[initial_fish_count]; // �ʱ� ����� ���� �迭 ũ�� ����
        log("Initial number of fish: " + initial_fish_count);  // �α� �߰�: �ʱ� ����� ��

        synchronized (this) {  // ����ȭ �߰�
            for (i = 0; i < initial_fish_count; i++) {  // �迭 ũ�⸦ �������� �ݺ�
                fish[i] = new Fish(this, (int)(Math.random() * 500) + 1, (int)(Math.random() * 500) + 1);
                fish[i].start();
                incrementFishCount();
            }
        }

        addMouseListener(new MouseAdapter() {  // ����� Ŭ�� �̺�Ʈ ó��
            public void mousePressed(MouseEvent e) {
                click_x = e.getX();
                click_y = e.getY();

                synchronized (AquaPanel.this) {  // ����ȭ �߰�
                    for (int i = 0; i < initial_fish_count; i++) {  // �迭 ũ�⸦ �������� �ݺ�
                        if (fish[i].life > 0 && fish[i].x < click_x && click_x < fish[i].x + 100 && fish[i].y < click_y && click_y < fish[i].y + 100) {
                            fish[i].Life();
                            log("Fish " + i + " life reset to 10");  // �α� �߰�: ����� �� ȸ��
                            effect = true;
                            log("Effect set to true");  // �α� �߰�: effect ���� ����
                        }
                    }
                }
                repaint(); // �����ʿ����� repaint ȣ��
            }

            public void mouseReleased(MouseEvent e) {
                effect = false;
                log("Effect set to false");  // �α� �߰�: effect ���� ����
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {  // ���콺 ������ ���� ������
            public void mouseMoved(MouseEvent e) {
                click_x = e.getX();
                click_y = e.getY();
                repaint(); // �� �κп����� repaint ȣ��
            }
        });

        try {
            feed = ImageIO.read(new File("����.png"));
        } catch (IOException e1) {
            System.out.println("no image");
            System.exit(1);
        }

        addMouseListener(new MouseAdapter() {  // ��� ���� �̺�Ʈ ó�� ������
            public void mousePressed(MouseEvent e) {
                attack_x = e.getX();
                attack_y = e.getY();
                synchronized (AquaPanel.this) {  // ����ȭ �߰�
                    if((enm.x < attack_x && attack_x < enm.x + 200) && (enm.y < attack_y && attack_y < enm.y + 200)) {
                        effect = false;
                        log("Effect set to false");  // �α� �߰�: effect ���� ����
                        attack = true;
                        log("Attack set to true");  // �α� �߰�: attack ���� ����
                        Enemy.life--;
                        log("Shark life decreased, current life: " + Enemy.life);  // �α� �߰�: ��� ����� ����
                    }
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                attack = false;
                log("Attack set to false");  // �α� �߰�: attack ���� ����
                repaint();
            }
        });

        int start_x = (int)(Math.random() * 700) + 1;
        int start_y = (int)(Math.random() * 500) + 1;

        enm = new Enemy(this, start_x, start_y);
        enm.start();

        setVisible(true);

        icon = new ImageIcon("������1.png");
    }
    //������� ������ �������ִ� �ڵ�
  	public void ChangeAppearance(int n) {
  		fish_num = n;
  	}
  	
    @Override
    public synchronized void paintComponent(Graphics g) {  // ����ȭ �߰�
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);

        Color c = new Color(0, 255, 0, green * 2);
        g.setColor(c);
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

        if (fish_num == 1) {
            f = new ImageIcon("fish5.png");
        } else if (fish_num == 2) {
            f = new ImageIcon("����ǽ�.png");
        } else if (fish_num == 3) {
            f = new ImageIcon("�ݺؾ�.png");
        } else {
        	f = new ImageIcon("fish5.png");
        	log("����� �̹����� ���޵��� ����");
        }

        synchronized (this) { // �׸��� �۾� ����ȭ
            for (int i = 0; i < fish.length; i++) {  // �迭�� ���� ũ�⸦ �������� �ݺ�
                if (fish[i] != null && fish[i].life > 0) {  // ����Ⱑ null�� �ƴϰ�, ������� 0 �̻��� ���� �׸���
                    g.drawImage(f.getImage(), fish[i].x, fish[i].y, null);
                }
            }
        }

        g.drawImage(feed, click_x - 30, click_y - 30, null);
        if (effect) {  // ȿ���� �� ���� �׸����� ����
            food = new ImageIcon("����ȿ��.png");
            g.drawImage(food.getImage(), click_x, click_y, null);
            effect = false;  // ȿ���� �׸� �� false�� ����
        }

        // ���� ���� üũ: ����� ���� 0�� �Ǹ� ���� ����
        if (getFishCount() <= 0) { 
            over = new ImageIcon("gameover.png");
            g.drawImage(over.getImage(), 80, 100, null);
        } else {
            if (GameEasy.Over) {
                over = new ImageIcon("over.png");
                g.drawImage(over.getImage(), 80, 100, null);
            } else if (GameEasy.Clear) {
                clear = new ImageIcon("clear.png");
                g.drawImage(clear.getImage(), 100, 100, null);
            }

            if (GameNormal.Over) {
                over = new ImageIcon("over.png");
                g.drawImage(over.getImage(), 80, 100, null);
            } else if (GameNormal.Clear) {
                clear = new ImageIcon("clear.png");
                g.drawImage(clear.getImage(), 100, 100, null);
            }

            if (GameHard.Over) {
                over = new ImageIcon("over.png");
                g.drawImage(over.getImage(), 80, 100, null);
            } else if (GameHard.Clear) {
                clear = new ImageIcon("clear.png");
                g.drawImage(clear.getImage(), 100, 100, null);
            }
        }

        img = new ImageIcon("shark.png");
        g.drawImage(img.getImage(), enm.x, enm.y, null);

        if (attack) {
            damage = new ImageIcon("����.png");
            g.drawImage(damage.getImage(), attack_x - 50, attack_y, null);
        }
    }

    // ���� �ڿ��� f_size�� �����ϰ� ����ϱ� ���� �޼���
    public synchronized void incrementFishCount() {
        if (f_size < 15) {
            f_size++;
            log("Fish count incremented, new count: " + f_size);
        }
    }

    public synchronized void decrementFishCount() {
        if (f_size > 0) {
            f_size--;
            log("Fish count decremented, new count: " + f_size);
        }
    }

    public synchronized int getFishCount() {
        return f_size;
    }

    // �α׸� ����ϴ� �޼��� �߰�
    private void log(String message) {
        System.out.println(message);
    }
}