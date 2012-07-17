import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class ScorePlus extends Item{
	
	private StartingPoint appletInfo;
	
	public ScorePlus(int x, StartingPoint appletInfo) {
		super(x);
		// TODO Auto-generated constructor stub
		this.appletInfo = appletInfo;
	}
	
	@Override
	public void performAction(Ball b) {
		// TODO Auto-generated method stub
		Random r = new Random();
		appletInfo.setScore(appletInfo.getScore() + r.nextInt(2000) + 500);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		super.paint(g);
	}
}
