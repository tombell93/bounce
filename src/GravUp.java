import java.awt.Color;
import java.awt.Graphics;


public class GravUp extends Item{

	public GravUp(int x) {
		super(x);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball b) {
		// TODO Auto-generated method stub
		b.setGravity(b.getGravity() + 3);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		super.paint(g);
	}
}
