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
		easybtn = new JButton("옐로우 피쉬");
		normalbtn = new JButton("블루 피쉬");
		hardbtn = new JButton("금붕어");
		icon = new ImageIcon("난이도설명.png"); 
		
		easybtn.setBackground(new Color(197, 225, 240));
		normalbtn.setBackground(new Color(197, 225, 240));
		hardbtn.setBackground(new Color(197, 225, 240));
		
		Font font = new Font("맑은 고딕", Font.BOLD, 30);
		JLabel title = new JLabel("물고기를 선택하세요");
		title.setFont(font);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(170, 150, 700, 100);		
		
		//버튼 넣을 패널
		buttonPanel = new JPanel();
		buttonPanel.add(easybtn);
		buttonPanel.add(normalbtn);
		buttonPanel.add(hardbtn);
		buttonPanel.setBackground(new Color(91, 162, 207));
		//버튼 리스너
		ButtonListener btl = new ButtonListener();
		easybtn.addActionListener(btl);
		normalbtn.addActionListener(btl);
		hardbtn.addActionListener(btl);
			
		//버튼패널을 넣을 패널	
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
		
		//jframe 설정
		add(topPanel);
		setTitle("물고기를 선택하세요");
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
