package attendance;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class CardCheckThread implements Runnable{
	JLabel label1;
	
	public CardCheckThread(JLabel label1) {
		// TODO Auto-generated constructor stub
		this.label1 = label1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 0;
		while(true) {
			
			Date d = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String da = date.format(d);
			label1.setText("" + da);
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
		
}
