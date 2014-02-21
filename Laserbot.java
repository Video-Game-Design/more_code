package rpg;


import java.util.ArrayList;
import java.util.TimerTask;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Laserbot extends Mob {
	public Laserbot(float myx, float myy) throws SlickException
	{
		hp = 5;
		money = 5;
		image = new Image("res/Land Enemy 1C.png");
		seePlayer = false;
		moveX = 64*3;
		moveY = 64*4;
		x=myx;
		y=myy;
		
		directionrnd = (int)(Math.random()*3);
		moveagain=true;
	}

	public void ai(Player player, ArrayList<Projectile> projectiles)
	{	
		
		
		if (moveagain)
		{
		if (directionrnd == 0 && !cannotMoveRight && x<startingX+moveX) 
		{
			x = x+2;
			//constantly make a new timer fuuuu
			timer.schedule(new TimerTask(){
				public void run()
				{
					moveagain=false;
					directionrnd=(int)(Math.random()*3);
					//System.out.println("stop moving");
					subtimer.schedule(new TimerTask(){
						public void run()
						{
							moveagain=true;
							//System.out.println("start moving");
						}
					},(((int)(Math.random()*2000))+2000)); //walk time
				}
			},4000); //wait time
		}
		else
		{ 
			if (directionrnd==1 && !cannotMoveUp && y>startingY-moveY)
			{
				y = y-2;
				timer.schedule(new TimerTask(){
					public void run()
					{
						moveagain=false;
						directionrnd=(int)(Math.random()*3);
						//System.out.println("stop moving");
						subtimer.schedule(new TimerTask(){
							public void run()
							{
								moveagain=true;
								//System.out.println("start moving");
							}
						},(((int)(Math.random()*2000))+2000)); //walk time
					}
				},4000); //wait time
			}
			else
			{
				if(directionrnd==2 && !cannotMoveLeft && x>startingX-moveX)
				{
					x=x-2;
					timer.schedule(new TimerTask(){
						public void run()
						{
							moveagain=false;
							directionrnd=(int)(Math.random()*3);
							//System.out.println("stop moving");
							subtimer.schedule(new TimerTask(){
								public void run()
								{
									moveagain=true;
									//System.out.println("start moving");
								}
							},(((int)(Math.random()*2000))+2000)); //walk time
						}
					},4000); //wait time
				}
				else
				{
					if(directionrnd==3 && !cannotMoveDown && y<startingY+moveY)
					{
						y=y+2;
						timer.schedule(new TimerTask(){
							public void run()
							{
								moveagain=false;
								directionrnd=(int)(Math.random()*3);
								//System.out.println("stop moving");
								subtimer.schedule(new TimerTask(){
									public void run()
									{
										moveagain=true;
										//System.out.println("start moving");
									}
								},(((int)(Math.random()*2000))+2000)); //walk time
							}
						},4000); //wait time
					}
				}
			}
		}
	}
	}		
	
}
