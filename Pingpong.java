/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YLpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import javax.swing.JPanel;

class Pingpong extends JPanel implements KeyListener {

	private Ball ball = new Ball();
	public static String message = "";
	private Font mFont = new Font("TimesRoman",Font.BOLD,20);
	private Font sFont = new Font("Arial",Font.BOLD,50);
	private PrintWriter out; 
	int score1;
	int score2;
	String player1 = "";
	String player2 = "";
	int py1;
	int py2;
	int ballx;
	int bally;
	public Pingpong(PrintWriter pw, int s1, int s2) {
		out = pw;
		score1 = s1;
		score2 = s2;
		py1=200;
		py2=200;
		ballx=ball.getX();
		bally=ball.getY();
	}
	public int getPY1(){
		return py1;
	}
	public int getPY2(){
		return py2;
	}
	public void setPY1(int py1) {
		this.py1=py1;
	}
	public void setPY2(int py2) {
		this.py2=py2;
	}

	public Ball getBall() {
		return ball;
	}
 
	
	
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			out.println("UP");	
		}
		if (key == KeyEvent.VK_DOWN) {
			out.println("DOWN");	
		}		
	}

	public void keyReleased(KeyEvent e) {

	}

	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString(message, 100, 475);
		g.setColor(Color.gray);
		g.fillRect(50, 50, 700, 400);
                
	    g.setColor(Color.white);
            g.drawOval(220,80, 350,350);
	    g.drawLine(400,50, 400,450 );
            
                g.setColor(Color.green);
                g.drawString("Player 1 :", 150, 100);
		g.drawString(Integer.toString(score1), 200, 100);
                g.drawString("Player 2 :", 550, 100);
		g.drawString(Integer.toString(score2), 600, 100);
		
		
	   
	   
	    this.getX();
	    this.getY();
	    g.setColor(Color.white);
	    g.fillOval(ballx, bally, 15, 15);
	    this.getPY1();
	    this.getPY2();
	    g.setColor(Color.black);
        g.fillRect(100,py1, 10, 90);
   
		g.fillRect(700, py2, 10, 90);
		this.repaint();
		
		
		
	}

	
	
	public void updatePaddle1(int py1) {
		this.py1=py1;
		this.setPY1(py1);
		this.repaint();
	}
	public void updatePaddle2(int py2) {
		this.py2=py2;
		this.setPY2(py2);
		this.repaint();
	}
	

	public void updateBall(int x,int y) {
	
		ballx=x;
		bally=y;
		this.repaint();
	}
	
	public void moveUp(String mark) {
		if(mark.equals("1")) {
			this.repaint();
			py1=py1-50;
			if(py1<=50)
				py1=50;
			
			out.println("Paddle1 Move: " + py1);
			
		} else if(mark.equals("2")) {
			
			this.repaint();
			py2=py2-50;
			if(py2<=50)
				py2=50;
			
			out.println("Paddle2 Move: " + py2);
		}
	}
	
	public void moveDown(String mark) {
		if(mark.equals("1")) {
			
			this.repaint();
			py1=py1+50;
			if(py1>=370)
				py1=370;
			
			out.println("Paddle1 Move: " + py1);
		} else if(mark.equals("2")) {
			
			this.repaint();
			py2=py2+50;
			if(py2>=370)
				py2=370;
			
			out.println("Paddle2 Move: " + py2);
		}
	}
	
	
	public void updateScore1(int s1) {
		score1 = s1;
	}
	public void updateScore2(int s2) {
		score2 = s2;
	}
	
class Ball {
		
		private int x;
		private int y;
		int m = 3;
		int n = 3;

		public Ball() {
			
			
			this.x = 391;
			this.y = 250;

		}
		

		public String toString() {
			return "(" + x + ". " + y + ")";
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		
		public void ballMove() {
		
			x += m;
			y += n;
				
			if(x <= 100 && y >= getPY1() && y <= getPY1()+80 )
					m = -m;
			
			
			if(x >= 690 && y >= getPY2() && y <= getPY2()+80)
					m = -m;
			
			if ( y >= 430) {
				n = -n;
			}
			
			if ( y <= 50) {
				n = -n;
			}

		}
	}


}
