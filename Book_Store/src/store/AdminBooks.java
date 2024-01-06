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




public class AdminBooks extends JFrame {
	
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
	
	JButton jb_logout = new JButton("Logout");
	JButton jb_users = new JButton("Users");
	JButton jb_books = new JButton("Books");
	
	JLabel jl_bookId = new JLabel("Book ID");
	JTextField jtf_bookId= new JTextField(15);
	JLabel jl_bookTitle = new JLabel("Book Title");
	JTextField jtf_bookTitle = new JTextField(30);
	JLabel jl_author = new JLabel("Author");
	JTextField jtf_author = new JTextField(15);
	JLabel jl_price = new JLabel("Price");
	JTextField jtf_price = new JTextField(15);
	JLabel jl_quantity = new JLabel("Quantity");
	JTextField jtf_quantity= new JTextField(15);
	
	
	JButton jb_addBook = new JButton("Add");
	JButton jb_editBook = new JButton("Edit");
	JButton jb_deleteBook = new JButton("Delete");
	JButton jb_clear = new JButton("Clear");
	JLabel jb_searchBook = new JLabel("Search");
	JTextField jtf_search = new JTextField(15);
	
	JTable bookTable = new JTable();
	JLabel jl_t0 = new JLabel("BookId");
	JLabel jl_t1 = new JLabel("Boot Title");
	JLabel jl_t2 = new JLabel("Author");
	JLabel jl_t3 = new JLabel("Price");
	JLabel jl_t4 = new JLabel("Quantity");
	
	JScrollPane scroll_bookTable = new JScrollPane (bookTable);
	JScrollPane scroll_jp4 = new JScrollPane(jp4);
	
	/*(public static void main(String args[]) {
		AdminBooks adBook = new AdminBooks();
	}*/
	
	private void displayBooks() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
			Statement stm = con.createStatement();
			
