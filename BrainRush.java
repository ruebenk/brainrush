import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
/*<applet code="app" height="800" width="800">*/
public class BrainRush extends Applet implements Runnable,KeyListener
{
	public Thread t=null;
	public ball b2;
	public square s2;
	public ball[] b1=new ball[5];
	public square[] s1=new square[5];
	public static int flag=1,i=0,gameover=0,score=0,time=30,gap=120,highscore=0;
	public int x=0,choice;
	Random r = new Random();
	TextField text;
	public void reset()
	{
		Rectangle rect=new Rectangle();
		setSize(700,700);
		setBackground(Color.BLACK);
		addKeyListener(this);
		for(i=0;i<5;i++)
		{
			b1[i]=new ball(350,0);
			s1[i]=new square(350,0);
		}
		b2=new ball(350,600);
		s2=new square(350,600);
		b2.r=25;
		s2.width=30;
		score=0;gap=120;time=30;gameover=0;i=0;flag=1;x=0;
		t=new Thread(this);
		t.start();
	}
	public void init()
	{
		Rectangle rect=new Rectangle();
		setSize(700,700);
		setBackground(Color.BLACK);
		addKeyListener(this);
	}
	public void start()
	{
		for(i=0;i<5;i++)
		{
			b1[i]=new ball(350,0);
			s1[i]=new square(350,0);
		}
		b2=new ball(350,600);
		s2=new square(350,600);
		b2.r=25;
		s2.width=30;
		t=new Thread(this);
		t.start();
	}
	public void run()
	{
		i=0;
		while(true)
		{
			if(x%gap==0)
			{
				choice=r.nextInt(5);
				choice=choice%2;
				if(choice==0)
				{
					System.out.println("Circle "+i);
					b1[i].y=0;
					b1[i].r=25;
				}
				else
				{
					System.out.println("Square "+i);
					s1[i].y=0;
					s1[i].width=30;
				}
				i++;
				i%=5;
				//System.out.println(i);
			}
			x++;
			//System.out.println(i);
			for(int k=0;k<5;k++)
			{
				b1[k].update2(this);
				s1[k].update2(this);
			}
			repaint();
			try
			{
				Thread.sleep(time);
			}
			catch(InterruptedException e)
			{
			}
		}
	}
	public void paint(Graphics g)
	{
		if(gameover==1)
		{
			g.setColor(Color.WHITE);
			g.drawString("GAME-OVER",350,350);
			g.drawString("Controls : ",5,15);
			g.drawString("1. Press 's' to Switch between Circle and Square.",5,30);
			g.drawString("2. Press 'r' to Restart the Game.",5,50);
			g.drawString("Score: "+score,600,20);
			g.drawString("High-Score: "+highscore,600,50);
			t.stop();
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString("Controls : ",5,15);
			g.drawString("1. Press 's' to Switch between Circle and Square.",5,30);
			g.drawString("2. Press 'r' to Restart the Game.",5,50);
			g.drawString("Score: "+score,600,20);
			g.drawString("High-Score: "+highscore,600,50);
			for(int k=0;k<5;k++)
			{
				b1[k].paint1(g);
				s1[k].paint2(g);
			}
			b2.paint1(g);
			s2.paint2(g);
		}
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_S)
		{
			System.out.println("yo");
			s2.update1(this,b2);
			repaint();
	 	}
		else if(e.getKeyCode()==KeyEvent.VK_R)
		{
			System.out.println("yoyo");
			t.stop();
			reset();
	 	}
	}
	public void keyReleased(KeyEvent e)
	{
		b2.update1(this,s2);
		repaint();
	}
}
class ball extends BrainRush
{
	int r=0;
	int x=25;
	int y=25;
	ball()
	{
	}
	ball(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	void update1(BrainRush a,square s2)
	{
		r=0;
		s2.width=30;
		BrainRush.flag=1;
	}
	void update2(BrainRush a)
	{
		y+=3;
		if(BrainRush.flag==0)
		{
			if((y+r)>=600)
			{
				if(r!=0)
				{
					score++;
					if(time>(5+(score/5)))
					{
						time=time-(score/5);
					}
					if(gap>70)
					{
						gap-=5;
					}
				}
				r=0;
			}
		}
		else
		{
			if(((y+r)>=600)&&(r!=0))
			{
				BrainRush.gameover=1;
				if(score>highscore)
					highscore=score;
			}
		}
	}
	void paint1(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x,y,r,r);
	}
}
class square extends BrainRush
{
	int x=25;
	int y=25;
	int width=0;
	square()
	{
	}
	square(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	void update1(BrainRush a,ball b2)
	{
		width=0;
		b2.r=25;
		BrainRush.flag=0;
	}
	void update2(BrainRush a)
	{
		y+=3;
		if(BrainRush.flag==1)
		{
			if((y+30)>=600)
			{
				if(width!=0)
				{
					score++;
					if(time>(5+(score/5)))
					{
						time=time-(score/5);
					}
					if(gap>70)
					{
						gap-=5;
					}
				}
				width=0;
			}
		}
		else
		{
			if(((y+30)>=600)&&(width!=0))
			{
				BrainRush.gameover=1;
				if(score>highscore)
					highscore=score;
			}
		}
	}
	void paint2(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(x,y,width,width);
	}
}