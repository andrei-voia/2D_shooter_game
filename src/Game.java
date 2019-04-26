import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
	
	//irrelevant
	private static final long serialVersionUID = 1L;
	
	//variable
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	
	//textures initialization
	private BufferedImage floor = null;
	
	//world load
	private BufferedImage level = null;
	
	//textures
	private BufferedImage spriteSheet = null;
	private SpriteSheet ss;
	
	//camera movement
	private Camera camera;
	
	//ammo
	public int ammo = 100;
	//health bar
	public int hp = 100;

	public Game()
	{
		new Window(1920, 1080, "Game", this);
		start();
		
		handler = new Handler();
		
		//add camera
		camera = new Camera(0, 0);
		
		//keyboard input (movement)
		this.addKeyListener(new KeyInput(handler));

		//load world
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/map.png");
		
		//load textures
		spriteSheet = loader.loadImage("/textures.png");
		ss = new SpriteSheet(spriteSheet);
		//textures
		floor = ss.grabImage(4, 2, 32, 32);
		
		//mouse input(shoot) // added after SpriteSheet because we used it
		this.addMouseListener(new MouseInput(handler, camera, this, ss));
		
		loadLevel(level);
		
	}
	
	
	private void start()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	private void stop()
	{
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//Load level
	private void loadLevel(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();

		for(int xx=0; xx < w; xx++)
		{
			for(int yy=0; yy < h; yy++)
			{
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255) handler.addObject(new Block(xx*32, yy*32, ID.Block, handler, ss));
				else if(blue == 255 && green == 0) handler.addObject(new Wizard(xx*32,yy*32, ID.Player, handler, this, ss));
				else if(green == 255 && blue == 0) handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler, ss));
				else if(green == 255 && blue == 255) handler.addObject(new Crate(xx*32, yy*32, ID.Crate, handler, ss));
			}
		}
	}
	
	
	//update the game
	public void run() 
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
				render();
			}
			
			
			//frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//frames = 0;
			}
		}
		stop();
	}
			
	
	public void tick()
	{
		//camera movement for player
		for(int i=0; i < handler.object.size(); i++)
		{
			if(handler.object.get(i).getId()== ID.Player)
				camera.tick(handler.object.get(i));
		}
		
		
		handler.tick();
	}
	
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs==null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//camera movement
		Graphics2D g2d = (Graphics2D)g;
		
		//RENDER HERE
		////////////////////////////////////////////////////////////////////////////////////////

		//everything between translate moves with the object
		//draw floor
		g2d.translate(-camera.getX(), -camera.getY());
		
		
		for(int xx=0; xx < 32*100; xx+=32)
		{
			for(int yy=0; yy < 32*100; yy+=32)
			{
				g.drawImage(floor, xx, yy, null);
			}
		}
		
		
		handler.render(g);
		 
		g2d.translate(camera.getX(), camera.getY());
		
		//draw HP bar
		g.setColor(Color.gray);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.green);
		g.fillRect(5, 5, hp*2, 32);
		g.setColor(Color.black);
		g.drawRect(5, 5, 200, 32);
		
		//draw ammo
		g.setColor(Color.WHITE);
		g.drawString("Ammo: "+ ammo, 5, 50);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String[] args)
	{
		new Game();
	}

}
