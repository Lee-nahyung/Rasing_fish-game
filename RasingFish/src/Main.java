import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame{

	JScrollPane scrollPane;
	ImageIcon icon;
	JLabel title;
	JButton start; //, rule;

	
	public Main() {
		icon = new ImageIcon("������1.png");
		
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

	background.setLayout(null);
	Font font = new Font("���� ���", Font.BOLD, 50);
	JLabel title = new JLabel("����� Ű��� ����");
	title.setFont(font);
	title.setForeground(Color.WHITE);
	
	JButton start = new JButton("���� ����");
	start.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();//������ ������ �����Ŵ
			new FIshAppearance();
		}
	});
	start.setBackground(new Color(197, 225, 240));
	title.setBounds(170, 150, 700, 100);
	start.setBounds(350, 300, 100, 30);
	
	background.add(title);
	background.add(start);

	add(background);
	
	setTitle("����� Ű��� ����");
	setSize(800,600);
	setVisible(true);
	setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}