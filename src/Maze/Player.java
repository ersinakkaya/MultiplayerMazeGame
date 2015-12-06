package Maze;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Player {
	
	private int x, y, op_x, op_y;
	public int coin = 0;
	public int op_coin = 0;
	public String name;
	public String op_name;
	private Image player, opponent;
	public String path = "/Users/ersin/Documents/workspace/JavaMultiplayerMazeGame/src/";
	
	public Player(String name){
		this.name = name;
		if(name.equals("P1")){
			this.op_name = "P2";
			x = 1;
			y = 1;
			op_x = 39;
			op_y = 39;
		}
		
		if(name.equals("P2")){
			this.op_name = "P1";
			x = 39;
			y = 39;
			op_x = 1;
			op_y = 1;
		}
		
		ImageIcon img = new ImageIcon(path + "player.png");
		player = img.getImage();
		
		ImageIcon opp = new ImageIcon(path + "player_2.png");
		opponent = opp.getImage();
	}
	
	public Image getPlayer(){
		return player;
	}
	
	public Image getOpponent(){
		return opponent;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	
	public int getOpX(){
		return op_x;
	}
	
	public int getOpY(){
		return op_y;
	}
	
	public int getOpCoin(){
		return op_coin;
	}
	
	public String getOpName(){
		return op_name;
	}
	
	public void moveOpponent(int dopx, int dopy, int opcoin){
		op_x = dopx;
		op_y = dopy;
		op_coin = opcoin;
	}
}
