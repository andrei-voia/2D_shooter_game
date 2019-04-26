import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected int x, y;
	protected float velX = 0, velY = 0;
	protected ID id;
	protected Handler handler;
	protected SpriteSheet ss;
	
	public GameObject(int x, int y, ID id, Handler handler, SpriteSheet ss)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.handler=handler;
		this.ss=ss;
	}


	//what we need in every object
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	
	//Getters and setters
	public ID getId() {
		return id;
	}


	public void setId(ID id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public float getVelX() {
		return velX;
	}


	public void setVelX(float velX) {
		this.velX = velX;
	}


	public float getVelY() {
		return velY;
	}


	public void setVelY(float velY) {
		this.velY = velY;
	}
	
}
