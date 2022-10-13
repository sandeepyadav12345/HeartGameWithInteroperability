package com.perisic.sixeq.peripherals;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.perisic.sixeq.engine.GameEngine;
import java.io.DataOutputStream;
import java.net.*;
/**
 * A Simple Graphical User Interface for the Six Equation Game.
 * 
 * @author Marc Conrad
 *
 */
public class GameGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -107785653906635L;
	private static final String String = null;
	

	/**
	 * Method that is called when a button has been pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int solution = Integer.parseInt(e.getActionCommand());
		boolean correct = myGame.checkSolution(currentGame, solution);
		Integer score = myGame.getScore();
		String player1 = myGame.getPlayer();
	     
		if (correct) {
			System.out.println("YEAH!");
			System.out.println(player1);
			currentGame = myGame.nextGame(); 
			ImageIcon ii = new ImageIcon(currentGame);
			questArea.setIcon(ii);
			infoArea.setText("Good!  Score: "+score);
			try {
	 Runtime.getRuntime().exec("python C:\\Users\\sy984\\eclipse-workspace\\HeartGameVersion2\\com\\perisic\\sixeq\\peripherals/interopserverjava.py");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
	        /**
	    	 * coding part of socket to send score to listener written in python to update score of respective player
	    	 * 
	    	 * 
	    	 * 
	    	 */
			try {
				Socket socket = new Socket("localhost",49191);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(score.toString()+","+player1);
				dos.flush();
				
			//	dos.close();
			//	socket.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else { 
			System.out.println("Not Correct"); 
			infoArea.setText("Oops. Try again!  Score: "+score);
		}
		
	}

	JLabel questArea = null;
	GameEngine myGame = null;
	URL currentGame = null;
	JTextArea infoArea = null;
	
	
/**
 * Initializes the game. 
 * @param player
 */
	private void initGame(String player) {
		
		try {
		setSize(690, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("What is the value of the heart?");
		JPanel panel = new JPanel();
		Connection	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game?serverTimezone=UTC","root","");
		PreparedStatement	Pstatement = connection.prepareStatement("select score from player where username = '"+player+"' ");
		ResultSet rs = Pstatement.executeQuery();
		
		while(rs.next()) {
			
			String s = rs.getString("score");
			System.out.println(s);
			myGame = new GameEngine(player);
			currentGame = myGame.nextGame();
			infoArea = new JTextArea(1, 45);
			infoArea.setEditable(false);
			infoArea.setFont(new Font("Serif", Font.BOLD,15));
			infoArea.setText("Player name: "+ player+ " Last Score: "+ s + " What is the value of Heart? Score: 0"  );
		}
		
		JScrollPane infoPane = new JScrollPane(infoArea);
		panel.add(infoPane);
		

		ImageIcon ii = new ImageIcon(currentGame);
		questArea = new JLabel(ii);
	    questArea.setSize(330, 600);
	    
		JScrollPane questPane = new JScrollPane(questArea);
		panel.add(questPane);

		for (int i = 0; i < 10; i++) {
			JButton btn = new JButton(String.valueOf(i));
			panel.add(btn);
			btn.addActionListener(this);
		}
		
		JButton lgbtn = new JButton("logout");
		lgbtn.setBounds(50, 300, 100, 30);
		panel.add(lgbtn);
		
		lgbtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				//  this.setVisible(false);
				 // GameGUI myGUI = new GameGUI();
				 panel.setVisible(false);
			 new   LoginForm().setVisible(true);
			  } 
			} );
	

		getContentPane().add(panel);
		panel.repaint();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
		
/**
 * Default player is null. 
 */
	public GameGUI() {
		super();
		initGame(null);
		
		
	}

	/**
	 * Use this to start GUI, e.g., after login.
	 * 
	 * @param player
	 */
	public GameGUI(String player) {
		super();
		initGame(player);
		
	
	}

	/**
	 * Main entry point into the equation game.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		GameGUI myGUI = new GameGUI();
		myGUI.setVisible(true);
		
	
	}
}