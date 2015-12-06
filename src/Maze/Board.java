package Maze;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1490561065510329027L;
	public static final String wallCharacter = "|";
	public static final String floorCharacter = "_";
	public static final String coinCharacter = "*";
	public boolean end = false;
	private Timer timer;
	public Map m;
	public Player p;
	
	public String Message = "Coins = ";
	
	private Font font = new Font("Times", Font.BOLD, 30);
	
	public Board(PrintWriter out, String playerName) {
		m = new Map();
		p = new Player(playerName);
		addKeyListener(new Al(out));
		setFocusable(true);
		
		timer = new Timer(25, this);
		timer.start();
	}

	
	public void finishGame(String message){
		end = true;
		Message = message;
		repaint();
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		if(!end){
			for(int y = 0; y < Map.mazeSize; y++){
				for(int x = 0; x < Map.mazeSize; x++){
					if(m.getMap(x, y).equals(floorCharacter)){
						g.drawImage(m.getFloor(), x * 20, y * 20, null);
					}
					if(m.getMap(x, y).equals(wallCharacter)){
						g.drawImage(m.getWall(), x * 20, y * 20, null);
					}
					if(m.getMap(x, y).equals(coinCharacter)){
						g.drawImage(m.getCoin(), x * 20, y * 20, null);
					}
				}
			}
			
			g.drawImage(p.getPlayer(), p.getX() * 20, p.getY() * 20, null);
			g.drawImage(p.getOpponent(), p.getOpX() * 20, p.getOpY() * 20, null);
			
			g.setColor(Color.BLACK);
			g.setFont(font); 
			g.drawString("Your coins = " + p.coin, 8, (Map.mazeSize+1)*20 + 8);
			g.drawString("Opponent coins = " + p.op_coin , 520, (Map.mazeSize+1)*20 + 8);
		}
		else{
			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(Message, 10, 400);
		}
	}
	
	public class Al extends KeyAdapter{
		PrintWriter out;
		
		public Al(PrintWriter out) {
			// TODO Auto-generated constructor stub
			this.out = out;
		}
		
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			int offset_x = 0;
			int offset_y = 0;
			
			if(keycode == KeyEvent.VK_UP){
				offset_y = -1;
			}
			if(keycode == KeyEvent.VK_DOWN){
				offset_y = 1;
			}
			if(keycode == KeyEvent.VK_LEFT){
				offset_x = -1;
			}
			if(keycode == KeyEvent.VK_RIGHT){
				offset_x = 1;
			}
			int new_x = p.getX() + offset_x;
			int new_y = p.getY() + offset_y;
			if(!m.isWall(new_x, new_y)){
				if(m.isCoin(new_x, new_y)){
					p.coin++;
				}
				out.println("POS " + p.name + " " + new_x + " " + new_y + " " + p.coin);
				m.setTile(new_x, new_y, Board.floorCharacter);
				p.move(offset_x, offset_y);
			}
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		
		public void keyTyped(KeyEvent e){
			
		}
	}
}
