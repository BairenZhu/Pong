/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YLpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Pingpongclient {
		private JFrame frame = new JFrame("Pingpong Game");

		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		private Timer t;
		private Pingpong a;
		private int score1 = 0;
		private int score2 = 0;
                private String serverIP;
		public Pingpongclient()  {
			try {
                                serverIP = JOptionPane.showInputDialog(null, "ex. 127.0.0.1", "Enter a server IP:", 1);
				socket = new Socket(serverIP, 8080);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				a = new Pingpong(out, score1, score2); 
				frame.add(a);
				frame.addKeyListener(a);
				frame.addMouseListener(new MouseAdapter() {
                                        @Override
					public void mousePressed(MouseEvent e) {
						out.println("GO");
						
					
						t = new Timer(10, new TimerListener(a.getBall()));
						t.start();
						
					}
				});
			} catch (UnknownHostException e1) {
			} catch (IOException e1) {
			}
				
				
			
			
		}

		public static void main(String args[])  {
			while (true) {
				
				Pingpongclient client = new Pingpongclient();
				client.frame.setSize(800, 550);
				client.frame.setLocationRelativeTo(null);
				client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.frame.setVisible(true);
				client.frame.setResizable(false);
				
				try {
					client.play();
					if (!client.wantsToPlayAgain()) {
						break;
					}
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			}
		}

		
		class TimerListener implements ActionListener {
			private Pingpong.Ball b;

			public TimerListener(Pingpong.Ball b) {
				this.b = b;
			}

			public void actionPerformed(ActionEvent e) {
					b.ballMove();
					out.println("Ball Move: " + b.getX() + " " + b.getY());
					if (b.getX() > 750)
						{
						out.println("Player1 get one point");
						t.stop();
					
						}
					if (b.getX() < 50)
						{
						out.println("Player2 get one point");
						t.stop();
						
						}
				
				frame.repaint();
					
			}

		}
		public void play() throws Exception {
			String response;
			try {
				response = in.readLine();
				if (response.startsWith("WELCOME")) {
					char mark = response.charAt(8);
					out.println("PLAYER: " + mark );
					System.out.println(response);
					frame.setTitle("Ping-Pong Game Player " + mark);
					
				}
				
				while (true) {
					response = in.readLine();
					
					if (response.startsWith("UP ")) {
						String player = response.substring(3);
						a.moveUp(player);	
					}else if (response.startsWith("DOWN ")) {
						String player = response.substring(5);
						a.moveDown(player);	
					}else if (response.equals("GO")) {
							Pingpong.message = "";
							frame.repaint();
							
					
					}else if (response.startsWith("Paddle1 Move: ")) {
						String paddle1 = response.substring(14, response.length());
						
						int py1=  Integer.parseInt(paddle1);
						a.updatePaddle1(py1);
					} else if (response.startsWith("Paddle2 Move: ")) {
						String paddle2 = response.substring(14, response.length());
						
						int py2=  Integer.parseInt(paddle2);
						a.updatePaddle2(py2);
						
						
					} else if (response.startsWith("Ball Move: ")) {
						
						String ball = response.substring(11);
						String[] tmp = ball.split(" ");
						int ballx=Integer.parseInt(tmp[0]);
						int bally=Integer.parseInt(tmp[1]);
						
						a.updateBall(ballx,bally);
						
						
					} else if (response.startsWith("MESSAGE")) {
						Pingpong.message = response.substring(8);
						frame.repaint();
					} else if(response.startsWith("Player1: ")) {
						score1 = Integer.parseInt(response.substring(9));
						
						a.updateScore1(score1);
						a.getBall().setX(390);
						a.getBall().setY(210);
						a.updateBall(390, 210);
						Pingpong.message = "Click your mouse to start";
						frame.repaint();
					} else if(response.startsWith("Player2: ")) {
						score2 = Integer.parseInt(response.substring(9));
						
						a.updateScore2(score2);
						a.getBall().setX(390);
						a.getBall().setY(210);
						a.updateBall(390, 210);
						Pingpong.message = "Click your mouse to start";
						frame.repaint();
					} else if (response.equals("You Win!")) {
						Pingpong.message = response;
						frame.repaint();
						break;
					}else if (response.equals("You Lose!")) {
						Pingpong.message = response;
						frame.repaint();
						break;
					}
				}
				out.println("QUIT");

			} finally {
				socket.close();
			}
		}

		
		private boolean wantsToPlayAgain() {
			int response = JOptionPane.showConfirmDialog(frame, "Want to play again?", "",
					JOptionPane.YES_NO_OPTION);
			frame.dispose();
			return response == JOptionPane.YES_OPTION;
		}

	}


class Point {
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {   
		return  "" + y; 
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
}




