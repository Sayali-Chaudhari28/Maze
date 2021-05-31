/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class user_player extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JFrame main_frame = new JFrame("Player Registration");
	
	//Labels
	JLabel lb = new JLabel("MAZE GAME");
	//Buttons
	JButton register_player = new JButton("Registration");
	JButton login_player = new JButton("Login");
	JButton delete_player_acc = new JButton("Delete Account");
	JButton about_game = new JButton("About Game");
	user_player(){
		
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//label
		lb.setBounds(300,10,300,30);
		Font flb = new Font("Constantia",Font.BOLD,22);
		lb.setFont(flb);
		
		//Buttons
		register_player.setBounds(250,130,200,30);
		register_player.addActionListener(this);
		
		login_player.setBounds(250,170,200,30);
		login_player.addActionListener(this);
		
		delete_player_acc.setBounds(250,210,200,30);
		delete_player_acc.addActionListener(this);
		
		about_game.setBounds(250,250,200,30);
		about_game.addActionListener(this);
		
		//add components to frame
		main_frame.add(lb);
		main_frame.add(register_player);
		main_frame.add(login_player);
		main_frame.add(delete_player_acc);
		main_frame.add(about_game);
		
		//For Frame
		main_frame.setSize(700, 500);
		main_frame.setLocationRelativeTo(null);
		main_frame.setLayout(null);
		main_frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == register_player) {
			main_frame.setVisible(false);
			Register_Player.main(new String[] {});
		}
		if(e.getSource() == delete_player_acc) {
			main_frame.setVisible(false);
			Delete_Player.main(new String[] {});
		}
		if(e.getSource() == login_player) {
			main_frame.setVisible(false);
			Login_Player.main(new String[] {});
		}
		if(e.getSource() == about_game) {
			main_frame.setVisible(false);
			About_Game.main(new String[] {});
		}
		
	}
        public static void main(String args[]) {
		new user_player();
	}
}
/*public class Main {

	public static void main(String args[]) {
		new user_player();
	}
	

}*/