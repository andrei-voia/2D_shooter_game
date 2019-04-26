
public class Camera {

	private float x, y;
	
	public Camera(float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	
	
	public void tick(GameObject object)
	{
		x += ((object.getX()- x) - 1920 / 2)*0.05f;
		y += ((object.getY()- y) - 1080 / 2)*0.05f;
		
		//stop camera from going out of the frame
		//try and error method
		if(x <= 0) x = 0;
		if(x >= 1920) x = 1920;
		if(y <= 0) y = 0;
		if(y >= 1080) y = 1080;
	}
	


	//Getters and setters
	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}
	
}
