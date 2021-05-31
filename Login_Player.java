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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login_Player extends JFrame implements ActionListener {
	
	
	private static final long serialVersionUID = 1L;
	JFrame login_frame = new JFrame("Login");
	JLabel llogin,llogincontact,lloginid;
	JTextField tlogincontact,tloginid;
	JButton blogin, bback;
	
	Login_Player(){
		login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//label for login
		llogin = new JLabel("Lets play game...!!");
		llogin.setBounds(220,10,250,30);
		Font fllogin = new Font("Constantia",Font.PLAIN,25);
		llogin.setFont(fllogin);
		
		//label for name
		llogincontact = new JLabel("Enter Contact number");
		llogincontact.setBounds(100,100,250,30);
		Font flloginname = new Font("Constantia",Font.PLAIN,20);
		llogincontact.setFont(flloginname);
		
		
		//textfield for login contact
		tlogincontact = new JTextField();
		tlogincontact.setBounds(340,100,200,30);
		Font fttname = new Font("Constantia",Font.PLAIN,18);
		tlogincontact.setFont(fttname);
		
		//label for login id
		lloginid = new JLabel("Enter your ID");
		lloginid.setBounds(100,150,250,30);
		Font flloginid = new Font("Constantia",Font.PLAIN,20);
		lloginid.setFont(flloginid);
		
		
		//text field for login name
		tloginid = new JTextField();
		tloginid.setBounds(340,150,200,30);
		Font fttid = new Font("Constantia",Font.PLAIN,18);
		tloginid.setFont(fttid);
		
		//button for deletion
		blogin = new JButton("Login");
		blogin.setBounds(250,250,150,30);
		Font ftbdeletel = new Font("Constantia",Font.PLAIN,18);
		blogin.setFont(ftbdeletel);
		blogin.addActionListener(this);
		
		//button for back
		bback = new JButton("Back");
		bback.setBounds(270,380,200,30);
		Font ftbbreg_back = new Font("Constantia",Font.PLAIN,18);
		bback.setFont(ftbbreg_back);
		bback.addActionListener(this);		
		
		//add content
		login_frame.add(llogin);
		login_frame.add(llogincontact);
		login_frame.add(tlogincontact);
		login_frame.add(lloginid);
		login_frame.add(tloginid);
		login_frame.add(blogin);
		
		login_frame.setSize(700, 500);
		login_frame.setLocationRelativeTo(null);
		login_frame.setLayout(null);
		login_frame.setVisible(true);	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		int id, flag;
		String phoneno;
		user_player up = new user_player();

		if(e.getSource() == blogin) {
			id  = Integer.parseInt( tloginid.getText());
			phoneno = tlogincontact.getText();
			flag = CheckLogin.search(id, phoneno);
			if(flag == 1) {
				JOptionPane.showMessageDialog(null, "You are logged in\n ID: "+id + "\n Contact: "+phoneno);
				login_frame.setVisible(false);
				//MazeFrame.main(new String[] {});
                                new MazeFrame(11,11);
			}
			else {
				JOptionPane.showMessageDialog(null, "ID not found! \nPlease try again with proper Id!");
				login_frame.setVisible(true);
			}
		}
		if(e.getSource() == bback) {
			login_frame.setVisible(false);
			up.main_frame.setVisible(true);
		}
	}

	public static void main(String args[]) {
		new Login_Player();
	}
	
	
}