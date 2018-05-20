package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AdminSignIn extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JPasswordField passField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminSignIn frame = new AdminSignIn();
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
	public AdminSignIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(204, 102, 102));
		panel.setBounds(0, 0, 434, 67);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Caanan");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Myriad Pro", Font.BOLD, 45));
		label.setBackground(Color.WHITE);
		label.setBounds(138, 11, 158, 45);
		panel.add(label);
		
		nameField = new JTextField();
		nameField.setBounds(119, 111, 132, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(119, 95, 65, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(119, 146, 65, 14);
		contentPane.add(lblPassword);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String name=nameField.getText().toString();
					String pass=passField.getText().toString();
					checkDetails(name,pass);
					AdminPage ap=new AdminPage();
					ap.setVisible(true);
					dispose();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
				}
			}
		});
		btnSignIn.setBackground(new Color(205, 92, 92));
		btnSignIn.setBounds(119, 192, 89, 23);
		contentPane.add(btnSignIn);
		
		JLabel lblContinueWithoutSigning = new JLabel("Continue without Signing In");
		lblContinueWithoutSigning.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Category c= new Category();
				c.setVisible(true);
				dispose();
			}
		});
		lblContinueWithoutSigning.setBounds(262, 236, 150, 14);
		contentPane.add(lblContinueWithoutSigning);
		
		passField = new JPasswordField();
		passField.setBounds(119, 161, 132, 20);
		contentPane.add(passField);
	}
	public void checkDetails(String name, String pass) throws SQLException{
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/caanan","root", "");
		String query="select * from admin where username='"+name+"' and password='"+pass+"';";
		PreparedStatement pst= conn.prepareStatement(query);
		pst.executeQuery();
	}
}
