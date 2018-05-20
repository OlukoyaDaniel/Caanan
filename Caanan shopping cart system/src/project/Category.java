package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Category extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTextField quantField;
	
	public ArrayList<Cart> cartList=new ArrayList<Cart>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Category frame = new Category();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Category() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 715);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(204, 102, 102));
		panel.setBounds(0, 0, 369, 67);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Caanan");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Myriad Pro", Font.BOLD, 40));
		label.setBackground(Color.WHITE);
		label.setBounds(10, 20, 143, 33);
		panel.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(238, 11, 121, 20);
		panel.add(textField);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model= new DefaultTableModel();
				SearchPage sp= new SearchPage();
				String x=textField.getText().toString();
				
				
				try {
					Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
					String query="select Name, brand, price, quantity from item where name like '%"+x+"%'or brand like '%"+x+"%';";
					PreparedStatement pst= conn.prepareStatement(query);
					ResultSet rs= pst.executeQuery();
					
					model.setColumnIdentifiers(new Object[] {"Name","Brand","Price","Quantity"});
					Object[] row= new Object[4];
					
					while(rs.next()){
						
						row[0] = rs.getString(1);
						row[1] = rs.getString(2);
						row[2] = rs.getFloat(3);
						row[3] = rs.getInt(4);
						
						model.addRow(row);
						
					}
					sp.setVisible(true);
					sp.table.setModel(model);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button.setBackground(Color.WHITE);
		button.setBounds(280, 32, 79, 23);
		panel.add(button);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 204, 204));
		panel_1.setBounds(0, 66, 369, 41);
		contentPane.add(panel_1);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setToolTipText("");
		comboBox.setBounds(118, 11, 162, 20);
		panel_1.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 118, 349, 483);
		contentPane.add(scrollPane);
		
		table = new JTable(){
			public boolean isCellEditable(int row, int column){
			return false;
			};
		};;
		scrollPane.setViewportView(table);
		insert(comboBox);
		fillJtable(table,comboBox);
		
		JLabel lblSelectACategory = new JLabel("Select a Category");
		lblSelectACategory.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectACategory.setBounds(10, 14, 111, 14);
		panel_1.add(lblSelectACategory);
		
		JButton btnShow = new JButton("show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillJtable(table,comboBox);
			}
		});
		btnShow.setBackground(new Color(255, 255, 255));
		btnShow.setBounds(290, 10, 71, 23);
		panel_1.add(btnShow);
		
		quantField = new JTextField();
		quantField.setBounds(10, 626, 105, 20);
		contentPane.add(quantField);
		quantField.setColumns(10);
		
		JButton btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i= table.getSelectedRow();
				TableModel model= table.getModel();
				String item= (String) model.getValueAt(i, 0);
				String brand= (String) model.getValueAt(i, 1);
				int quantity= (int) model.getValueAt(i, 3);
				int userQuantity=Integer.parseInt(quantField.getText());
				
				float price=(float) model.getValueAt(i, 2);
				float calcPrice= userQuantity * price;
				if(userQuantity>quantity){
					JOptionPane.showMessageDialog(null, "Quantity Larger than Stock");
				}else{
					try {
						Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
						String query="Insert into Cart(item,quantity,price) Values ('"+item+"',"+userQuantity+","+calcPrice+");";
						PreparedStatement pst= conn.prepareStatement(query); 
						pst.executeUpdate();
						int quant=quantity-userQuantity;
						updateQuantity(quant,item,brand);
						quantField.setText(null);
						JOptionPane.showMessageDialog(null, "Added");
						Category c= new Category();
						dispose();
						c.setVisible(true);
					}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			}
		});
		btnAddToCart.setBackground(new Color(204, 102, 102));
		btnAddToCart.setBounds(7, 653, 108, 23);
		contentPane.add(btnAddToCart);
		
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(10, 612, 58, 14);
		contentPane.add(lblQuantity);
		
		JButton btnCheckCart = new JButton("Check Cart");
		btnCheckCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CartPage cp=new CartPage();
				cp.setVisible(true);
				dispose();
			}
		});
		btnCheckCart.setBackground(new Color(205, 92, 92));
		btnCheckCart.setBounds(254, 625, 105, 23);
		contentPane.add(btnCheckCart);
	}
	public void fillJtable(JTable name,JComboBox<String> combo){
		DefaultTableModel model= new DefaultTableModel();
		try{
			Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
			String category=(String) combo.getSelectedItem();
			String query="select Name, brand, price,quantity from item where quantity>0 and category= (select id from category where name='"+category+"');" ;
			PreparedStatement pst= conn.prepareStatement(query); 
			ResultSet rs= pst.executeQuery();
			
			
			
			model.setColumnIdentifiers(new Object[] {"Name","Brand","Price","Quantity"});
			Object[] row= new Object[4];
			
			while(rs.next()){
				
				row[0] = rs.getString(1);
				row[1] = rs.getString(2);
				row[2] = rs.getFloat(3);
				row[3] = rs.getInt(4);
				
				model.addRow(row);
				
			}
			name.setModel(model);
		}
		catch(SQLException e1){
			e1.printStackTrace();
		}
		
	}

	public void insert(JComboBox<String> combo){
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
			String query="select Name from category;" ;
			PreparedStatement pst= conn.prepareStatement(query);
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()){
				String name=rs.getString("name");
				combo.addItem(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateQuantity(int quant,String item,String brand) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="update item set quantity="+quant+" where name='"+item+"' and brand='"+brand+"';";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}

}
