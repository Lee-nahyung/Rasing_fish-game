import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GameEasy extends JFrame implements Runnable {
    JTabbedPane tabpane;
    JPanel top;
    JPanel aqua;
    JPanel money, fish_num, time;
    JPanel buttonPanel;

    public static JLabel moneyL, fishL, timeL;
    JButton get_money, cleanWater;

    int time_now;
    public int money_now = 0;
    public static boolean Over = false;
    public static boolean Clear = false;

    public GameEasy(String msg) {
        setTitle(msg);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Font font = new Font("�޸�����ü", Font.BOLD, 30);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        top = new JPanel();
        top.setLayout(new GridLayout(1, 3));

        money = new JPanel();
        money.setSize(200, 50);
        moneyL = new JLabel("�ܰ� : " + money_now);
        moneyL.setFont(font);
        money.add(moneyL);

        fish_num = new JPanel();
        fish_num.setSize(200, 50);
        fishL = new JLabel("����� �� : " + AquaPanel.f_size);
        fishL.setFont(font);
        fish_num.add(fishL);

        time = new JPanel();
        time.setSize(200, 50);
        timeL = new JLabel("���� �ð� : " + time_now);
        timeL.setFont(font);
        time.add(timeL);

        get_money = new JButton("������");

        get_money.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                money_now += 10;
                moneyL.setText("�ܰ� : " + money_now);
            }
        });

        cleanWater = new JButton("�� ��ȭ");
        cleanWater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (money_now >= 100) {
                    AquaPanel.green = 0;
                    money_now = money_now - 100;
                    repaint();
                }
            }
        });

        get_money.setBackground(new Color(180, 198, 255));
        cleanWater.setBackground(new Color(151, 170, 255));

        buttonPanel.add(get_money);
        buttonPanel.add(cleanWater);

        top.add(money);
        top.add(fish_num);
        top.add(time);

        aqua = new JPanel();
        aqua.setLayout(new BorderLayout());
        aqua.add("East", buttonPanel);

        JPanel aquaPanel = new AquaPanel();
        aqua.add("Center", aquaPanel);

        tabpane = new JTabbedPane();
        tabpane.addTab("������", aqua);
        add("North", top);
        add("Center", tabpane);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void run() {
        for (time_now = 60; time_now >= 0; time_now--) {
            timeL.setText("���� �ð� : " + (int) time_now / 60 + " �� " + time_now % 60 + "��");

            if (AquaPanel.green == 0) {
                repaint();
                moneyL.setText("�ܰ� : " + money_now);
            }
            AquaPanel.green++;

            fishL.setText("����� ��: " + AquaPanel.f_size);
            System.out.println("Updated fish count: " + AquaPanel.f_size);

            try {
                Thread.sleep(1000);
                repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (AquaPanel.f_size < 5) {
                Over = true;
                break;
            }
        }
        if (AquaPanel.f_size >= 5) {
            Clear = true;
        }
    }
}