package rpg;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public abstract class Entity {

	public float x;
	public float y;
    public Image image;
    public boolean isSolid;
    public boolean cannotMoveLeft;
    public boolean cannotMoveRight;
    public boolean cannotMoveUp;
    public boolean cannotMoveDown;
    public float startingX;
    public float startingY;
    public String direction;
    public Animation sprite, up, down, left, right, idle;
    public Entity()
    {
    	
    }
    
    public boolean isInside(Entity other)
    {	//Originally a failed collision method; this may or may not be useful.
    	boolean a;
    	a=(other.y+other.image.getHeight()>=y && other.y <= y + image.getHeight());
    	return( a && (other.x+other.image.getWidth()>=x && other.x <= x+image.getWidth()));
    }
    
    public void collision(Entity other)
    {	//other will be the player for player & world collisions
    	
    	if(other.y < y+image.getHeight() && y < other.y+other.image.getHeight())
    	{// if player's top is above wall's bottom && wall's top is above player's bottom	
    		if(other.x==x+image.getWidth()) cannotMoveRight=true;
    		else cannotMoveRight=false;
    		if(other.x+other.image.getWidth() == x) cannotMoveLeft=true;
    		else cannotMoveLeft=false;
    	}
    	else 
    	{
    		cannotMoveRight=false;
    		cannotMoveLeft=false;
    	}
    	if((other.x <= x+image.getWidth()) && (x<other.x+other.image.getWidth()))
    	{	
    		if(other.y==y+image.getHeight()) cannotMoveDown=true;
    		else cannotMoveDown=false;
    		if(other.y+other.image.getHeight()==y) cannotMoveUp=true;
    		else cannotMoveUp=false;
    	}
    	else
    	{
    		cannotMoveUp=false;
    		cannotMoveDown=false;
    	}
    }
    public void collision(Entity other, boolean a)
    {
    	//if (y == other.y) || 
    }
}
