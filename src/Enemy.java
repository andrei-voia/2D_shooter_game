
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{

	private Random r = new Random();
	private int choose = 0;
	private int hp = 100;
	private BufferedImage enemy;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		enemy = ss.grabImage(4, 1, 32, 32);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		choose = r.nextInt(10);
		
		//check collision
		for(int i=0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block)
			{
				if(getBoundsBig().intersects(tempObject.getBounds()))
				{
					x += (velX*5) * -1;
					y += (velY*5) * -1;
					velX *= -1;
					velY *= -1;
				}
				//random number between -4 and 4
				else if(choose == 0)
				{
					velX =(r.nextInt(4 - -4) + -4);
					velY =(r.nextInt(4- -4) + -4);
				}
			}
			
			if(tempObject.getId() == ID.Bullet)
			{
				if(getBounds().intersects(tempObject.getBounds())) hp -= 50;
			}
		}
		
		if(hp<=0)handler.removeObject(this);
	}

	
	@Override
	public void render(Graphics g) {
		g.drawImage(enemy, x, y, null);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	
	public Rectangle getBoundsBig() {
		return new Rectangle(x-16, y-16, 64, 64);
	}
	
}
