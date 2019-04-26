
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject{
	
	private BufferedImage crate;

	public Crate(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		crate = ss.grabImage(6, 2, 32, 32);

	}

	@Override
	public void tick() {

		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(crate, x, y, null);
	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);
	}
	

}
