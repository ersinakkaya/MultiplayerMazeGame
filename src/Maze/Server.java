package Maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int PORT = 9047;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Maze Game server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
        	Game game = new Game();
            Game.Player p1 = game.new Player(listener.accept(), "P1");
            Game.Player p2 = game.new Player(listener.accept(), "P2");
            p1.setOpponent(p2);
            p2.setOpponent(p1);
            p1.start();
            p2.start();
            
        } finally {
            listener.close();
        }
    }
}

class Game{
	
   public class Player extends Thread {
    	private Socket socket;
        private String playerName;
        BufferedReader input;
        PrintWriter output;
        Player opponent;
        int x, y, coins;
    	
    	public Player(Socket socket, String playerName) {
            this.socket = socket;
            this.playerName = playerName;
            log("New connection with # " + playerName+ " at " + socket);
            try {
                input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + playerName);
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
        	//Start the game!
            output.println("START");
            
            String response;
            // Get messages from the client
            while (true) {
            	response = input.readLine();
            	if(response.startsWith("POS")){
            		log("Client response:" + response);
            		String data[] = response.split("\\s");
            		int pos_x = Integer.parseInt(data[2]);
                    int pos_y = Integer.parseInt(data[3]);
                    int no_coins = Integer.parseInt(data[4]);
            		if(data[1].equals(playerName)){
            			x = pos_x;
            			y = pos_y;
            			coins = no_coins;
            		}else {
            			opponent.x = pos_x;
            			opponent.y = pos_y;
            			opponent.coins = no_coins;
            		}
                	
            		//Handling player collusions
            		if(x == opponent.x && y == opponent.y){
            			if(coins == opponent.coins){
                    		output.println("END TIE! Both players have same amount of coins.");
                    		opponent.output.println("END TIE! Both players have same amount of coins.");
            			}
            			else if(coins > opponent.coins){
            				output.println("END YOU WON! Your opponent has " + opponent.coins + " coins and you have " + coins + "!");
            				opponent.output.println("END YOU LOST! Your opponent has " + coins + " coins and you have " + opponent.coins + "!");
            			}
            			else {
            				output.println("END YOU LOST! Your opponent has " + opponent.coins + " coins and you have " + coins + "!");
            				opponent.output.println("END YOU WON! Your opponent has " + coins + " coins and you have " + opponent.coins + "!");
            			}
            		}
                    
            		output.println(response);
            		opponent.output.println(response);
            	}
            }
        } catch (IOException e) {
            log("Error handling client# " + playerName + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            log("Connection with client# " + playerName + " closed");
        }
    }
    
        private void log(String message) {
        	System.out.println(message);
        }
   }
}