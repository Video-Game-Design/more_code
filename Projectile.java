package rpg;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Projectile extends Entity {
		float i;
		float j;
		float x1;
		float y1;
		double magnitude;
	public Projectile(float myx, float myy, float myx1, float myy1) throws SlickException 
	{
		x = myx;
		y = myy;
		startingX=myx;
		startingY=myy;
		x1=myx1;
		y1=myy1;
		i = startingX-x1;
		j = startingY-y1;
		magnitude = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
		image = new Image("res/Bullet.png");
		cannotMoveLeft=false;
		cannotMoveRight=false;
		cannotMoveUp= false;
		cannotMoveDown=false;
	}
	
	public void update(int multiple)
	{
		double angle = Math.atan(j/i);
		if (((angle < (Math.PI/2)) && (angle > (Math.PI/4))) || ((angle > (Math.PI/-2)) && (angle < (Math.PI/-4))))
		{ 
			if(y1>startingY)
			{
				y+=multiple;
			}
			else
			{
				y+=-multiple;
			}
		}
		if ((angle > (Math.PI/-4)) && (angle < (Math.PI/4)))
		{
			if(x1>startingX)
			{
				x+=multiple;
			}
			else
			{
				x+=-multiple;
			}
		}
	}
}
