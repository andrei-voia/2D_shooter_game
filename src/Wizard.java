
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject{
	
	private Game game;
	private BufferedImage wizardImage;
	
	public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, handler, ss);		
		this.game=game;
		wizardImage = ss.grabImage(1, 1, 32, 32);
	}
	
	
	private void collision()
	{
		for(int i=0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					x += velX * -1;
					y += velY * -1;
				}
			}
			
			if(tempObject.getId() == ID.Crate)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					game.ammo+=10;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ID.Enemy)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					game.hp--;
				}
			}
			
		}
	}


	public void tick() {
		x += velX;
		y += velY;
		
		//collision
		collision();
		
		//player movement
		if(handler.isUp()) velY = -5;
		else if(!handler.isDown()) velY = 0;	//without lag
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;		//without lag
		
		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;	//without lag
		
		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;	//without lag
	}


	public void render(Graphics g) {

		g.setColor(Color.green);
		g.drawImage(wizardImage, x, y, null);
	}


	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);
	}

}
