package rpg;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Player extends Entity {
    public int hp;
    public int maxhp;
    public int mp;
    public int maxmp;
    public int str;
    public int shoot1=0;
    public int shoot2 = 0;
    public String weapon1 = "screw driver";
    public String weapon2 = "saber";
    public boolean canAttack = true;
    public int intel;
    public int money;
    public boolean swinging;
    public Image[] leftA = {new Image("res/Left A1.png"), new Image("res/Left A2.png"),new Image("res/Left A3.png"),new Image("res/Left A4.png"),new Image("res/Left B1.png"),new Image("res/Left B2.png"),new Image("res/Left B3.png"),new Image("res/Left B4.png")};
    public Image[] rightA = {new Image("res/Right A1.png"), new Image("res/Right A2.png"),new Image("res/Right A3.png"),new Image("res/Right A4.png"),new Image("res/Right B1.png"),new Image("res/Right B2.png"),new Image("res/Right B3.png"),new Image("res/Right B4.png")};
	public Image[] upA = {new Image("res/playerbk1.png"), new Image("res/playerbk2.png"), new Image("res/playerbk3.png"), new Image("res/playerbk4.png"), new Image("res/playerbk5.png"), new Image("res/playerbk6.png")};
	public Image[] downA = {new Image("res/playerft1.png"), new Image("res/playerft2.png"), new Image("res/playerft3.png"), new Image("res/playerft4.png"), new Image("res/playerft5.png"), new Image("res/playerft6.png")};
    public Image[] idleA = {new Image("res/playerft1.png"),new Image("res/playerbk1.png")};
    public Image[] stabUpScrew = {new Image("res/B1.png"),new Image("res/B2.png")};
    public Image[] stabDownScrew = {new Image("res/F1.png"),new Image("res/F2.png")};
    public Image[] stabLeftScrew = {new Image("res/L1.png"),new Image("res/L2.png")};
    public Image[] stabRightScrew = {new Image("res/R1.png"),new Image("res/R2.png")};
    public Image[] stabUpSaber = {new Image("res/B1 (Energy Saber).png"),new Image("res/B2 (Energy Saber).png"), new Image ("res/B3 (Energy Saber).png")};
    public Image[] stabDownSaber = {new Image("res/F1 (Energy Saber).png"),new Image("res/F2 (Energy Saber).png"), new Image("res/F3 (Energy Saber).png")};
    public Image[] stabLeftSaber = {new Image("res/L1 (Energy Saber).png"),new Image("res/L2 (Energy Saber).png"), new Image ("res/L3 (Energy Saber).png")};
    public Image[] stabRightSaber = {new Image("res/R1 (Energy Saber).png"),new Image("res/R2 (Energy Saber).png"), new Image("res/R3 (Energy Saber).png")};
    public Image[] shootUp = {new Image("res/B1 (Laser Shot).png"),new Image("res/B2 (Laser Shot).png")};
    public Image[] shootDown = {new Image("res/F1 (Laser Shot).png"),new Image("res/F2 (Laser Shot).png")};
    public Image[] shootLeft = {new Image("res/L1 (Laser Shot).png"),new Image("res/L2 (Laser Shot).png")};
    public Image[] shootRight = {new Image("res/R1 (Laser Shot).png"),new Image("res/R2 (Laser Shot).png")};
    
	public String direction;
    public Animation sprite, up, down, left, right, idle,stabL,stabR,stabU,stabD;
    
    public Player() throws SlickException
    {
    	image = new Image("res/playerft1.png");
    	
		
		//int[] time1= {300,300,300,300,300,300};
		int time1=100;
		int time2=300;
		left = new Animation(leftA,time1,false);
		right = new Animation(rightA,time1,false);
		up = new Animation(upA,time1,false);
		down = new Animation(downA,time1,false);
		idle = new Animation(idleA,1,false);
	
			stabL = new Animation(stabLeftScrew,time2,true);
			stabR = new Animation(stabRightScrew,time2, true);
			stabU = new Animation(stabUpScrew,time1,false);
			stabD = new Animation(stabDownScrew,time1,false);
			
		sprite = up;
		direction="down";
    	cannotMoveLeft =false;
    	cannotMoveRight=false;
    	cannotMoveUp=false;
    	cannotMoveDown=false;
    	isSolid = true;
    	x = 444;
    	y = 288;
    	hp = 50;
    	maxhp = 50;
    	mp = 25;
    	maxmp = 25;
    	str = 10;
    	intel = 8;
    	money = 0;
    }
    public boolean meleeRange(Entity object, float range)
	{
		Circle radius = new Circle(x,y,range);
		Rectangle theplayer = new Rectangle(object.x,object.y,object.image.getWidth(),object.image.getHeight());
		if (radius.intersects(theplayer)||radius.contains(theplayer.getX(),theplayer.getY()))
		{
			return true;
		}
		else return false;
	}
    public void setWeapon1(String weapons)
    {
    	this.weapon1  =  weapons;
    }
    public void setWeapon2(String weapons)
    {
    	this.weapon2  =  weapons;
    }
    public String getWeapon1()
    {
    	return this.weapon1;
    }
    public String getWeapon2()
    {
    	return this.weapon2;
    }
    public void Swing(String direction) throws SlickException
    { 
    	if (direction.equals("up"))
    	{
    		sprite=stabU;
    	}
    	else if(direction.equals("left"))
    	{
    		sprite=stabL;
    	}
    	else if(direction.equals("down"))
    	{
    		sprite=stabD;
    	}
    	else if(direction.equals("right"))
    	{
    		sprite=stabR;
    	}
    }
    public String toString()
    {
    	return (hp + "/" + maxhp + "health " + mp + "/" + maxmp + "Mana ");
    }
    
}
