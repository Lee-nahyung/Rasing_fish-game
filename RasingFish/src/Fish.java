
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Random;

public class Fish extends Thread{
	int x;
	int y;
	JPanel app;
	int life;

	public Fish(JPanel _app, int x, int y) {
		app = _app;
		this.x = x;
		this.y = y;		
	}
	
	public void run() {

		life = 10;
		while(life > 0) {
			int next_x = new Random().nextInt(600)+30;
			int next_y = new Random().nextInt(350)+30;

			while(!(x == next_x && y == next_y)) {
				if (x > next_x) x -= 1;
				else if (x < next_x) x += 1;
				else if (y > next_y) y -= 1;
				else if (y < next_y) y += 1;
				
				app.repaint();
				
				try {

					if (Enemy.life > 0 || AquaPanel.green > 15) {
						sleep(5);
					}
					else {
						sleep(10);
					}
				}catch (Exception ex) {
				}
			}
			life--;
			app.repaint();

		}
		
		if (life <= 0) {
			x = -50; y = -50;
			app.repaint();
			AquaPanel.f_size--;
		}
	
	}

	public void Life() {
		this.life = 10;
	}
	
}