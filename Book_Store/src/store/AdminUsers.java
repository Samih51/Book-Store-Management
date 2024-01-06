package store;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminUsers extends JFrame  {
	
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();
	JPanel jp5 = new JPanel();
	JPanel jp6 = new JPanel();
	JPanel jp7 = new JPanel();
	JPanel jp8 = new JPanel();
	JPanel jp9 = new JPanel();
	JPanel jp10 = new JPanel();
	JPanel jp11 = new JPanel();

	JPanel jpNorth = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel jpSouth = new JPanel();
	
	JLabel jl_pageTitle = new JLabel("Manage Store");
	JLabel jl_errorMsg = new JLabel("");
	JLabel jl_id = new JLabel("ID");
	JTextField jtf_id = new JTextField(15);
	
	JLabel jl_firstName = new JLabel("First Name");
	JTextField jtf_firstName = new JTextField(15);
	JLabel jl_lastName = new JLabel("Last Name");
	JTextField jtf_lastName = new JTextField(15);
	JLabel jl_username = new JLabel("Username");
	JTextField jtf_username = new JTextField(15);
	JLabel jl_password = new JLabel("Password");
	JPasswordField jpf_password = new JPasswordField(15);
	JLabel jl_phoneNumber = new JLabel("Phone Number");
	JTextField jtf_phoneNumber = new JTextField(15);
	JLabel jb_searchBook = new JLabel("Search");
	JTextField jtf_search = new JTextField(15);
	
	JButton jb_logout = new JButton("Logout");
	JButton jb_users = new JButton("Users");
	JButton jb_books = new JButton("Books");
	JButton jb_addUser = new JButton("Add User");
	JButton jb_addAdmin = new JButton("Add as Admin");
	JButton jb_editUser = new JButton("Edit");
	JButton jb_deleteUser = new JButton("Delete");
	JButton jb_clear = new JButton("Clear");
	
	JTable userTable =new JTable();
	JLabel jl_t0 = new JLabel("Id");
	JLabel jl_t1 = new JLabel("Username");
	JLabel jl_t2 = new JLabel("Password");
	JLabel jl_t3 = new JLabel("First Name");
	JLabel jl_t4 = new JLabel("Last Name");
	JLabel jl_t5 = new JLabel("Phone Number");
	
	JScrollPane scroll_userTable = new JScrollPane(userTable);
	JScrollPane scroll_jp4 = new JScrollPane(jp4);
	/*public static void main(String[] args) {
		
		AdminUsers adUser = new AdminUsers();
	}*/
	
	private void displayUsers() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
			Statement stm = con.createStatement();
			
			String sql = "select * from users";
			ResultSet rs = stm.executeQuery(sql);
			userTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(Exception a) {
			a.printStackTrace();
		}
		
	}
	
	public AdminUsers() {
		initialize();
	}
	
	private void initialize() {
		
		userTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Username", "Password", "First Name", "Last Name", "Phone Number"
				}
			));
		displayUsers();
		
			
		setTitle("Digital Book Store");
		
		jb_users.setEnabled(false);
		
		jl_pageTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		jb_books.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
					dispose();
					AdminBooks adBo = new AdminBooks();
			}
		});
		jb_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
					dispose();
					Login log = new Login();
			}
		});
		
		
		
		
		jtf_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
			DefaultTableModel model = (DefaultTableModel)userTable.getModel();
			if(jtf_search.getText()!=" ") {
			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
			userTable.setRowSorter(tr);
			tr.setRowFilter(RowFilter.regexFilter(jtf_search.getText()));
			
			}
			else {
				displayUsers();
			}
			}
		});
		
		jb_addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (jtf_id.getText().isEmpty() || jtf_firstName.getText().isEmpty()|| jtf_lastName.getText().isEmpty()|| jpf_password.getText().isEmpty() || jtf_username.getText().isEmpty() || jtf_phoneNumber.getText().isEmpty()) {
					//JOptionPane.showMessageDialog("There is an empty field" ,"error", 0);
					jl_errorMsg.setText("There is an empty field. Add failed");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						
						Statement stm = con.createStatement();
						String sql = "select * from users where id ="+jtf_id.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							jl_errorMsg.setText("User ID: "+jtf_id.getText()+ " Already Exists");
							jl_errorMsg.setForeground(Color.RED);
						}else {
							PreparedStatement adds = con.prepareStatement("insert into users values(?,?,?,?,?,?)");
							
							adds.setInt(1,Integer.valueOf(jtf_id.getText()));
							adds.setString(2,jtf_username.getText());
							adds.setString(3,jpf_password.getText());
							adds.setString(4,jtf_firstName.getText());
							adds.setString(5,jtf_lastName.getText());
							adds.setInt(6,Integer.valueOf(jtf_phoneNumber.getText()));
							
							adds.executeUpdate();
							displayUsers();
							
							//JOptionPane.showMessageDialog(frameu,"New User Added" ,"Succes", 0);
							jl_errorMsg.setText("New User Added");
							jl_errorMsg.setForeground(Color.GREEN);
						}
						
						
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
				}
			
			
			}
		});
		
		jb_addAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (jtf_id.getText().isEmpty() || jtf_firstName.getText().isEmpty()|| jtf_lastName.getText().isEmpty()|| jpf_password.getText().isEmpty() || jtf_username.getText().isEmpty() || jtf_phoneNumber.getText().isEmpty()) {
					//JOptionPane.showMessageDialog(frameu,"There is an empty field" ,"error", 0);
					jl_errorMsg.setText("There is an empty field. Add failed");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						
						Statement stm = con.createStatement();
						String sql = "select * from users where id ="+jtf_id.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							jl_errorMsg.setText("User ID: "+jtf_id.getText()+ " Already Exists");
							jl_errorMsg.setForeground(Color.RED);
						}else {
							PreparedStatement adds = con.prepareStatement("insert into admin values(?,?,?,?,?,?)");
							
							adds.setInt(1,Integer.valueOf(jtf_id.getText()));
							adds.setString(2,jtf_username.getText());
							adds.setString(3,jpf_password.getText());
							adds.setString(4,jtf_firstName.getText());
							adds.setString(5,jtf_lastName.getText());
							adds.setInt(6,Integer.valueOf(jtf_phoneNumber.getText()));
							
							adds.executeUpdate();
							
							adds = con.prepareStatement("insert into users values(?,?,?,?,?,?)");
							
							adds.setInt(1,Integer.valueOf(jtf_id.getText()));
							adds.setString(2,jtf_username.getText());
							adds.setString(3,jpf_password.getText());
							adds.setString(4,jtf_firstName.getText());
							adds.setString(5,jtf_lastName.getText());
							adds.setInt(6,Integer.valueOf(jtf_phoneNumber.getText()));
							
							adds.executeUpdate();
							displayUsers();
							//JOptionPane.showMessageDialog(frameu,"New Admin Added" ,"Succes", 0);
							jl_errorMsg.setText("New Admin Added");
							jl_errorMsg.setForeground(Color.GREEN);
						}
						
						
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
				}
			
			}
		});
		
		jb_editUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (jtf_id.getText().isEmpty() || jtf_firstName.getText().isEmpty()|| jtf_lastName.getText().isEmpty()|| jpf_password.getText().isEmpty() || jtf_username.getText().isEmpty() || jtf_phoneNumber.getText().isEmpty()) {
					//JOptionPane.showMessageDialog(frameu,"There is an empty field" ,"error", 0);
					jl_errorMsg.setText("There is an empty field. Edit failed");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						

						Statement stm = con.createStatement();
						String sql = "select * from users where id ="+jtf_id.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							String query="update users set username='"+jtf_username.getText()+"',password='"+jpf_password.getText()+"', fname='"+jtf_firstName.getText()+"',lname='"+jtf_lastName.getText()+"' ,phone='"+jtf_phoneNumber.getText()+"' where id="+jtf_id.getText();
							Statement upd=con.createStatement();
							upd.executeUpdate(query);
							displayUsers();
							//JOptionPane.showMessageDialog(frameu,"User Updated" ,"Succes", 0);
							jl_errorMsg.setText("User Updated");
							jl_errorMsg.setForeground(Color.GREEN);
						}else {
							jl_errorMsg.setText("User ID: "+jtf_id.getText()+" Does not Exist");
							jl_errorMsg.setForeground(Color.RED);
						}
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
					}
			

			}
		});
		
		jb_deleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtf_id.getText().isEmpty()) {
					//JOptionPane.showMessageDialog(frameu,"Need the user id" ,"error", 0);
					jl_errorMsg.setText("Need the user id");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						
						Statement stm = con.createStatement();
						String sql = "select * from users where id ="+jtf_id.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							String query="delete from users where id ="+jtf_id.getText();
							Statement del=con.createStatement();
							del.executeUpdate(query);
							displayUsers();
							//JOptionPane.showMessageDialog(frameu,"User Deleted" ,"Succes", 0);
							jl_errorMsg.setText("User ID: "+jtf_id.getText()+" Deleted");
							jl_errorMsg.setForeground(Color.GREEN);
						}else {
							jl_errorMsg.setText("User ID: "+jtf_id.getText()+" Does not Exist");
							jl_errorMsg.setForeground(Color.RED);
						}
						
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
					}
			 
			
			}
		});
		
		jb_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtf_id.setText("");
				jtf_username.setText("");
				jpf_password.setText("");
				jtf_firstName.setText("");
				jtf_lastName.setText("");
				jtf_phoneNumber.setText("");
				jl_errorMsg.setText("");
						
			}
		});
		
		userTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				DefaultTableModel model = (DefaultTableModel)userTable.getModel();
				int index = userTable.getSelectedRow();
				jtf_id.setText(model.getValueAt(index, 0).toString());
				jtf_username.setText(model.getValueAt(index, 1).toString());
				jpf_password.setText(model.getValueAt(index, 2).toString());
				jtf_firstName.setText(model.getValueAt(index, 3).toString());
				jtf_lastName.setText(model.getValueAt(index, 4).toString());
				jtf_phoneNumber.setText(model.getValueAt(index, 5).toString());
				jl_errorMsg.setText("");
				
			
			}
		});
		jp1.add(jl_pageTitle);
		jp1.add(jb_users);
		jp1.add(jb_books);
		
		//jp5.setLayout(new GridLayout(1,3));
		jp5.add(jl_id);
		jp5.add(jtf_id);
		jp5.add(jl_firstName);
		jp5.add(jtf_firstName);
		jp5.add(jl_lastName);
		jp5.add(jtf_lastName);
		
		jp6.add(jl_username);
		jp6.add(jtf_username);
		jp6.add(jl_password);
		jp6.add(jpf_password);
		jp6.add(jl_phoneNumber);
		jp6.add(jtf_phoneNumber);
		
		jp7.add(jb_addUser);
		jp7.add(jb_addAdmin);
		jp7.add(jb_editUser);
		jp7.add(jb_deleteUser);
		jp7.add(jb_clear);
		jp7.add(jb_searchBook);
		jp7.add(jtf_search);
		
		jp10.add(jb_logout);
		jp9.add(jl_errorMsg);
		
		/*
		jp11.setLayout(new GridLayout(1,6));
		jp11.add(jl_t0);
		jp11.add(jl_t1);
		jp11.add(jl_t2);
		jp11.add(jl_t3);
		jp11.add(jl_t4);
		jp11.add(jl_t5);
		*/
		
		jp3.setLayout(new GridLayout(4,1));
		jp3.add(jp5);
		jp3.add(jp6);
		jp3.add(jp7);
		jp4.add(jp11);
		
		//jp4.setLayout(new GridLayout(2,1));
		//jp4.add(jp11);
		jp4.add(scroll_userTable);
		
		jp2.setPreferredSize(new Dimension(790, 400));
		jp2.setLayout(new GridLayout(2,1));
		jp2.add(jp3);
		//jp2.add(jp4);
		jp2.add(scroll_jp4);
		jp8.setLayout(new GridLayout(2,1));
		jp8.add(jp9);
		jp8.add(jp10);
		
		jpNorth.add(jp1);
		//jpCenter.setLayout(new GridLayout(2,1));
		jpCenter.add(jp2);
		//jpCenter.add(jp3);
		//jpCenter.add(jp4);
		jpSouth.add(jp8);
		
		add(jpNorth,BorderLayout.NORTH);
		add(jpCenter,BorderLayout.CENTER);
		add(jpSouth,BorderLayout.SOUTH);
		
		setSize(800,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
