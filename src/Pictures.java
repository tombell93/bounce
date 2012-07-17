import java.applet.AudioClip;
import java.awt.Image;
import java.net.URL;


public class Pictures {

	static Image platform, ball;
	URL url;
	static StartingPoint sp;
	static AudioClip music, wind, bounce;
	static int level = 1;
	
	public Pictures(StartingPoint sp) {
		// TODO Auto-generated constructor stub
		try{
			url = sp.getDocumentBase();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		music = sp.getAudioClip(url, "music/bass_drip.au");
		bounce = sp.getAudioClip(url, "music/click.au");
		wind = sp.getAudioClip(url, "music/down.au");
		
		platform = sp.getImage(url, "images/platform.png");
		this.sp = sp;
	}
}
