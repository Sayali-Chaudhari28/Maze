import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Delete_Player extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JFrame delte_frame = new JFrame("Delete Account");
    JLabel lb = new JLabel("Delete Acount");
    JLabel ldel = new JLabel("Enter your ID");
    JTextField tdel;
    JButton delbutton, bback_delete;
	
	Delete_Player(){
		delte_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// main lable
		lb.setBounds(260,10,300,30);
		Font flb = new Font("Constantia",Font.BOLD,22);
		lb.setFont(flb);
		
		//lable for ldel
		ldel.setBounds(140,70,250,30);
		 Font ftldel = new Font("Constantia",Font.PLAIN,18);
		ldel.setFont(ftldel);
			
		
		//textfield for del
		tdel = new JTextField();
		tdel.setBounds(300,65,250,30);
		Font fttdel = new Font("Constantia",Font.PLAIN,18);
		tdel.setFont(fttdel);
	
		//button for deletion
		delbutton = new JButton("Delete Account");
		delbutton.setBounds(260,150,180,30);
		Font ftbdelete = new Font("Constantia",Font.PLAIN,18);
		delbutton.setFont(ftbdelete);
		delbutton.addActionListener(this);
		
		//button for back
		bback_delete = new JButton("Back");
		bback_delete.setBounds(260,235,180,30);
		Font ftbbreg_back = new Font("Constantia",Font.PLAIN,18);
		bback_delete.setFont(ftbbreg_back);
		bback_delete.addActionListener(this);
		
		//add content
		delte_frame.add(lb);
		delte_frame.add(ldel);
		delte_frame.add(tdel);
		delte_frame.add(delbutton);
		delte_frame.add(bback_delete);
		
		delte_frame.setSize(700, 500);
		delte_frame.setLocationRelativeTo(null);
		delte_frame.setLayout(null);
		delte_frame.setVisible(true);	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int id, flag;
		user_player up = new user_player();
		if(e.getSource() == delbutton) {
			delte_frame.setVisible(false);
			id  = Integer.parseInt( tdel.getText());
			CheckLogin.delete(id);
				JOptionPane.showMessageDialog(null, "Your Account has been deleted");
				delte_frame.setVisible(false);
				up.main_frame.setVisible(true);
		}
		if(e.getSource() == bback_delete) {
			delte_frame.setVisible(false);
			up.main_frame.setVisible(true);
		}
		
		
	}
	
	public static void main(String args[]) {
		new Delete_Player();
	}
}