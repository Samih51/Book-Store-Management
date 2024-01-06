package store;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

public class Customer extends JFrame {
	

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
	JPanel jp12 = new JPanel();
	JPanel jp13 = new JPanel();
	JPanel jp14 = new JPanel();
	JPanel jp15 = new JPanel();
	
	JPanel jpNorth = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel jpSouth = new JPanel();
	
	JLabel jl_pageTitle = new JLabel("Buy Books");
	JLabel jl_errorMsg = new JLabel("");
	JButton jb_logout = new JButton("Logout");
	//JButton jb_users = new JButton("Users");
	//JButton jb_books = new JButton("Books");
	
	JLabel jl_bookId = new JLabel("Book ID");
	JTextField jtf_bookId= new JTextField(15);
	JLabel jl_bookTitle = new JLabel("Book Title");
	JTextField jtf_bookTitle = new JTextField(30);
	JLabel jl_author = new JLabel("Author");
	JTextField jtf_author = new JTextField(15);
	JLabel jl_price = new JLabel("Price");
	JTextField jtf_price = new JTextField(15);
	JLabel jl_quantity = new JLabel("Quantity");
	JTextField jtf_quantity= new JTextField(5);
	JLabel jl_search = new JLabel("Search");
	JTextField jtf_search= new JTextField(15);
	
	JButton jb_addToCart = new JButton("Add to Cart");
	JButton jb_clearCart = new JButton("Clear");
	JTextArea jta_cart = new JTextArea(23,45);
	JLabel jl_total = new JLabel("Total:");
	JLabel jtf_total = new JLabel();
	JButton jb_checkout= new JButton("Checkout");
	
	JScrollPane scroll_jtf_cart = new JScrollPane(jta_cart);
	JScrollPane scroll_jp5 = new JScrollPane(jp5);
	
	//JButton jb_searchBook = new JButton("Search");
	//JTextField jtf_searchBook = new JTextField(15);
	
	JTable bookTable = new JTable();
	JLabel jl_t0 = new JLabel("BookId");
	JLabel jl_t1 = new JLabel("Boot Title");
	JLabel jl_t2 = new JLabel("Author");
	JLabel jl_t3 = new JLabel("Price");
	JLabel jl_t4 = new JLabel("Quantity");
	
	JScrollPane scroll_bookTable = new JScrollPane(bookTable);
	
	DefaultTableModel model;
	
	int orderCount=0;
	double totalPrice=0;
	int stock = 0;
	int id;
	
	/*public static void main(String args[]) {
		
		Customer cus = new Customer();
	}*/
	
	public Customer() {
		initialize();
		displayBooks();
	}
	
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
	
