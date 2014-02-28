package rpg;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class test extends BasicGame{
	Player player;
	Wall wall;
	int timey;
	int  layers;
	boolean pasta;
	TiledMap grassMap;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Mob> mobs = new ArrayList<Mob>();
	ArrayList<Medkit> medkits = new ArrayList<Medkit>();
	int x,y;
	boolean titleScreen = false;
	boolean menu;
	boolean inv = false;
	static int level;
	Image HUD;
	Image health;
	Image ItemHUD;
	Image item1;
	Image item2;
	Sound step1;
	Sound step2;
	int steptimer=0;
	int timer =0;
	boolean stepSwitch=true;
	Sound lasersfx;
	Sound explosion;
	Sound screwsfx;
	Sound sabersfx;
	Rectangle menuButton;
	Rectangle resumeButton;
	Rectangle quitButton;
	Rectangle restartButton, item1Button, item2Button, item3Button;
	boolean title=true;
	Image titleDoor1;
	Image titleDoor2;
	int doorshift = 0;
	boolean doormove = false;
	public test(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		if(title)
		{
			titleDoor1.draw(-doorshift,0);
			titleDoor2.draw(doorshift,0);
		}
		else
		{//order of render: backgrounds -> walls -> items -> player+mobs
		grassMap.render(x-820, y-64,0); // background
		grassMap.render(x-820, y-64,1); // transition
		player.sprite.draw(player.x,player.y);
		for(Medkit meds:medkits)
		{
			meds.image.draw(meds.x,meds.y);
		}
		for(Mob moby:mobs)
		{
			moby.sprite.draw(moby.x,moby.y);
		}
		for (Projectile projecty:projectiles)
		{
			projecty.image.draw(projecty.x,projecty.y);
		}
		for (int i = 2; i<layers;i++)
		{
			grassMap.render(x-820, y-64, i); //objects
		}
		//g.drawString("Mouse one Weapon: " + player.getWeapon1(), 700, 10);
		//g.drawString("Mouse two Weapon: " + player.getWeapon2(), 700, 30);
		HUD.draw(-22,-22);
		ItemHUD.draw(660,340);
		item1.draw(926,418);
		item2.draw(799,534);
		if(player.hp>0)
			health = new Image("res/health/"+player.hp+"hp.png");
		health.draw(114,22);
		if (menu)
		{
			g.drawImage(new Image("res/Menu.png"), 10,020);
		}}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		Music goodMusic;
		menu=false;
		arg0.setMouseCursor(new Image("res/Video Game Tiles - Pixel by Pixel/Cursor.png"), 0, 0);
		step1 = new Sound("res/sound/Step.wav");
		step2 = new Sound("res/sound/Step2.wav");
		lasersfx = new Sound("res/sound/Laser.wav");
		explosion = new Sound("res/sound/Explosion0.wav");
		screwsfx = new Sound("res/sound/ScrewStab.wav");
		sabersfx = new Sound("res/sound/SaberSwoosh.wav");
		player = new Player();
		x=0;
		y=0;
		timey = 0;
		pasta = false;
		HUD = new Image("res/Video Game Tiles - Pixel by Pixel/HUD.png");
		health = new Image("res/health/20hp.png");
		ItemHUD = new Image("res/Video Game Tiles - Pixel by Pixel/Inventory.png");
		item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Screwdriver.png");
		item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Energy Saber.png");
		titleDoor1 = new Image("res/Video Game Tiles - Pixel by Pixel/Top TL.png");
		titleDoor2 = new Image("res/Video Game Tiles - Pixel by Pixel/Door BR.png");
		menuButton = new Rectangle(660+272,340+238,86,32);
		resumeButton = new Rectangle(158,366,312,53);
		restartButton = new Rectangle(158,436,312,53);
		restartButton = new Rectangle(158,508,312,53);
		item1Button = new Rectangle(423,175,161,158);
		item2Button = new Rectangle(582,175,161,158);
		item3Button = new Rectangle(498,352,161,158);
		switch (level)
		{
		case 0:
			goodMusic = new Music("res/music/Drowning In The High Seas.wav");
			goodMusic.loop();
			grassMap = new TiledMap("res/Level 1.tmx");
			layers = 4;
			break;
		case 1:
			grassMap = new TiledMap("res/Level 1.tmx");
			goodMusic = new Music("res/music/LightHart.wav");
			goodMusic.loop();
			layers = 4;
			break;
		case 2:
			grassMap = new TiledMap("res/Level 2.tmx");
			layers = 4;
			goodMusic = new Music("res/music/LightHart.wav");
			goodMusic.loop();
			break;
		}

		for (int layer = 0;layer < layers;layer++)
		{
			for (int xAxis=0;xAxis<grassMap.getWidth(); xAxis++)
			{
				for (int yAxis=0;yAxis<grassMap.getHeight(); yAxis++)
				{
					int tileID = grassMap.getTileId(xAxis, yAxis,layer);
					String value = grassMap.getTileProperty(tileID, "blocked", "false");
					if (value.equals("true"))
					{
						walls.add(new Wall((xAxis*32)-820,(yAxis*32)-64,"grass"));
					}
				}
			}

		}
		mobs.add(new Sentry(1024,256));
		mobs.add(new Meleebot(1024,128));
	}

	@Override
	public void update(GameContainer container, int arg1) throws SlickException {

		Input input = container.getInput();
		if(title)
		{
			if(input.isMousePressed(0))
			{
				doormove=true;
			}
			if(doormove)
			{
				doorshift+=16;
				if(doorshift>1024)
					title=false;
			}
		}
		else
		{
		pasta = true;
		if (menu==false)
		{
			if (player.direction.equals("down")) player.sprite = new Animation(new Image[]{new Image("res/Video Game Tiles - Pixel by Pixel/playerft1.png")},1,false);
			else if (player.direction.equals("up")) player.sprite = new Animation(new Image[]{new Image("res/Video Game Tiles - Pixel by Pixel/playerbk1.png")},1,false);
			else if (player.direction.equals("left")) player.sprite = new Animation(new Image[]{new Image("res/Video Game Tiles - Pixel by Pixel/Left A1.png")},1,false);
			else if (player.direction.equals("right")) player.sprite = new Animation(new Image[]{new Image("res/Video Game Tiles - Pixel by Pixel/Right A1.png")},1,false);
			player.sprite.update(arg1);
			//updates for list of stuff
			for(int i=0;i<mobs.size();i++)
			{
				mobs.get(i).ai(player,projectiles);
				if (mobs.get(i).hp<=0)
				{
					explosion=new Sound("res/sound/Explosion"+((int)(Math.random()*3))+".wav");
					explosion.play();
					mobs.get(i).dropHP(.4,medkits);
					mobs.remove(i);
					//EXPLOSION!
				}
			}
			for(int i=0;i<projectiles.size();i++)
			{
				projectiles.get(i).update(10,player,mobs,projectiles);
				if (projectiles.size()>1)
				{
					if (Math.sqrt((Math.pow(projectiles.get(i).startingX-projectiles.get(i).x,2)) + (Math.pow(projectiles.get(i).startingY-projectiles.get(i).y,2))) > 2048)
						projectiles.remove(i);
				}
			}
			for(int i=0;i<medkits.size();i++)
			{
				medkits.get(i).update(player,i,medkits);
			}
			if (input.isKeyDown(Input.KEY_A))
			{
				if (player.swinging==false)
				{
					player.direction="left";
					collisionCheck(4,0,"right");
					player.sprite = player.left;
					player.sprite.update(arg1);
				}
			}
			else
				if (input.isKeyDown(Input.KEY_D))
				{	//right
					if(player.swinging==false)
					{
						player.direction="right";
						collisionCheck(-4,0,"left");
						player.sprite = player.right;
						player.sprite.update(arg1);
					}
				}
				else
					if (input.isKeyDown(Input.KEY_W))
					{	//up
						if(player.swinging==false)
						{
							player.direction="up";
							collisionCheck(0,4,"down");
							player.sprite = player.up;
							player.sprite.update(arg1);
						}
					}
					else
						if (input.isKeyDown(Input.KEY_S))
						{	//down
							if(player.swinging==false)
							{
								player.direction="down";
								collisionCheck(0,-4,"up");
								player.sprite = player.down;
								player.sprite.update(arg1);
							}
						}
			if (input.isMousePressed(0))
			{	
				if((menuButton.contains(input.getMouseX(),input.getMouseY())))
				{	//open menu

					menu=true;
				}

				else
				{
					if (player.shoot1 >= 25 )
					{
						player.shoot1 = 0;
						int time1 =100;
						int time2 = 200;
						if (player.getWeapon1().equals("screw driver"))
						{
							screwsfx.play();
							player.stabL = new Animation(player.stabLeftScrew,time1,false);
							player.stabR = new Animation(player.stabRightScrew,time1, false);
							player.stabU = new Animation(player.stabUpScrew,time1,false);
							player.stabD = new Animation(player.stabDownScrew,time1,false);

							for (Mob moby:mobs)
							{
								if (player.meleeRange(moby, 128))
								{
									moby.hurt(10);
								}
							}
						}
						else
							if (player.getWeapon1().equals("saber"))
							{
								sabersfx.play();

								player.stabL = new Animation(player.stabLeftSaber,time2,true);
								player.stabR = new Animation(player.stabRightSaber,time2, true);
								player.stabU = new Animation(player.stabUpSaber,time2,true);
								player.stabD = new Animation(player.stabDownSaber,time2,true);

								for (Mob moby:mobs)
								{
									if (player.meleeRange(moby, 128))
									{
										moby.hurt(20);
									}
								}
							}
						if (player.getWeapon1().equals("energy gun"))
						{

							player.stabL = new Animation(player.shootLeft,time2,true);
							player.stabR = new Animation(player.shootRight,time2, true);
							player.stabU = new Animation(player.shootUp,1000,true);
							player.stabD = new Animation(player.shootDown,1000,true);
							lasersfx.play();
							if (player.direction.equals("left"))
							{
								projectiles.add(new Projectile(player.x, player.y+35, 0, 0,true));
							}
							else
								if (player.direction.equals("right"))
								{
									projectiles.add(new Projectile(player.x+45, player.y+35, 1000, 1,true));
								}
								else
									if (player.direction.equals("up"))
									{
										projectiles.add(new Projectile(player.x+45, player.y+35, 1000, -1000,true));
									}
									else
										if (player.direction.equals("down"))
										{
											projectiles.add(new Projectile(player.x+15, player.y+35, 1000, 1000,true));
										}
						}
						player.swinging = true;
						timey=0;	
					}
				}
			}
			if (input.isKeyPressed(Input.KEY_4))
			{
				player.setWeapon1("screw driver");	
				item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Screwdriver.png");
			}
			if (input.isKeyPressed(Input.KEY_5))
			{
				player.setWeapon1("saber");
				item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Energy Saber.png");
			}
			if (input.isKeyPressed(Input.KEY_6))
			{
				player.setWeapon1("energy gun");
				item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Laser Gun.png");
			}
			if (input.isKeyPressed(Input.KEY_1))
			{
				player.setWeapon2("screw driver");
				item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Screwdriver.png");
			}
			if (input.isKeyPressed(Input.KEY_2))
			{
				player.setWeapon2("saber");
				item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Energy Saber.png");
			}
			if (input.isKeyPressed(Input.KEY_3))
			{
				player.setWeapon2("energy gun");
				item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Laser Gun.png");
			}
			if (input.isMousePressed(1))
			{
				if (player.shoot2 >= 25 )
				{
					player.shoot2 = 0;
					int time1 =200;
					int time2 = 300;
					if (player.getWeapon2().equals("screw driver"))
					{
						screwsfx.play();

						player.stabL = new Animation(player.stabLeftScrew,time1,false);
						player.stabR = new Animation(player.stabRightScrew,time1, false);
						player.stabU = new Animation(player.stabUpScrew,time1,false);
						player.stabD = new Animation(player.stabDownScrew,time1,false);

						for (Mob moby:mobs)
						{
							if (player.meleeRange(moby, 128))
							{
								moby.hurt(10);
							}
						}
					}
					else
						if (player.getWeapon2().equals("saber"))
						{
							sabersfx.play();

							player.stabL = new Animation(player.stabLeftSaber,time2,true);
							player.stabR = new Animation(player.stabRightSaber,time2, true);
							player.stabU = new Animation(player.stabUpSaber,time2,true);
							player.stabD = new Animation(player.stabDownSaber,time2,true);

							for (Mob moby:mobs)
							{
								if (player.meleeRange(moby, 128))
								{
									moby.hurt(20);
								}
							}
						}
					if (player.getWeapon2().equals("energy gun"))
					{

						player.stabL = new Animation(player.shootLeft,time2,true);
						player.stabR = new Animation(player.shootRight,time2, true);
						player.stabU = new Animation(player.shootUp,5000,false);
						player.stabD = new Animation(player.shootDown,5000,false);
						lasersfx.play();
						if (player.direction.equals("left"))
						{
							projectiles.add(new Projectile(player.x, player.y+35, 0, 0,true));
						}
						else
							if (player.direction.equals("right"))
							{
								projectiles.add(new Projectile(player.x+45, player.y+35, 1000, 1,true));
							}
							else
								if (player.direction.equals("up"))
								{
									projectiles.add(new Projectile(player.x+45, player.y+35, 1000, -1000,true));
								}
								else
									if (player.direction.equals("down"))
									{
										projectiles.add(new Projectile(player.x+15, player.y+35, 1000, 1000,true));
									}
					}
					player.swinging = true;
					timey=0;

				}
			}
			if (player.shoot1 < 25)
			{
				player.shoot1+=1;
			}
			if (player.shoot2 < 25)
			{
				player.shoot2+=1;
			}
			if (player.swinging==true)
			{	
				player.Swing(player.direction);
				player.sprite.update(arg1);
			}
			if (timey>=15)
			{
				player.swinging=false;
			}
			else
			{
				timey+=1;
			}
		}
		else
		{
			if (input.isMousePressed(0))
			{
				if((resumeButton.contains(input.getMouseX(),input.getMouseY())))
				{
					menu = false;
				}
				if((restartButton.contains(input.getMouseX(),input.getMouseY())))
				{
					//TODO RESTART
				}
				if((quitButton.contains(input.getMouseX(),input.getMouseY())))
				{
					System.out.println("OMG FUCKER");
					//level=1;
				}
				if ((item1Button.contains(input.getMouseX(),input.getMouseY())))
				{
					player.setWeapon1("screw driver");	
					item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Screwdriver.png");
				}
				if (item2Button.contains(input.getMouseX(),input.getMouseY()))
				{
					player.setWeapon1("saber");
					item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Energy Saber.png");
				}
				if ((item3Button.contains(input.getMouseX(),input.getMouseY())))
				{
					player.setWeapon1("energy gun");
					item1 = new Image("res/Video Game Tiles - Pixel by Pixel/Laser Gun.png");
				}
			}
			else
				if (input.isMousePressed(1))
				{
					if ((item1Button.contains(input.getMouseX(),input.getMouseY())))
					{
						player.setWeapon2("screw driver");	
						item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Screwdriver.png");
					}
					if (item2Button.contains(input.getMouseX(),input.getMouseY()))
					{
						player.setWeapon2("saber");
						item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Energy Saber.png");
					}
					if ((item3Button.contains(input.getMouseX(),input.getMouseY())))
					{
						player.setWeapon2("energy gun");
						item2 = new Image("res/Video Game Tiles - Pixel by Pixel/Laser Gun.png");
					}
				}
		}
	}}

	public void collisionCheck(int x1, int y1,String direction) throws SlickException
	{

		for (Wall wally: walls)
		{
			wally.collision(player);
		}
		ArrayList<Boolean> cantMove = new ArrayList<Boolean>();
		for (Wall wally: walls)
		{
			if ( direction.equals("right"))
			{
				cantMove.add(wally.cannotMoveRight==false);
			}
			else if (direction.equals("left"))
			{
				cantMove.add(wally.cannotMoveLeft==false);
			}
			else if (direction.equals("up"))
			{
				cantMove.add(wally.cannotMoveUp==false);
			}
			else if (direction.equals("down"))
			{
				cantMove.add(wally.cannotMoveDown==false);
			}


		}
		for(boolean x : cantMove)
		{
			if (x==false)
			{
				pasta = false;
			}
		}
		if(pasta==true) 
		{
			//TODO Keep adding the things on the screen as we make more stuff
			x+=x1;
			y+=y1;
			for (Wall wally:walls)
			{
				wally.x+=x1;
				wally.y+=y1;	
			}
			for(Mob moby:mobs)
			{
				moby.x+=x1;
				moby.y+=y1;
			}
			for (Projectile projecty: projectiles)
			{
				projecty.x+=x1;
				projecty.y+=y1;
			}
			for (Medkit meds:medkits)
			{
				meds.x+=x1;
				meds.y+=y1;
			}
			if (steptimer>=17)
			{
				steptimer=0;
				if(stepSwitch)
				{
					step1.play();
					stepSwitch = false;
				}
				else
				{
					step2.play();
					stepSwitch=true;
				}
			}
			else
				steptimer++;
		}

	}
	public static void main(String[] args){
		int maxFPS = 60;
		String str=JOptionPane.showInputDialog("Type number of level(1-2):");
		level = Integer.parseInt(str);
		AppGameContainer app;
		try {
			app = new AppGameContainer(new test("Game"));
			app.setDisplayMode(1024,640,false);
			app.setTargetFrameRate(maxFPS);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}