			String sql = "select * from books";
			ResultSet rs = stm.executeQuery(sql);
			bookTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(Exception a) {
			a.printStackTrace();
		}
		
	}
	
	public AdminBooks() {
		initialize();
		displayBooks();
	}
	private void initialize() {
		
		bookTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Book ID", "Book Name", "Author", "Price", "Quantity"
				}
			));
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				

				DefaultTableModel model = (DefaultTableModel)bookTable.getModel();
				int index = bookTable.getSelectedRow();
				
				jtf_bookId.setText(model.getValueAt(index, 0).toString());
				jtf_bookTitle.setText(model.getValueAt(index, 1).toString());
				jtf_author.setText(model.getValueAt(index, 2).toString());
				jtf_price.setText(model.getValueAt(index, 3).toString());
				jtf_quantity.setText(model.getValueAt(index, 4).toString());
				jl_errorMsg.setText("");
				
			}
		});
		displayBooks();
		setTitle("Digital Book Store");
		
		jb_books.setEnabled(false);
		jl_pageTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		jb_users.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
					dispose();
					AdminUsers adUs = new AdminUsers();
			}
		});
		jb_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
					dispose();
					Login log = new Login();
			}
		});
		
		jb_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				jtf_bookId.setText("");
				jtf_bookTitle.setText("");
				jtf_author.setText("");
				jtf_price.setText("");
				jtf_quantity.setText("");
				jl_errorMsg.setText("");
			}
		});
		
		jp1.add(jl_pageTitle);
		jp1.add(jb_users);
		jp1.add(jb_books);
		
		jp5.add(jl_bookId);
		jp5.add(jtf_bookId);
		jp5.add(jl_bookTitle);
		jp5.add(jtf_bookTitle);
		jp6.add(jl_author);
		jp6.add(jtf_author);
		jp6.add(jl_price);
		jp6.add(jtf_price);
		jp6.add(jl_quantity);
		jp6.add(jtf_quantity);
		//jp7.setLayout(new GridLayout(1,5));
		
		
		
		
		jtf_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				DefaultTableModel model = (DefaultTableModel)bookTable.getModel();
			if(jtf_search.getText()!=" ") {
			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
			bookTable.setRowSorter(tr);
			tr.setRowFilter(RowFilter.regexFilter(jtf_search.getText()));
			
			}
			else {
				displayBooks();
			}
			}
		});
		
		
		
		jb_addBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (jtf_bookId.getText().isEmpty() || jtf_bookTitle.getText().isEmpty()|| jtf_quantity.getText().isEmpty()|| jtf_price.getText().isEmpty() || jtf_author.getText().isEmpty()) {
				//JOptionPane.showMessageDialog(frameb,"There is an empty field" ,"error", 0);
				jl_errorMsg.setText("There is an empty field. Add failed");
				jl_errorMsg.setForeground(Color.RED);
			}
			else {
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver"); 
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
					
					Statement stm = con.createStatement();
					String sql = "select * from books where bookid ="+jtf_bookId.getText();
					ResultSet rs = stm.executeQuery(sql);
					if(rs.next()) {
						jl_errorMsg.setText("Book ID: "+jtf_bookId.getText()+ " Already Exists");
						jl_errorMsg.setForeground(Color.RED);
					}else {
						PreparedStatement adds = con.prepareStatement("insert into books values(?,?,?,?,?)");
						
						adds.setInt(1,Integer.valueOf(jtf_bookId.getText()));
						adds.setString(2,jtf_bookTitle.getText());
						adds.setString(3,jtf_author.getText());
						adds.setFloat(4,Float.valueOf(jtf_price.getText()));
						adds.setInt(5,Integer.valueOf(jtf_quantity.getText()));
						
						adds.executeUpdate();
						displayBooks();
						//JOptionPane.showMessageDialog(frameb,"New Book Added" ,"Succes", 0);
						jl_errorMsg.setText("New Book Added");
						jl_errorMsg.setForeground(Color.GREEN);
					}
					
					
					
				}
				catch(Exception a) {
					a.printStackTrace();
				}
			}
			
			}
		});
		jp7.add(jb_addBook);
		
		jb_editBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			
			
				if (jtf_bookId.getText().isEmpty() || jtf_bookTitle.getText().isEmpty()|| jtf_quantity.getText().isEmpty()|| jtf_price.getText().isEmpty() || jtf_author.getText().isEmpty()) {
					//JOptionPane.showMessageDialog(frameb,"There is an empty field" ,"error", 0);
					jl_errorMsg.setText("There is an empty field. Edit failed");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						
						Statement stm = con.createStatement();
						String sql = "select * from books where bookid ="+jtf_bookId.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							String query="update books set bookName='"+jtf_bookTitle.getText()+"',author='"+jtf_author.getText()+"',quantity='"+jtf_quantity.getText()+"',price='"+jtf_price.getText()+"' where bookid="+jtf_bookId.getText();
							Statement upd=con.createStatement();
							upd.executeUpdate(query);
							displayBooks();
							//JOptionPane.showMessageDialog(frameb,"Book Updated" ,"Succes", 0);
							jl_errorMsg.setText("Book Updated");
							jl_errorMsg.setForeground(Color.GREEN);
						}else {
							jl_errorMsg.setText("Book ID: "+jtf_bookId.getText()+ " Does NOT Exists");
							jl_errorMsg.setForeground(Color.RED);
						}
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
					}
			
			
			
			
			}
		});
		jp7.add(jb_editBook);
		
		jb_deleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (jtf_bookId.getText().isEmpty()) {
					//.showMessageDialog(frameb,"Need the book id" ,"error", 0);
					jl_errorMsg.setText("Need the book id");
					jl_errorMsg.setForeground(Color.RED);
				}
				else {
					
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
						
						Statement stm = con.createStatement();
						String sql = "select * from books where bookid ="+jtf_bookId.getText();
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()) {
							String bookid=jtf_bookId.getText();
							String query="delete from books where bookid ="+bookid;
							Statement del=con.createStatement();
							del.executeUpdate(query);
							displayBooks();
							//JOptionPane.showMessageDialog(frameb,"Book Deleted" ,"Succes", 0);
							jl_errorMsg.setText("Book Deleted");
							jl_errorMsg.setForeground(Color.GREEN);
						}else {
							jl_errorMsg.setText("Book ID: "+jtf_bookId.getText()+ " Does NOT Exist");
							jl_errorMsg.setForeground(Color.RED);
						}
						
						
						
					}
					catch(Exception a) {
						a.printStackTrace();
					}
					}
			 
			
			}
		});
		jp7.add(jb_deleteBook);
		jp7.add(jb_clear);
		jp7.add(jb_searchBook);
		jp7.add(jtf_search);
		
		jp9.add(jl_errorMsg);
		jp10.add(jb_logout);
		/*
		jp11.setLayout(new GridLayout(1,5));
		jp11.add(jl_t0);
		jp11.add(jl_t1);
		jp11.add(jl_t2);
		jp11.add(jl_t3);
		jp11.add(jl_t4);
		*/
		jp3.setLayout(new GridLayout(4,1));
		jp3.add(jp5);
		jp3.add(jp6);
		jp3.add(jp7);
		jp3.add(jp11);
		
		//jp4.setLayout(new GridLayout(2,1));
		//jp4.add(jp11);
		//jp4.setPreferredSize(new Dimension(800, 100));
		jp4.add(scroll_bookTable);
		
		jp2.setPreferredSize(new Dimension(790, 400));
		jp2.setLayout(new GridLayout(2,1));
		jp2.add(jp3);
		//jp2.add(jp4);
		jp2.add(scroll_jp4);
		
		jpNorth.setLayout(new GridLayout(2,1));
		jpNorth.add(jp1);
		jpCenter.add(jp2);
		jpSouth.setLayout(new GridLayout(2,1));
		jpSouth.add(jp9);
		jpSouth.add(jp10);
		
		add(jpNorth,BorderLayout.NORTH);
		add(jpCenter,BorderLayout.CENTER);
		add(jpSouth,BorderLayout.SOUTH);
		
		setSize(800,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