	private void sale() {
		
		

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
			
			int newStock= stock-Integer.valueOf(jtf_quantity.getText());
			
			
				String query="update books set quantity='"+newStock+"' where bookid="+id;
				Statement upd=con.createStatement();
				upd.executeUpdate(query);
				displayBooks();
				
			
		}
		catch(Exception a) {
			a.printStackTrace();
		}
		
		
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
				jtf_quantity.setText("1");
				jl_errorMsg.setText("");
				
			}
		});
		
		displayBooks();
		setTitle("Digital Book Store");
		jl_pageTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		jp1.add(jl_pageTitle);
		
		jp6.add(jl_bookTitle);
		jtf_bookTitle.setEnabled(false);;
		jp6.add(jtf_bookTitle);
		//jp6.add(jl_author);
		//jp6.add(jtf_author);
		jp7.add(jl_price);
		jp7.add(jtf_price);
		jtf_price.setEnabled(false);
		jp7.add(jl_quantity);
		jp7.add(jtf_quantity);

		jp12.add(jb_addToCart);
		
		jp7.add(jl_search);
		jp7.add(jtf_search);
		
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
		
		
		jb_addToCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)bookTable.getModel();
				int index = bookTable.getSelectedRow();
				
				if(jtf_bookTitle.getText().isEmpty() ) {
					jl_errorMsg.setText("Please select a book");
					jl_errorMsg.setForeground(Color.RED);
				}else if(jtf_quantity.getText().isEmpty() || Integer.valueOf(jtf_quantity.getText())==0){
					jl_errorMsg.setText("Please Enter Quantity");
					jl_errorMsg.setForeground(Color.RED);
				}else if(Integer.valueOf(jtf_quantity.getText())>Integer.valueOf(model.getValueAt(index, 4).toString())){
					jl_errorMsg.setText("We don't have it in that quantity");
					jl_errorMsg.setForeground(Color.RED);
				}else {
					
					
					if(jta_cart.getText().isEmpty()) {
						System.out.println("THIS");
						jta_cart.append("++++++++++++++++++++++++++++++ Book Store ++++++++++++++++++++++++++++++\n");
						jta_cart.append(" #          Product                    Price          Quantity          Total  \n");
						jta_cart.append("------------------------------------------------------------------------------------\n");
					}
					jta_cart.append(" "+(orderCount+1)+"          ");
					
					stock = Integer.valueOf(model.getValueAt(index, 4).toString());
					id=Integer.valueOf(model.getValueAt(index, 0).toString());
					orderCount++;
					jta_cart.append(model.getValueAt(index, 1).toString());
					for(int i=(model.getValueAt(index, 1).toString().length());i<26;i++) {
						jta_cart.append(" ");
					}
					jta_cart.append(model.getValueAt(index, 3).toString());
					for(int i=(model.getValueAt(index, 3).toString().length());i<20;i++) {
						jta_cart.append(" ");
					}
					jta_cart.append(jtf_quantity.getText().toString());
					for(int i=(model.getValueAt(index, 4).toString().length());i<25;i++) {
						jta_cart.append(" ");
					}
					double total = Double.valueOf(jtf_quantity.getText()) * Double.valueOf(jtf_price.getText());
					jta_cart.append(total+"\n");
					
					totalPrice += total;
					jtf_total.setText(totalPrice+" Br");
					jl_errorMsg.setText("Added to cart");
					jl_errorMsg.setForeground(Color.GREEN);
					sale();
				}
				
				
				
			}
		});
		jb_checkout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					jta_cart.append("------------------------------------------------------------------------------------\n");
					for(int i=Double.toString(totalPrice).length();i<85;i++) {
						jta_cart.append(" ");
					}
					jta_cart.append("Total:  "+Double.toString(totalPrice)+" Br");
					jta_cart.print();
					jtf_bookTitle.setText("");
					jtf_price.setText("");
					jtf_quantity.setText("");
					jta_cart.setText("");
					jtf_total.setText("");
					orderCount=0;
					totalPrice=0;
					jl_errorMsg.setText("");
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		jp12.add(jb_clearCart);
		jb_clearCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jtf_bookTitle.setText("");
				jtf_price.setText("");
				jtf_quantity.setText("");
				jta_cart.setText("");
				jtf_total.setText("");
				orderCount=0;
				totalPrice=0;
				jl_errorMsg.setText("");
				
			}
		});
		jp11.add(jl_search);
		jp11.add(jtf_search);
		
		jp4.setLayout(new GridLayout(4,1));
		jp4.add(jp6);
		jp4.add(jp7);
		jp4.add(jp12);
		jp4.add(jp11);
		
		/*
		jp11.setLayout(new GridLayout(1,5));
		jp11.add(jl_t0);
		jp11.add(jl_t1);
		jp11.add(jl_t2);
		jp11.add(jl_t3);
		jp11.add(jl_t4);
		*/
		//jp5.setLayout(new GridLayout(2,1));
		//jp5.add(jp11);
		//jp5.add(bookTable);
		//jp5.setPreferredSize(new Dimension(500, 400));
		jp5.add(scroll_bookTable);
		
		jp2.setLayout(new GridLayout(2,1));
		jp2.add(jp4);
		//jp2.add(jp5);
		jp2.add(scroll_jp5);
		
		
		jp14.add(jl_total);
		jp14.add(jtf_total);
		jp15.add(jb_checkout);
		
		jp13.setLayout(new GridLayout(2,1));
		jp13.add(jp14);
		jp13.add(jp15);
		
		jp3.add(scroll_jtf_cart);
		jta_cart.setEnabled(false);
		jp3.add(jp13);
		
		
		jp9.add(jl_errorMsg);
		jb_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
					dispose();
					Login log = new Login();
			}
		});
		jp10.add(jb_logout);
		
		jpNorth.add(jp1);
		jpCenter.setLayout(new GridLayout(1,2));
		jpCenter.add(jp2);
		jpCenter.add(jp3);
		jpSouth.add(jp9);
		jpSouth.add(jp10);
		
		add(jpNorth,BorderLayout.NORTH);
		add(jpCenter,BorderLayout.CENTER);
		add(jpSouth,BorderLayout.SOUTH);
		
		setSize(1050,600);
		setVisible(true);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
