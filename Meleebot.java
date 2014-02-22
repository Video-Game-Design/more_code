package rpg;



import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Meleebot extends Mob {

	public Meleebot(float myx, float myy) throws SlickException 
	{
		hp = 70;
		money = 5;
		seePlayer = false;
		dmg = 3;
		direction = "Down";
		x=myx;
		y=myy;
		image = new Image("res/Warrior Down.png");
		Image[] leftA = {new Image("res/Warrior Left Attack1.png"),new Image("res/Warrior Left Attack2.png"),new Image("res/Warrior Left Attack3.png")};
		Image[] rightA = {new Image("res/Warrior Right Attack1.png"),new Image("res/Warrior Right Attack2.png"),new Image("res/Warrior Right Attack3.png")};
		Image[] upA = {new Image("res/Warrior Up Attack1.png"),new Image("res/Warrior Up Attack2.png"),new Image("res/Warrior Up Attack3.png")};
		Image[] downA = {new Image("res/Warrior Down Attack1.png"),new Image("res/Warrior Down Attack2.png"),new Image("res/Warrior Down Attack3.png")};
		Image[] idleA={new Image("res/Warrior Up.png"),new Image("res/Warrior Down.png"),new Image("res/Warrior Left.png"),new Image("res/Warrior Right.png")};
		left = new Animation (leftA,120,true);
		right = new Animation(rightA,120,true);
		up = new Animation(upA,120,true);
		down = new Animation(downA,120,true);
		idle = new Animation(idleA,1000,false);
		directionrnd=1;
		sprite = idle;
		sprite.setCurrentFrame(1);
	}
	public void ai(Player player, ArrayList<Projectile> projectiles)
	{
		System.out.println(hp);
		float i = (player.x-x);
		float j = (player.y-y);
		double angle = Math.atan(j/i);
		//animation code here
		if (canSeePlayer(player,256))
		{
			Circle inner = new Circle(player.x,player.y,48);
			Rectangle bot = new Rectangle(x,y,image.getWidth(),image.getHeight());
			if(!(inner.intersects(bot)||inner.contains(bot.getX(),bot.getY())))
			{
				double magnitude = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
				x = (float) (x + ((i/magnitude)*3));
				y = (float) (y + ((j/magnitude)*3));
				sprite=idle;
				if (((angle < (Math.PI/2)) && (angle > (Math.PI/4))) || ((angle > (Math.PI/-2)) && (angle < (Math.PI/-4))))
				{ 
					if(player.y+32>y)
					{
						directionrnd=1;
						sprite.setCurrentFrame(directionrnd);
						up.restart();
						left.restart();
						right.restart();
					}
					else
					{
						directionrnd=0;
						sprite.setCurrentFrame(directionrnd);
						down.restart();
						left.restart();
						right.restart();
					}
				}
				if ((angle > (Math.PI/-4)) && (angle < (Math.PI/4)))
				{
					if(player.x+32>x)
					{
						directionrnd=3;
						sprite.setCurrentFrame(directionrnd);
						up.restart();
						down.restart();
						left.restart();
					}
					else
					{
						directionrnd=2;
						sprite.setCurrentFrame(directionrnd);
						up.restart();
						down.restart();
						right.restart();
					}
				}
			}
			else
			{//Above=sprites for moveing around, below=sprites for attacking
				if (((angle < (Math.PI/2)) && (angle > (Math.PI/4))) || ((angle > (Math.PI/-2)) && (angle < (Math.PI/-4))))
				{ 
					if(player.y+32>y)
					{
						sprite = down;
						directionrnd=1;
						up.restart();
						left.restart();
						right.restart();
					}
					else
					{
						sprite = up;
						directionrnd=0;
						down.restart();
						left.restart();
						right.restart();
					}
				}
				if ((angle > (Math.PI/-4)) && (angle < (Math.PI/4)))
				{
					if(player.x+32>x)
					{
						sprite =right;
						directionrnd=3;
						up.restart();
						down.restart();
						left.restart();
					}
					else
					{
						sprite = left;
						directionrnd=2;
						up.restart();
						down.restart();
						right.restart();
					}
				}
			}
			//attack
			player.hp--;
		}
		else
		{
			sprite = idle;
			sprite.setCurrentFrame(directionrnd);
			up.restart();
			down.restart();
			right.restart();
			left.restart();
		}
	}

}
