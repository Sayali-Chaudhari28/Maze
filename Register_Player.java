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

public class Register_Player extends JFrame implements ActionListener{
	JFrame Register_Player_frame = new JFrame("Registeration");
	JLabel lmain_reg_info, lid, lname, lage, lphone, temp;
	JTextField tid, tname, tage, tphone;
	JButton bregister, breg_back;
	
	Register_Player(){
		Register_Player_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Register_Player_frame.setLayout(null);
		//label for heading
		lmain_reg_info = new JLabel("REGISTRATION");
		lmain_reg_info.setBounds(270,10,250,30);
		Font ftlmain_reg_info = new Font("Constantia",Font.PLAIN,25);
		lmain_reg_info.setFont(ftlmain_reg_info);
		//label for id
		lid = new JLabel("Enter Id of your choice ");
		lid.setBounds(50,70,250,30);
		Font ftlid = new Font("Constantia",Font.PLAIN,18);
		lid.setFont(ftlid);
		
		//text field for id
		tid = new JTextField();
		tid.setBounds(300,65,250,30);
		Font fttid = new Font("Constantia",Font.PLAIN,18);
		tid.setFont(fttid);
		
		//label for name
		lname = new JLabel("Enter your name ");
		lname.setBounds(50,130,250,30);
		Font ftlname = new Font("Constantia",Font.PLAIN,18);
		lname.setFont(ftlname);
		
		//text field for name
		tname = new JTextField();
		tname.setBounds(300,125,250,30);
		Font fttname = new Font("Constantia",Font.PLAIN,18);
		tname.setFont(fttname);
		
		//label for age
		lage = new JLabel("Enter your age ");
		lage.setBounds(50,190,250,30);
		Font ftllage = new Font("Constantia",Font.PLAIN,18);
		lage.setFont(ftllage);
				
		//text field for age
		tage = new JTextField();
		tage.setBounds(300,185,250,30);
		Font ftttage = new Font("Constantia",Font.PLAIN,18);
		tage.setFont(ftttage);
		
		//label for phone no
		lphone = new JLabel("Enter your contact number ");
		lphone.setBounds(50,250,250,30);
		Font ftlphone = new Font("Constantia",Font.PLAIN,18);
		lphone.setFont(ftlphone);
			
		//text field for phone no
		tphone = new JTextField();
		tphone.setBounds(300,245,250,30);
		Font fttphone = new Font("Constantia",Font.PLAIN,18);
		tphone.setFont(fttphone);
		
		//button for register
		bregister = new JButton("Register");
		bregister.setBounds(270,305,250,30);
		Font ftbregister = new Font("Constantia",Font.PLAIN,18);
		bregister.setFont(ftbregister);
		bregister.addActionListener(this);
		
		breg_back = new JButton("Back");
		breg_back.setBounds(270,380,250,30);
		Font ftbbreg_back = new Font("Constantia",Font.PLAIN,18);
		breg_back.setFont(ftbbreg_back);
		breg_back.addActionListener(this);
		
		//add components
		Register_Player_frame.add(lmain_reg_info);
		Register_Player_frame.add(lid);
		Register_Player_frame.add(tid);
		Register_Player_frame.add(lname);
		Register_Player_frame.add(tname);
		Register_Player_frame.add(lage);
		Register_Player_frame.add(tage);
		Register_Player_frame.add(lphone);
		Register_Player_frame.add(tphone);
		Register_Player_frame.add(bregister);
        Register_Player_frame.add(breg_back);
		
		
		Register_Player_frame.setSize(700, 500);
		Register_Player_frame.setLocationRelativeTo(null);
		Register_Player_frame.setLayout(null);
		Register_Player_frame.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int id, age;
		String name, phoneno;
		user_player up = new user_player();
		if(e.getSource() == bregister) {
			CheckLogin chklogin = new CheckLogin();
		     id  = Integer.parseInt( tid.getText());
		     name = tname.getText();
		     phoneno = tphone.getText();
		     age = Integer.parseInt(tage.getText());
		     CheckLogin.insert(id, name, age, phoneno);
		     JOptionPane.showMessageDialog(null, "Your details are \n"+"ID:"+id + " "+"\nNAME: "+name+"\nAGE: "+age+"\nCONTACT: "+phoneno);
		     Register_Player_frame.setVisible(false);
		     up.main_frame.setVisible(true);
		}
		if(e.getSource() == breg_back) {
			Register_Player_frame.setVisible(false);
			up.main_frame.setVisible(true);
		}
	}
	
//----------------------------------------------------------------------------------------------
	
	public static void main(String args[]) {
		new Register_Player();
	}
	
}
	
class Maze_Players{
	int id;
	String name;
	String phone_no;
	int age;
	public Maze_Players(int id, String name, int age, String phone_no) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.phone_no = phone_no;
	}
}
//--------------------------------------CheckLogin----------------------------------------------
class CheckLogin{
	static int size = 5;
	static int currentsize=0;
	static Maze_Players hash_table[] = new Maze_Players[size];
	//Calculate hash key
	static int hash(int key) {
		return (key%size);
	}

	//check hash table is full or not
	static boolean isFull() {
		return currentsize == size;
	}
	//check hash table is empty or not
	static boolean isEmpty() {
		return currentsize == 0;
	}
	
	//Insert into table
	static void insert(int id, String name, int age, String phone_no) {
		id = hash(id);
		Maze_Players cust = new Maze_Players(id, name, age, phone_no);	//calculate hash value
		for(int i=0; i < 5; i++) {
			if(hash_table[i] == null) {
				hash_table[i] = cust;
				currentsize++;
				i=hash(i);
				System.out.println("Data inserted in the Hash table");
				break;
			}
		}
	}
	
	//Search the record in the table
	static int search(int id_, String Contact_) {
		int flag=0;
		if(isEmpty()) {
			System.out.println("Record is Empty!");
			JOptionPane.showMessageDialog(null, "Record is Empty!");
		}
		else {
			id_ = hash(id_);
			for(int i=0; i<size; i++) {
				if(hash_table[i]==null) {
					continue;
				}
				if(hash_table[i].id == id_ && flag == 0 ) {
					flag=1;
				}
			}
		}
		return flag;
	}	
	
	//Delete the record from the table
	static void delete(int id) {
		id = hash(id);
		for(int i=0; i<size; i++) {
			if(hash_table[i] != null && hash_table[i].id == id) {
				hash_table[i] = null;
				currentsize--;
				System.out.println("Customer's Record Deleted!");
				break;
			}
			else {
				continue;
			}
		}
	}
}