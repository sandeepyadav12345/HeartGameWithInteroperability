package com.perisic.sixeq.peripherals;

//Code adapted from https://www.tutorialsfield.com/registration-form-in-java-with-database-connectivity
/**
 * Simple register form to register to the system to play the math game
 * 
 * 
 * 
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class RegistrationForm extends JFrame implements ActionListener {
    JFrame frame;
    String[] gender={"Male","Female"};
    JLabel nameLabel=new JLabel("NAME");
    JLabel genderLabel=new JLabel("GENDER");
   
    JLabel passwordLabel=new JLabel("PASSWORD");
    JLabel confirmPasswordLabel=new JLabel("CONFIRM PASSWORD");
    JLabel cityLabel=new JLabel("CITY");
    JLabel usernameLabel=new JLabel("USER NAME");
    JTextField nameTextField=new JTextField();
    JComboBox genderComboBox=new JComboBox(gender);
    
    JPasswordField passwordField=new JPasswordField();
    JPasswordField confirmPasswordField=new JPasswordField();
    JTextField cityTextField=new JTextField();
    JTextField usernameTextField=new JTextField();
    JButton registerButton=new JButton("REGISTER");
    JButton resetButton=new JButton("Already user,Login");

    /**
	 * constructor to create register form, adding components, action events to the system
	 * 
	 * 
	 * 
	 */

    RegistrationForm()
    {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }
    
    /**
	 * method to creating register frame window
	 * 
	 * 
	 * 
	 */
    public void createWindow()
    {
        frame=new JFrame();
        frame.setTitle("Registration Form");
        frame.setBounds(40,40,380,600);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    
    
    /**
	 * method to set the location and size of the components like textbox, label
	 * 
	 * 
	 * 
	 */
    public void setLocationAndSize()
    {
        nameLabel.setBounds(20,20,40,70);
        genderLabel.setBounds(20,70,80,70);
       
        passwordLabel.setBounds(20,170,100,70);
        confirmPasswordLabel.setBounds(20,220,140,70);
        cityLabel.setBounds(20,270,100,70);
        usernameLabel.setBounds(20,320,100,70);
        nameTextField.setBounds(180,43,165,23);
        genderComboBox.setBounds(180,93,165,23);
        
        passwordField.setBounds(180,193,165,23);
        confirmPasswordField.setBounds(180,243,165,23);
        cityTextField.setBounds(180,293,165,23);
        usernameTextField.setBounds(180,343,165,23);
        registerButton.setBounds(70,400,100,35);
        resetButton.setBounds(180,400,170,35);
    }
    
    /**
	 * coding part of add components like label, textbox, to the frame
	 * 
	 * 
	 * 
	 */
    public void addComponentsToFrame()
    {
        frame.add(nameLabel);
        frame.add(genderLabel);
        
        frame.add(passwordLabel);
        frame.add(confirmPasswordLabel);
        frame.add(cityLabel);
        frame.add(usernameLabel);
        frame.add(nameTextField);
        frame.add(genderComboBox);
       
        frame.add(passwordField);
        frame.add(confirmPasswordField);
        frame.add(cityTextField);
        frame.add(usernameTextField);
        frame.add(registerButton);
        frame.add(resetButton);
    }
    
    /**
	 * coding part to add register button and reset button to the register form
	 * 
	 * 
	 * 
	 */
    public void actionEvent()
    {
        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    /**
	 * Method that is called when a button has been pressed to register to the system.
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        /**
    	 * coding part of register button to start registering process
    	 * 
    	 * 
    	 * 
    	 */
        if(e.getSource()==registerButton)
        {
            try {
            	
            	 /**
            	 * code to connect to database
            	 * inserting name username, password, gender,city to the database for registering
            	 */
                //Creating Connection Object
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/game?serverTimezone=UTC","root","");
                //Preapared Statement
                PreparedStatement Pstatement= connection.prepareStatement("insert into player(name,gender,password,confirm_password,city_name,username) values(?,?,?,?,?,?)");
                //Specifying the values of it's parameter
                Pstatement.setString(1,nameTextField.getText());
                Pstatement.setString(2,genderComboBox.getSelectedItem().toString());
                Pstatement.setString(3,passwordField.getText());
                Pstatement.setString(4,confirmPasswordField.getText());
                Pstatement.setString(5,cityTextField.getText());
                Pstatement.setString(6,usernameTextField.getText());
                
                PreparedStatement st = connection.prepareStatement("select username from player where username = '"+usernameTextField.getText()+"'");
               // ResultSet r1=st.executeQuery();
               // st.setString(1,username);
                ResultSet r1=st.executeQuery();
                
                if(nameTextField.getText().hashCode() == 0 || passwordField.getText().hashCode() == 0 || cityTextField.getText().hashCode() == 0 || usernameTextField.getText().hashCode() == 0){
                    JOptionPane.showMessageDialog(null, " textfields are empty");
                }
                else {
                    /**
                	 * checking for the password match
                	 * 
                	 * 
                	 * 
                	 */
                	 //Checking for the Password match
                    if(passwordField.getText().equalsIgnoreCase(confirmPasswordField.getText()))
                    {
                        /**
                    	 * executing query to register to the system and showing login form after registering
                    	 * 
                    	 * validating if username is already taken
                    	 * validating password and confirm password is matched or not 
                    	 * 
                    	 */
                    	
                    	 if(r1.next()) 
                         {
                         
                         JOptionPane.showMessageDialog(null,"username already taken");
                              
                           
                         }
                    	 
                    	 else {
                    	
                        Pstatement.executeUpdate();
                        JOptionPane.showMessageDialog(null,"You are registered Successfully");
                        frame.setVisible(false);
                        new LoginForm().setVisible(true);
                    	 }
                    }
                    
                   
                    
                    else
                    {
                        JOptionPane.showMessageDialog(null,"password and confirm password did not match");
                    }
                }
               
                
               
                
                

            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        }
        /**
    	 * coding part of already user logi button to login to the syste 
    	 * 
    	 * 
    	 * 
    	 */
        if(e.getSource() == resetButton)
        {
        	frame.setVisible(false);
        	//RegistrationForm().setVisible(false);
        	new LoginForm().setVisible(true);
   
        }

    }
}
