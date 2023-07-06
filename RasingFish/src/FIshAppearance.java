import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FIshAppearance extends JFrame{
	
	ImageIcon icon;
	JButton easybtn, normalbtn, hardbtn;
	JPanel topPanel;
	JPanel buttonPanel;
	
	public FIshAppearance() {
		easybtn = new JButton("���ο� �ǽ�");
		normalbtn = new JButton("��� �ǽ�");
		hardbtn = new JButton("�ݺؾ�");
		icon = new ImageIcon("���̵�����.png"); 
		
		easybtn.setBackground(new Color(197, 225, 240));
		normalbtn.setBackground(new Color(197, 225, 240));
		hardbtn.setBackground(new Color(197, 225, 240));
		
		Font font = new Font("���� ���", Font.BOLD, 30);
		JLabel title = new JLabel("����⸦ �����ϼ���");
		title.setFont(font);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(170, 150, 700, 100);		
		
		//��ư ���� �г�
		buttonPanel = new JPanel();
		buttonPanel.add(easybtn);
		buttonPanel.add(normalbtn);
		buttonPanel.add(hardbtn);
		buttonPanel.setBackground(new Color(91, 162, 207));
		//��ư ������
		ButtonListener btl = new ButtonListener();
		easybtn.addActionListener(btl);
		normalbtn.addActionListener(btl);
		hardbtn.addActionListener(btl);
			
		//��ư�г��� ���� �г�	
		JPanel topPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);		
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		topPanel.setLayout(new BorderLayout());
		topPanel.add(title, BorderLayout.NORTH);
		topPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		//jframe ����
		add(topPanel);
		setTitle("����⸦ �����ϼ���");
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			AquaPanel aquaPanel = new AquaPanel();
			
			if(e.getSource()==easybtn) {
				aquaPanel.ChangeAppearance(1);
				dispose();
				new SetDifficultPanel();
			}
			else if(e.getSource()==normalbtn) {
				aquaPanel.ChangeAppearance(2);
				dispose();
				new SetDifficultPanel();
			}
			else if(e.getSource()==hardbtn) {
				aquaPanel.ChangeAppearance(3);
				dispose();
				new SetDifficultPanel();
			}
		}
	}	
}
