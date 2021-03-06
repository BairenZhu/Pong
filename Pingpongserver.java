/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YLpong;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Pingpongserver {
		static ServerSocket server=null;
		public static void main(String[] args)  {
			 
			System.out.println("Ping-Pong Server is Running");

			try {
				server = new ServerSocket(8080);
				while (true) {
					Game game = new Game();
					Game.Player player1 = game.new Player(server.accept(), '1', 0);
					Game.Player player2 = game.new Player(server.accept(), '2', 0);
					
					
					player1.setOpponent(player2);
					player2.setOpponent(player1);
					game.currentPlayer = player1;
					
					player1.start();
					player2.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
//Game class 
class Game{
	

		Player currentPlayer;
		
		class Player extends Thread {
		
			char mark;
			int score;
			Player opponent;
			Socket socket;
			BufferedReader input;
			PrintWriter output;

			public Player(Socket socket, char mark, int score) {
				this.socket = socket;
				this.mark = mark;
				this.score = score;
				try {
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream(), true);
					output.println("WELCOME " + mark);
					output.println("MESSAGE Waiting for opponent to connect");
				} catch (IOException e) {
					System.out.println("Player died: " + e);
				}
			}

			public void setOpponent(Player opponent) {
				this.opponent = opponent;
			}

			public void run() {

				try {
					output.println("MESSAGE All players connected");	
					output.println("MESSAGE Click Mouse to Start");
					

					
					while (true) {
						String command = input.readLine();
						
						if (command.equals("UP")) {
							output.println("UP " + this.mark);
							
						}
						if (command.equals("DOWN")) {
							output.println("DOWN " + this.mark);
							
						}
						if(command.equals("GO")) {
							updateOppnent(command);
							output.println(command);
							
						}
						if (command.startsWith("Ball Move: ")) {
							output.println(command);
							updateOppnent(command);
						}
						if (command.startsWith("Paddle1 Move: ")) {
							updateOppnent(command);
						}
						if (command.startsWith("Paddle2 Move: ")) {
							updateOppnent(command);
						}
						if (command.startsWith("GAME OVER: ")) {
							output.println(command);
							updateOppnent(command);
						}
						if (command.equals("Player1 get one point")) {
							if(this.mark == '1') {
								this.score++;
								updateOppnent("Player1: " + this.score);
								output.println("Player1: " + this.score);
							} else {
								this.opponent.score++;
								updateOppnent("Player1: " + this.opponent.score);
								output.println("Player1: " + this.opponent.score);
							}
							checkWinner();
						}
						if (command.equals("Player2 get one point")) {

							if(this.mark == '2') {
								this.score++;
								updateOppnent("Player2: " + this.score);
								output.println("Player2: " + this.score);
							} else {
								this.opponent.score++;
								updateOppnent("Player2: " + this.opponent.score);
								output.println("Player2: " + this.opponent.score);
							}
							checkWinner();
						}
						if (command.startsWith("QUIT"))
							break;
					}
				} catch (IOException e) {
					System.out.println("GAME OVER " + e);
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}

			
			public void updateOppnent(String message) {
				currentPlayer = this.opponent;
				currentPlayer.otherOppnent(message);
			}

			public void otherOppnent(String message) {
				output.println(message);
			}
			
			public void checkWinner() {
				if(this.score == 2) {
					updateOppnent("You Lose!");
					output.println("You Win!");
				}else if(this.opponent.score == 2) {
					updateOppnent("You Win!");
					output.println("You Lose!");
				}
			}
		}
}





