import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About_Game extends JFrame implements ActionListener {
	JPanel about_game;
	JLabel labout;
	JButton bback_from_about;
	JFrame game_about_frame;
	public About_Game() {
		game_about_frame = new JFrame("ABOUT MAZE");
		game_about_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		about_game = new JPanel();
		
		labout = new JLabel("<html><h1>What the Maze is?</h1> "
				+ "<br><h2>A maze is a type of puzzle games where a player moves "
				+ "<br>in complex and branched passages to find a particular "
				+ "<br>target or location. One method to create a maze is the "
				+ "<br>DFS. To reach the target loaction we use DFS ans BFS "
				+ "<br>algorithm. This research explored the possible ways to"
				+ "<br>navigate a maze.</h2>"
				+ "<br><br>Created by,"
				+ "<br>Sayali Chaudhari"
				+ "<br>Gargi Joshi"
				+ "<br>Durga Pandharkar</html>");
		
		
		labout.setBounds(150,30,400,270);
		Font flabout= new Font("Constantia",Font.BOLD,16);
		labout.setFont(flabout);
		labout.setForeground(Color.white);
		
		about_game.setBounds(30,30,650,350);    
		about_game.setBackground(Color.gray);  
		//Font ftpabout_game= new Font("Constantia",Font.BOLD,15);
		//about_game.setFont(ftpabout_game);
		
		bback_from_about = new JButton("Back");
		bback_from_about.setBounds(270,390,200,30);
		Font ftbbreg_back = new Font("Constantia",Font.PLAIN,18);
		bback_from_about.setFont(ftbbreg_back);
		bback_from_about.addActionListener(this);
		
		game_about_frame.add(about_game);
		about_game.add(labout);
		game_about_frame.add(bback_from_about);
		
		
		
		game_about_frame.setSize(700, 500);
		game_about_frame.setLocationRelativeTo(null);
		game_about_frame.setLayout(null);
		game_about_frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bback_from_about) {
			user_player up = new user_player();
			game_about_frame.setVisible(false);
			up.main_frame.setVisible(true);
		}
	}
	public static void main(String args[]) {
		new About_Game();
	}
}
