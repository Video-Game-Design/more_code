package rpg;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class Particle extends Entity {
	Entity parent;
	Animation animation;
	int lifetime;
	Image lastImage;
	public Particle(float myx, float myy, Image[] myanimation, int mytime) 
	{
		x = myx;
		y = myy;
		lifetime = mytime;
		lastImage = myanimation[myanimation.length-1];
		animation = new Animation(myanimation,lifetime,false);
	}
	public void update()
	{
		if (animation.getImage(animation.getFrame()) == lastImage)
		{
			animation.stop();
		}
		if (parent != null)
		{
			x = parent.x;
			y = parent.y;
		}
	}

}
