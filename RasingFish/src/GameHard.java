import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;


public class GameHard extends JFrame implements Runnable
{
	JTabbedPane tabpane; //탭 패널
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



	public GameHard(String msg)
	{	
		setTitle(msg);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Font font = new Font("휴먼편지체", Font.BOLD, 30);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));


		//		--top 시작-------------------------------------------------------------

		//top => 현재 잔고, 물고기 수, 남은 시간
		top = new JPanel();
		top.setLayout(new GridLayout(1,3));

		//돈을 관리해주는 패널
		money = new JPanel();
		money.setSize(200, 50);
		moneyL = new JLabel("잔고 : " + money_now);
		moneyL.setFont(font);
		money.add(moneyL);

		//물고기 수를 알려주는 패널
		fish_num = new JPanel();
		fish_num.setSize(200,50);
		fishL = new JLabel("물고기 수 : " + AquaPanel.f_size);
		fishL.setFont(font);
		fish_num.add(fishL);

		//시간 알려주는 패널
		time = new JPanel();
		time.setSize(200,50);
		timeL = new JLabel("남은 시간 : " + time_now);
		timeL.setFont(font);
		time.add(timeL);

		//돈을 버는 버튼
		get_money = new JButton("돈벌기");
		get_money.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				money_now += 10;
				moneyL.setText("돈벌기 버튼 : " + money_now);
			}

		});

		//물을 정화해주는 버튼
		cleanWater = new JButton("물 정화");
		cleanWater.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if (money_now >= 100) {
					AquaPanel.green = 0;
					money_now = money_now - 100;
				}
			}

		});
		get_money.setBackground(new Color(180, 198, 255));
		cleanWater.setBackground(new Color(151, 170, 255));
		//button 패널에 두 버튼을 넣는다
		buttonPanel.add(get_money);
		buttonPanel.add(cleanWater);

		//top패널에는 돈, 물고기 수, 시간 정보를 담음
		top.add(money);
		top.add(fish_num);
		top.add(time);

		aqua = new JPanel();
		aqua.setLayout(new BorderLayout());
		aqua.add("East", buttonPanel);

		//아쿠아에 buttonPanel, aqua패널을 넣는다
		JPanel aquaPanel = new AquaPanel();
		aqua.add("Center", aquaPanel);

		//tabpane 설정, buttonPanel, aquaPanel을 넣은 aqua를 넣음
		tabpane = new JTabbedPane();
		tabpane.addTab("수족관", aqua);

		add("North", top);
		add("Center", tabpane);
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null); //창이 모니터 가운데에 뜨도록

	}

	//스레드를 조정해주는 메소드
	public void run()
	{

		for (time_now=60; time_now>=0; time_now--)
		{
			timeL.setText("남은 시간 : " + (int)time_now/60 + " 분 " + time_now%60 + "초");


			//점차 초록색으로 변한다
			if(AquaPanel.green == 0)
			{
				repaint();
				moneyL.setText("잔고 : " + money_now);
			}
			AquaPanel.green+=4; //난이도 조절의 핵심

			try
			{
				Thread.sleep(1000);
				fishL.setText("물고기 수: "+ AquaPanel.f_size);
				repaint();
			} catch(Exception e)
			{
			}
			
			if(AquaPanel.f_size<5) {
				Over = true;
				break;
			}

		}
		
		if (AquaPanel.f_size >= 5)
		{
			Clear = true;
		}
		
		
	}
	

}