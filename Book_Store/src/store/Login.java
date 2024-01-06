package store;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame {
	
	public Login(){
		initialize();
	}
	private void initialize() {
		String typesOfUsers[] = {"Admin","Customer"};
		JPanel jpM = new JPanel();
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JComboBox jcb_userType = new JComboBox(typesOfUsers);
		JLabel jl_username = new JLabel("Username");
		JLabel jl_password = new JLabel("Password");
		JLabel jla_header = new JLabel(" ");
		JTextField jtf_username = new JTextField(10);
		JPasswordField jpf_password = new JPasswordField(10);
		JButton jb_login = new JButton("Login");
		
		jla_header.setHorizontalAlignment(SwingConstants.CENTER);
		jla_header.setForeground(Color.RED);
		
		jp1.add(jl_username);
		jp1.add(jtf_username);
		jp1.add(jl_password);
		jp1.add(jpf_password);
		
		jpM.add(jcb_userType);
		jpM.add(jp1);
		jpM.add(jb_login);
		jpM.add(jp2);
		
		jb_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver"); 
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
					//System.out.println("Connected With the database successfully");

					int userType = jcb_userType.getSelectedIndex();
					String user = jtf_username.getText();
					String pass = jpf_password.getText();
					
					//TESTING
					//System.out.println("userType"+userType);

					Statement stm = con.createStatement();
					String sql;
					
					
					if(userType==0) {
						sql = "select * from admin where username = '"+user+"' and password ='"+pass+"'";
					}else {
						sql = "select * from users where username = '"+user+"' and password ='"+pass+"'";
					}
					ResultSet rs = stm.executeQuery(sql);;
					
					
					if (rs.next()) {
						dispose();
						if(userType==0) {
							AdminUsers adminPage = new AdminUsers();
						}else {
							Customer cust = new Customer();
						}
			
					}
					else {
						//JOptionPane.showMessageDialog(frame,"Incorrect Username or Password","Error Message", 0);
						//username.setText("");
						//password.setText("");
						jla_header.setText("Incorrect username or password");
					}
					con.close();
				}
				 catch (Throwable e) {
					// TODO Auto-generated catch block
				System.out.println("Error while connecting to the database");
					e.printStackTrace();
				}
			}
		});
		
		add(jla_header,BorderLayout.NORTH);
		add(jpM);
		setTitle("Digital Book Store");
		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
