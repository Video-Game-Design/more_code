package rpg;



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Meleebot extends Mob {

	public Meleebot(float myx, float myy) throws SlickException 
	{
		hp = 5;
		money = 5;
		seePlayer = false;
		dmg = 3;
		direction = "Down";
		x=myx;
		y=myy;
		Image[] leftA = {new Image("res/Warrior Left Attack1.png"),new Image("res/Warrior Left Attack2.png"),new Image("res/Warrior Left Attack3.png")};
		Image[] rightA = {new Image("res/Warrior Right Attack1.png"),new Image("res/Warrior Right Attack2.png"),new Image("res/Warrior Right Attack3.png")};
		Image[] upA = {new Image("res/Warrior Up Attack1.png"),new Image("res/Warrior Up Attack2.png"),new Image("res/Warrior Up Attack3.png")};
		Image[] downA = {new Image("res/Warrior Down Attack1.png"),new Image("res/Warrior Down Attack2.png"),new Image("res/Warrior Down Attack3.png")};
		Image[] idleA={new Image("res/Warrior Up.png"),new Image("res/Warrior Down.png"),new Image("res/Warrior Left.png"),new Image("res/Warrior Right.png")};
		
	}
	public void ai(Player player)
	{
		if (canSeePlayer(player,256))
		{
			Circle inner = new Circle(player.x,player.y,64);
			Rectangle bot = new Rectangle(x,y,image.getWidth(),image.getHeight());
			if(!(inner.intersects(bot)||inner.contains(bot.getX(),bot.getY())))
			{
				float i = (player.x-x);
				float j = (player.y-y);
				double magnitude = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
				x = (float) (x + ((i/magnitude)*1.5));
				y = (float) (y + ((j/magnitude)*1.5));
			}
			//attack
			player.hp--;
		}
	}

}
