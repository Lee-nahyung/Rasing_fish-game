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
    public static int f_size = 15; // 현재 물고기 수를 의미 (최대 15)
    public static int initial_fish_count = 15; // 초기 물고기 수 (배열 크기)

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

    public static int fish_num = 0; // 물고기 외형을 나타내는 변수

    public AquaPanel() {
        fish = new Fish[initial_fish_count]; // 초기 물고기 수로 배열 크기 설정
        log("Initial number of fish: " + initial_fish_count);  // 로그 추가: 초기 물고기 수

        synchronized (this) {  // 동기화 추가
            for (i = 0; i < initial_fish_count; i++) {  // 배열 크기를 기준으로 반복
                fish[i] = new Fish(this, (int)(Math.random() * 500) + 1, (int)(Math.random() * 500) + 1);
                fish[i].start();
                incrementFishCount();
            }
        }

        addMouseListener(new MouseAdapter() {  // 물고기 클릭 이벤트 처리
            public void mousePressed(MouseEvent e) {
                click_x = e.getX();
                click_y = e.getY();

                synchronized (AquaPanel.this) {  // 동기화 추가
                    for (int i = 0; i < initial_fish_count; i++) {  // 배열 크기를 기준으로 반복
                        if (fish[i].life > 0 && fish[i].x < click_x && click_x < fish[i].x + 100 && fish[i].y < click_y && click_y < fish[i].y + 100) {
                            fish[i].Life();
                            log("Fish " + i + " life reset to 10");  // 로그 추가: 물고기 피 회복
                            effect = true;
                            log("Effect set to true");  // 로그 추가: effect 상태 변경
                        }
                    }
                }
                repaint(); // 리스너에서만 repaint 호출
            }

            public void mouseReleased(MouseEvent e) {
                effect = false;
                log("Effect set to false");  // 로그 추가: effect 상태 변경
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {  // 마우스 움직임 감지 리스너
            public void mouseMoved(MouseEvent e) {
                click_x = e.getX();
                click_y = e.getY();
                repaint(); // 이 부분에서만 repaint 호출
            }
        });

        try {
            feed = ImageIO.read(new File("먹이.png"));
        } catch (IOException e1) {
            System.out.println("no image");
            System.exit(1);
        }

        addMouseListener(new MouseAdapter() {  // 상어 공격 이벤트 처리 리스너
            public void mousePressed(MouseEvent e) {
                attack_x = e.getX();
                attack_y = e.getY();
                synchronized (AquaPanel.this) {  // 동기화 추가
                    if((enm.x < attack_x && attack_x < enm.x + 200) && (enm.y < attack_y && attack_y < enm.y + 200)) {
                        effect = false;
                        log("Effect set to false");  // 로그 추가: effect 상태 변경
                        attack = true;
                        log("Attack set to true");  // 로그 추가: attack 상태 변경
                        Enemy.life--;
                        log("Shark life decreased, current life: " + Enemy.life);  // 로그 추가: 상어 생명력 감소
                    }
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                attack = false;
                log("Attack set to false");  // 로그 추가: attack 상태 변경
                repaint();
            }
        });

        int start_x = (int)(Math.random() * 700) + 1;
        int start_y = (int)(Math.random() * 500) + 1;

        enm = new Enemy(this, start_x, start_y);
        enm.start();

        setVisible(true);

        icon = new ImageIcon("수족관1.png");
    }
    //물고기의 외형을 변경해주는 코드
  	public void ChangeAppearance(int n) {
  		fish_num = n;
  	}
  	
    @Override
    public synchronized void paintComponent(Graphics g) {  // 동기화 추가
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);

        Color c = new Color(0, 255, 0, green * 2);
        g.setColor(c);
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

        if (fish_num == 1) {
            f = new ImageIcon("fish5.png");
        } else if (fish_num == 2) {
            f = new ImageIcon("블루피쉬.png");
        } else if (fish_num == 3) {
            f = new ImageIcon("금붕어.png");
        } else {
        	f = new ImageIcon("fish5.png");
        	log("물고기 이미지가 전달되지 않음");
        }

        synchronized (this) { // 그리기 작업 동기화
            for (int i = 0; i < fish.length; i++) {  // 배열의 실제 크기를 기준으로 반복
                if (fish[i] != null && fish[i].life > 0) {  // 물고기가 null이 아니고, 생명력이 0 이상일 때만 그리기
                    g.drawImage(f.getImage(), fish[i].x, fish[i].y, null);
                }
            }
        }

        g.drawImage(feed, click_x - 30, click_y - 30, null);
        if (effect) {  // 효과를 한 번만 그리도록 제어
            food = new ImageIcon("먹이효과.png");
            g.drawImage(food.getImage(), click_x, click_y, null);
            effect = false;  // 효과를 그린 후 false로 설정
        }

        // 게임 오버 체크: 물고기 수가 0이 되면 게임 오버
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
            damage = new ImageIcon("공격.png");
            g.drawImage(damage.getImage(), attack_x - 50, attack_y, null);
        }
    }

    // 공유 자원인 f_size를 안전하게 사용하기 위한 메서드
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

    // 로그를 출력하는 메서드 추가
    private void log(String message) {
        System.out.println(message);
    }
}