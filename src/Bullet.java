import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{
	
	private static int SECONDS = 1;
	private double countSeconds;
	
	public Bullet(int Ax, int Ay, ID id, Handler handler, int Bx, int By, SpriteSheet ss) {
		super(Ax, Ay, id, handler, ss);
		
		//speed of the bullet
		//velX = (Bx - Ax) * 0.1f;
		//velY = (By - Ay) * 0.1f;
		
		calculateVelocity(Ax, Ay, Bx, By);
		countSeconds = System.currentTimeMillis() + SECONDS * 1000;
	}
	
	
	//mathematical algorithm
	public void calculateVelocity(int fromX, int fromY, int toX, int toY)
	{
		double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
		double speed = 10;	//speed [2,n) n should be < 20 for normal speed
		//find Y
		velY = (float)((toY - fromY) * speed / distance);
		//find X
		velX = (float)((toX - fromX) * speed / distance);
	}

	
	public void tick() {
		x += velX;
		y += velY;
		
		if(System.currentTimeMillis() > countSeconds)handler.removeObject(this);

		for(int i=0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					handler.removeObject(this);
				}
			}
			
			//delete bullet if out of map
			//if(tempObject.x<0 || tempObject.y<0 )handler.removeObject(this);
		}
	}


	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, 10, 10);
		
	}


	public Rectangle getBounds() {

		return new Rectangle(x, y, 10, 10);
	}

}
