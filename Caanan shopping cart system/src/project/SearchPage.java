package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchPage extends JFrame {

	private JPanel contentPane;
	static JTable table;
	private JTextField quantField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPage frame = new SearchPage();
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
	public SearchPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 642);
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
		
		JLabel lblYourSearch = new JLabel("Search Result");
		lblYourSearch.setForeground(Color.WHITE);
		lblYourSearch.setFont(new Font("Tahoma", Font.BOLD, 45));
		lblYourSearch.setBounds(20, 64, 319, 51);
		panel.add(lblYourSearch);
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setBounds(273, 25, 66, 29);
		panel.add(lblHome);
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Category cp= new Category();
				cp.setVisible(true);
				dispose();
			}
		});
		lblHome.setForeground(new Color(102, 102, 102));
		lblHome.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 137, 329, 382);
		contentPane.add(scrollPane);
		
		table = new JTable(){
		public boolean isCellEditable(int row, int column){
			return false;
			};
		};
		scrollPane.setViewportView(table);
		
		JLabel label_1 = new JLabel("Quantity:");
		label_1.setBounds(10, 530, 58, 14);
		contentPane.add(label_1);
		
		quantField = new JTextField();
		quantField.setColumns(10);
		quantField.setBounds(10, 544, 105, 20);
		contentPane.add(quantField);
		
		JButton button = new JButton("Add To Cart");
		button.addActionListener(new ActionListener() {
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
						SearchPage sp= new SearchPage();
						JOptionPane.showMessageDialog(null, "Added");
						sp.setVisible(true);
						dispose();
					}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			}
		});
		button.setBackground(new Color(204, 102, 102));
		button.setBounds(7, 579, 108, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Check Cart");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CartPage cartP=new CartPage();
				cartP.setVisible(true);
				dispose();
			}
		});
		button_1.setBackground(new Color(204, 102, 102));
		button_1.setBounds(231, 543, 108, 23);
		contentPane.add(button_1);
	}
	
	public void updateQuantity(int quant,String item,String brand) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="update item set quantity="+quant+" where name='"+item+"' and brand='"+brand+"';";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeUpdate();
	}
}
