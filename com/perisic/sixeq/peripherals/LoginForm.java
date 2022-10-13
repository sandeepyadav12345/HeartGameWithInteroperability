package com.perisic.sixeq.peripherals;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Simple Graphical User Interface for login to the Math Game.
 * 
 * 
 *
 */

public class LoginForm extends JFrame implements ActionListener {
	
	/**
	 * A Simple Graphical User Interface for login to the Math Game.
	 * using file system to save username and password to system computer
	 */
	File file = new File(System.getProperty("user.home")+"\\Documents\\save.txt");
    public static void main(String[] a) throws IOException {
    	
 
    	
      //  LoginForm frame = new LoginForm();
    	LoginForm frame = new LoginForm();
        frame.setTitle("Login Form");
        
        frame.setBounds(10, 10, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
       // frame.setUndecorated(true);
       // frame.setSize(690, 500);
       // frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);     

       
}
    
    Container container = getContentPane();
    JLabel headingLabel = new JLabel("The Heart Game With COVID19 LIVE RESULTS NEPAL");
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton registerButton = new JButton("New user, REGISTER");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JCheckBox rememberMe = new JCheckBox("Remember Me");
    
    
    /**
	 * LoginForm() Method to contain components
	 * set location and size of components
	 * 
	 *
	 */

    LoginForm() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        UPDATE();
        
        File file = new File(System.getProperty("user.home")+"\\Desktop\\save.txt"); 
        String demo = file.getAbsolutePath();
        System.out.println(demo); 
        /**
  	   * here i am fetching the coronavirus live json data from rapiapi .
  	   * And displaying the json data in table
  	   * 
  	   *
  	   */
        HttpRequest request = HttpRequest.newBuilder()
 				.uri(URI.create("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=Nepal"))
 				.header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
 				.header("x-rapidapi-key", "3156a51162msh61ca384dd671c70p147f96jsn702d5d5c6902")
 				.method("GET", HttpRequest.BodyPublishers.noBody())
 				.build();
 		HttpResponse<String> response = null;
 		try {
 			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		JSONObject obj = null;
		try {
			obj = new JSONObject(response.body());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONObject pageName = null;
		try {
			pageName = obj.getJSONObject("data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //System.out.println(pageName);

        JSONArray arr = null;
        	try {
				arr=	pageName.getJSONArray("covid19Stats");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	/**
        	 * storing the json data values to the variables
        	 * 
        	 *
        	 * 
        	 */
      for (int i = 0; i < arr.length(); i++) {
            String country = null;
            Integer confirmed = null;
            Integer death = null;
            String lastupdate = null;
			try {
				country = arr.getJSONObject(i).getString("country");
				confirmed = arr.getJSONObject(i).getInt("confirmed");
				death = arr.getJSONObject(i).getInt("deaths");
				lastupdate = arr.getJSONObject(i).getString("lastUpdate");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           // System.out.println(post_id);
 		
 		//System.out.println(response.body().toString());
 		
 		String data[][]={ {"Country",country},    
                 {"Confirmed Cases",confirmed.toString()},    
                 {"Deaths",death.toString()},{"Updated At",lastupdate}}; 
 		 String column[]= {"COVID19","Results"};  
 		/**
 		 * Displaying data from api to table
 		 */
 		JTable jtable = new JTable(data,column);
 		jtable.setBounds(310,150,350,100);
 		 //jtable.setTableHeader(new SingleColumnTableHeader());
        
         jtable.setGridColor(Color.LIGHT_GRAY);
         Color ivory = new Color(255, 255, 208);
         jtable.setBackground(ivory);
         jtable.setFont(new Font("Serif", Font.BOLD, 15));
        // JScrollPane scrollPane = new JScrollPane(jtable);
        // scrollPane.setBounds(310, 150, 350, 100);
        // container.add(scrollPane);
 	     container.add(jtable);
 	    // setContentPane(scrollPane);
 	     
    }
    }

    public void setLayoutManager() {
        container.setLayout(null);
       
    }

    /**
	 * setting location and size of the components of login form
	 */
    public void setLocationAndSize() {
    	headingLabel.setBounds(100,50,600,100);
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        rememberMe.setBounds(150, 270, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        registerButton.setBounds(160, 300, 180, 30);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 20));
        headingLabel.setForeground(Color.RED);
        
        
    }

    /**
	 * Adding components to the container to login frame
	 * 
	 * 
	 * 
	 */
    public void addComponentsToContainer() {
        container.add(headingLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registerButton);
        container.add(rememberMe);
       
    }
    /**
	 * adding button login, register to the login frame
	 * 
	 * 
	 * 
	 */
    public void addActionEvent() {
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        showPassword.addActionListener(this);
        rememberMe.addActionListener(this);
    }
    
    /**
	 * Method that is called when a button has been pressed to login to the system.
	 */
    
   
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
           // SAVE();
            /**
        	 * code to connect to database
        	 * checking usernaname and password to login to the system
        	 */
            try {
     Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/game?serverTimezone=UTC","root","");
            String query = "SELECT * FROM `player` WHERE `username` =? AND `password` =?";
            PreparedStatement Pstatement= connection.prepareStatement(query);
            Pstatement.setString(1, userText);
            Pstatement.setString(2, pwdText);
            ResultSet rs = Pstatement.executeQuery();
            if(rs.next()) {
            	GameGUI theGame = new GameGUI(userText);
				theGame.setVisible(true); 
				dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        
            
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
            
           
        }
        
        /**
    	 * coding part of register button to show registration form
    	 * 
    	 * 
    	 * 
    	 */
        
        if (e.getSource() == registerButton) {
        this.setVisible(false);
        new RegistrationForm();
            
        }
        
        /**
    	 * coding part of show password jcheckbox 
    	 * 
    	 * 
    	 * 
    	 */
        
       //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        }
        /**
         * coding part of remember me to save username and password to computer system
         * after checking remeber me.
         * 
         *
         */
        if (e.getSource() == rememberMe) {
        	 
                

                    // if(rememberMe.isSelected()){
                        SAVE(); //Save This UserName and his PassWord     
                   //  }

                 //end of mouseReleased
            

        }
        
       
        
    }
    
    /**
     * coding part of remember me to save username and password to computer system
     * 
     * 
     *
     */ 
    public void SAVE(){      //Save the UserName and Password (for one user)

        try {
           // if(!file.exists()){
         //  file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            bw.write(userTextField.getText()); //write the name
            bw.newLine(); //leave a new Line
            bw.write(passwordField.getPassword()); //write the password
            bw.close(); //close the BufferdWriter

      //  } 
        }  catch (IOException e) { e.printStackTrace(); }        

 }
    /**
     * coding part to update username and password to respective textfeild
     * 
     * so that we do not have to enter username and password
     *
     */
    
    public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION

        try {
          if(file.exists()){    //if this file exists

            Scanner scan = new Scanner(file);   //Use Scanner to read the File

            userTextField.setText(scan.nextLine());  //append the text to name field
            passwordField.setText(scan.nextLine()); //append the text to password field
            scan.close();
          }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }                

   }//End OF UPDATE  

}

