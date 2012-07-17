import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Random;

public class StartingPoint extends Applet implements Runnable, KeyListener, MouseMotionListener,
		MouseListener{
	
	private Image img;
	private Graphics doubleG;
	Ball b1;
	Platform p[] = new Platform[10];
	Item item[] = new Item[3];
	private int score;
	double cityX = 0;
	double cityDx = .3;
	URL url;
	Image city;
	int levelcheck = 0;
	boolean gameOver = false;
	boolean mouseIn = false;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public void init() {
		setSize(800, 600);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		try{
			url = getDocumentBase();
		}catch (Exception e) {
			// TODO: handle exception
		}
		city = getImage(url, "images/background.png");
		new Pictures(this);
		Pictures.wind.play();
		//Pictures.music.loop(); //background music loops
	}
	
	@Override
	public void start() {
		b1 = new Ball();
		score = 0;
		for (int i = 0; i < p.length; i++){
			Random r = new Random();
			p[i] = new Platform(i*120, 300);
		}
		
		for (int i = 0; i < item.length; i++){
			Random r = new Random();
			switch (r.nextInt(5)){
			case 0:
				item[i] = new GravUp(getWidth() + 2000*i);
				break;
			case 1:
				item[i] = new GravDown(getWidth() + 2000*i);
				break;
			case 2:
				item[i] = new AgilUp(getWidth() + 2000*i);
				break;
			case 3:
				item[i] = new AgilDown(getWidth() + 2000*i);
				break;
			case 4:
				item[i] = new ScorePlus(getWidth() + 2000*i, this);
				break;
			}
		}
		
		Thread thread = new Thread(this); //threads need a run(). Since class is runnable, can use run() as static method
		thread.start(); //starts to implement this classes run() method in a thread
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// thread information
		while(true){
			Random r = new Random();
			
			for(int i = 0; i < p.length; i++){
				int testX = p[i].getX();
				if(testX < 0 - p[i].getWidth()){
					int fakei = i;
					if(i == 0){
						fakei = p.length;
					}
					p[i].setX(p[fakei-1].getX() + p[i].getWidth() + Pictures.level*r.nextInt(25));
				}
			}
			
			gameOver = b1.getGameOver();
			
			if(levelcheck > 1000){
				Pictures.level++;
				levelcheck = 0;
			}			
			levelcheck++;
			if(cityX > getWidth()*-1){
				cityX -= cityDx;
			}else{
				cityX = 0;
			}
			if(!gameOver){
				score++;
			}
			
			
			for(int i = 0; i < item.length; i++){
				if(item[i].isCreateNew()){
					item[i] = null;
					switch (r.nextInt(5)){
					case 0:
						item[i] = new GravUp(getWidth() + 10*r.nextInt(500));
						break;
					case 1:
						item[i] = new GravDown(getWidth() + 10*r.nextInt(500));
						break;
					case 2:
						item[i] = new AgilUp(getWidth() + 10*r.nextInt(500));
						break;
					case 3:
						item[i] = new AgilDown(getWidth() + 10*r.nextInt(500));
						break;
					case 4:
						item[i] = new ScorePlus(getWidth() + 10*r.nextInt(500), this);
						break;
					}
					item[i].setCreateNew(false);
				}
			}
			
			b1.update(this);
			
			for(int i = 0; i < p.length; i++){
				p[i].update(this, b1);
			}
			
			for(int i = 0; i < item.length; i++){
				item[i].update(this, b1);
			}
			
			repaint();
			try {
				Thread.sleep(17); //60 fps so 17 ms sleep
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	@Override
	public void stop() {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void update(Graphics g) {
		// for double buffering
		if (img == null){
			img = createImage(this.getSize().width, this.getSize().height);
			doubleG = img.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		g.drawImage(img, 0, 0, this);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(15, 77, 147));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(city, (int) cityX, 0, this);
		g.drawImage(city, (int) cityX + getWidth(), 0, this);
		
		for(int i = 0; i < p.length; i++){
			p[i].paint(g);
		}
		
		for(int i = 0; i < item.length; i++){
			item[i].paint(g);
		}
		
		b1.paint(g);
		
		String s = Integer.toString(score);
		Font font = new Font("Serif", Font.BOLD, 32);
		g.setFont(font);
		g.setColor(new Color(198, 226, 255));
		g.drawString(s, getWidth() - 150, 50);
		g.setColor(Color.BLACK);
		g.drawString(s, getWidth() - 150 + 2, 50 + 2);
		if(gameOver){
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER!", 300, 300);
			g.drawRect(310, 310, 180, 40);
			if(mouseIn){
				g.setColor(Color.RED);
				g.drawString("Play again?", 320, 340);
			}else{
				g.setColor(Color.WHITE);
				g.drawString("Play again?", 320, 340);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			b1.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			b1.moveRight();
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(gameOver){
			if(e.getX() > 310 && e.getX() < 490){
				if(e.getY() > 310 && e.getY() < 350){
					mouseIn = true;
				}
			}
			if(e.getX() < 310 || e.getX() > 490){
				mouseIn = false;
			}
			if(e.getY() < 310 || e.getY() > 350){
				mouseIn = false;
			}
		}
	}
	
	public void restart(){
		b1 = null;
		b1 = new Ball();
		score = 0;
		levelcheck = 0;
		gameOver = false;
		mouseIn = false;
		for (int i = 0; i < p.length; i++){
			Random r = new Random();
			p[i] = new Platform(i*120, 300);
		}
		
		for (int i = 0; i < item.length; i++){
			Random r = new Random();
			switch (r.nextInt(5)){
			case 0:
				item[i] = new GravUp(getWidth() + 2000*i);
				break;
			case 1:
				item[i] = new GravDown(getWidth() + 2000*i);
				break;
			case 2:
				item[i] = new AgilUp(getWidth() + 2000*i);
				break;
			case 3:
				item[i] = new AgilDown(getWidth() + 2000*i);
				break;
			case 4:
				item[i] = new ScorePlus(getWidth() + 2000*i, this);
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//start new game
		if(mouseIn){
			restart();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
