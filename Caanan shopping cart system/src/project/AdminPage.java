package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField nameField;
	private JTextField brandField;
	private JTextField priceField;
	private JTextField quantityField;
	private JTextField idField;
	private JTextField categoryField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage frame = new AdminPage();
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
	public AdminPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 491, 488);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 458, 257);
		contentPane.add(scrollPane);
		
		table = new JTable();
		fillJtable(table);
		scrollPane.setViewportView(table);
		
		idField = new JTextField();
		idField.setBounds(10, 368, 40, 20);
		contentPane.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(60, 368, 136, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		brandField = new JTextField();
		brandField.setBounds(206, 368, 86, 20);
		contentPane.add(brandField);
		brandField.setColumns(10);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(379, 368, 35, 20);
		contentPane.add(priceField);
		
		quantityField = new JTextField();
		quantityField.setBounds(424, 368, 40, 20);
		contentPane.add(quantityField);
		quantityField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=Integer.parseInt(idField.getText());
				String brand= brandField.getText();
				String name= nameField.getText();
				float price= Float.parseFloat(priceField.getText());
				int quantity=Integer.parseInt(quantityField.getText());
				int category=Integer.parseInt(categoryField.getText());
				try {
					add(id, name, brand, price, quantity, category);
					idField.setText(null);
					nameField.setText(null);
					brandField.setText(null);
					priceField.setText(null);
					quantityField.setText(null);
					categoryField.setText(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBackground(new Color(204, 102, 102));
		btnAdd.setBounds(57, 415, 74, 23);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(204, 102, 102));
		btnUpdate.setBounds(188, 415, 86, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(204, 102, 102));
		btnDelete.setBounds(331, 415, 86, 23);
		contentPane.add(btnDelete);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(204, 102, 102));
		panel.setBounds(0, 0, 478, 67);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Caanan");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminSignIn as= new AdminSignIn();
				as.setVisible(true);
				dispose();
			}
		});
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Myriad Pro", Font.BOLD, 40));
		label.setBackground(Color.WHITE);
		label.setBounds(10, 20, 143, 33);
		panel.add(label);
		
		
		
		JButton button = new JButton("Search");
		button.setBackground(Color.WHITE);
		button.setBounds(389, 32, 79, 23);
		panel.add(button);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblName.setBounds(66, 343, 40, 14);
		contentPane.add(lblName);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBrand.setBounds(206, 343, 40, 14);
		contentPane.add(lblBrand);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrice.setBounds(379, 343, 35, 14);
		contentPane.add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuantity.setBounds(412, 343, 56, 14);
		contentPane.add(lblQuantity);
		
		JLabel lblItemid = new JLabel("Item ID");
		lblItemid.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblItemid.setBounds(10, 343, 46, 14);
		contentPane.add(lblItemid);
		
		idField = new JTextField();
		idField.setBounds(10, 368, 40, 20);
		contentPane.add(idField);
		idField.setColumns(10);
		
		categoryField = new JTextField();
		categoryField.setBounds(302, 368, 67, 20);
		contentPane.add(categoryField);
		categoryField.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCategory.setBounds(302, 343, 67, 14);
		contentPane.add(lblCategory);
	}
	
	public void fillJtable(JTable name){
		DefaultTableModel model= new DefaultTableModel();
		try{
			Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
			
			String query="select * from item;" ;
			PreparedStatement pst= conn.prepareStatement(query); 
			ResultSet rs= pst.executeQuery();
			
			
			
			model.setColumnIdentifiers(new Object[] {"Name","Brand","Price","Quantity","Category"});
			Object[] row= new Object[5];
			
			while(rs.next()){
				
				row[0] = rs.getString(2);
				row[1] = rs.getString(3);
				row[2] = rs.getFloat(4);
				row[3] = rs.getInt(5);
				row[4] = rs.getInt(6);
								
				model.addRow(row);
				
			}
			name.setModel(model);
		}
		catch(SQLException e1){
			e1.printStackTrace();
		}
		
	}
	
	public void add(int ID,String name, String brand,float price,int quantity,int category) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="insert into item values ("+ID+"'"+ name + "','" + brand + "," + price + "," + quantity + "," + category + ");";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}

}
