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
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CartPage extends JFrame {
	DefaultTableModel model= new DefaultTableModel();
	
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartPage frame = new CartPage();
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
	public CartPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 641);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(204, 102, 102));
		panel.setBounds(0, 0, 351, 126);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Caanan");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Myriad Pro", Font.BOLD, 40));
		label.setBackground(Color.WHITE);
		label.setBounds(10, 20, 143, 33);
		panel.add(label);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					delete();
					CartPage cp= new CartPage();
					dispose();
					cp.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCheckOut.setBackground(Color.WHITE);
		btnCheckOut.setBounds(231, 92, 110, 23);
		panel.add(btnCheckOut);
		
		JLabel label_4 = new JLabel("Cart");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 60));
		label_4.setBounds(20, 64, 143, 51);
		panel.add(label_4);
		
		JLabel label_1 = new JLabel("Home");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Category cp= new Category();
				cp.setVisible(true);
				dispose();
			}
		});
		label_1.setForeground(new Color(102, 102, 102));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_1.setBounds(273, 20, 66, 29);
		panel.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 137, 329, 409);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		fillJtable(table);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i= table.getSelectedRow();
				TableModel model= table.getModel();
				String item= (String) model.getValueAt(i, 0);
				int quantity= (int) model.getValueAt(i, 1);
				float price=(float) model.getValueAt(i, 2);
				try {
					remove(item,quantity);
					addRemoved(quantity,item);
					CartPage cp= new CartPage();
					dispose();
					cp.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRemove.setForeground(Color.BLACK);
		btnRemove.setBackground(new Color(204, 102, 102));
		btnRemove.setBounds(10, 557, 81, 23);
		contentPane.add(btnRemove);
		
		JLabel lblYourTotalIs = new JLabel("Your Total is: ");
		lblYourTotalIs.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblYourTotalIs.setBounds(127, 559, 110, 14);
		contentPane.add(lblYourTotalIs);
		
		JLabel total = new JLabel("");
		total.setBounds(243, 561, 96, 14);
		contentPane.add(total);
	}
	public void fillJtable(JTable name){
		DefaultTableModel model= new DefaultTableModel();
		try{
			Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
			
			String query="select * from cart;" ;
			PreparedStatement pst= conn.prepareStatement(query); 
			ResultSet rs= pst.executeQuery();
			
			
			
			model.setColumnIdentifiers(new Object[] {"Name","Quantity","Price"});
			Object[] row= new Object[3];
			
			while(rs.next()){
				
				row[0] = rs.getString(2);
				row[1] = rs.getInt(3);
				row[2] = rs.getFloat(4);
								
				model.addRow(row);
				
			}
			name.setModel(model);
		}
		catch(SQLException e1){
			e1.printStackTrace();
		}
		
	}
	
	public void delete() throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="delete from cart;";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}
	public void remove(String item, int quantity) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="delete from cart where item='"+item+"'and quantity="+quantity+";";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}
	
	public void addRemoved(int quantity,String item) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="update item set quantity=quantity+"+quantity+" where name='"+item+"';";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}
	
	public int sum() throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="select sum(price) from cart";
		PreparedStatement pst= conn.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		int total=rs.getInt(1);
		return total;
	}
}
