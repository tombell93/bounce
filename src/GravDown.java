import java.awt.Color;
import java.awt.Graphics;


public class GravDown extends Item{

	public GravDown(int x) {
		super(x);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball b) {
		// TODO Auto-generated method stub
		if(b.getGravity() > 3){
			b.setGravity(b.getGravity() - 3);
			if (b.getGravity() < 3){
				b.setGravity(3);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		super.paint(g);
	}
}
