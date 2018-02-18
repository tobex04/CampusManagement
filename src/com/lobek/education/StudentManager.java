package com.lobek.education;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @author Andrew Oseghale
 *
 */
public class StudentManager implements DataTypeManager {
	
	private Connection conn;
	private Scanner inputs;
	private Scanner console;

	public StudentManager(Connection conn) {
		this.conn = conn;
	}
	
	public int getRecordCount() {
		int rtn = 0;
		
		try {
			Statement stmt = this.conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM student");
			rs.next();
			int rowCount = rs.getInt(1);
			
			rtn = rowCount;
		} catch (Exception e) {
			;
		}
		
		return rtn;
	
	}
	
	public void process() {
		boolean done = false;
		
		printInstructions();
		
		char c; 
			console = new Scanner(System.in);
		
		do {
			String consoleLine = console.nextLine();
			c = consoleLine.charAt(0);
			
			switch(c) {
				case 'l':
					listAll();
					System.out.format("%n%n");
					printInstructions();
					break;
				case 'a':
					add2List();
					System.out.format("%n%n");
					printInstructions();
					break;		
				case 'd':
					delete();
					System.out.format("%n%n");
					printInstructions();
					break;
				case 'm':
					modify();
					System.out.format("%n%n");
					printInstructions();
					break;
										
				case 'b': // Go Back
					break;
					
				default:
					System.out.println("You entered an unsupported command (" + c + ")");
					
				console.close();
					
			}
		} while (c != 'b');
	}
	
	private void printInstructions() {
		System.out.format("%n%n");
		System.out.println("=======================================================================");
		System.out.format ("| STUDENTS MANAGER (TOTAL RECORDS: %3d)                               |%n", getRecordCount());
		System.out.println("=======================================================================");
		System.out.println("USAGE: enter a command below to manage the students");
		System.out.println("l                           List all students");
		System.out.println("a                           Add a new student");
		System.out.println("d                           Delete a student");
		System.out.println("m                           Modify a student");
		System.out.println("b                           Go Back");
		System.out.print("Please enter your command to continue: ");
	}
	
	public void listAll() {
		try {
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM student");
			System.out.println("id  , firstname   , lastname   ,  dateOfBirth   ,  sex ");
	
			while (rs.next()) {
	
				String id = rs.getString("id");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String dateOfBirth = rs.getString("dateOfBirth");
				String sex = rs.getString("sex");
				
				System.out.println(id+"  "+firstname+"  "+lastname+"  "+dateOfBirth+"  "+sex);
			}
		stmt.close();
		rs.close();
		} 
		
		catch(SQLException e) {
		System.out.println("SQL Exception Occured" + e);
		}

	}
	public void add2List() {
		
		try {
	         inputs = new Scanner(System.in);
	         
	         //System.out.println("Enter the Student ID number");
	         //String idnumber = inputs.nextLine();
	         System.out.println("Enter the Student Firstname");
	         String infirstname = inputs.nextLine();
	         System.out.println("Enter the Student Lastname");
	         String inlastname = inputs.nextLine(); //System.console().readLine();
	         System.out.println("Enter the Student Date of Birth");
	         String indateOfBirth = inputs.nextLine();
	         System.out.println("Enter the Student Sex");
	         String insex = inputs.nextLine();
	         
	         Statement stmt = conn.createStatement();
	         
	         //String sql = "INSERT INTO student (id,firstname,lastname,dateOfBirth,sex) VALUES  ("+idnumber+","+infirstname+","+inlastname+","+indateOfBirth+","+insex+")";
	         
	         String sql = "INSERT INTO student (firstname,lastname,dateOfBirth,sex) VALUES  (?,?,?,?)";
	         
	         PreparedStatement ps = conn.prepareStatement(sql);
	         
	         ps.setString(1, infirstname);
	         ps.setString(2, inlastname);
	         ps.setString(3, indateOfBirth);
	         ps.setString(4, insex);
	         
	         
	         stmt.executeUpdate(sql);
	         
	         ResultSet rs = stmt.executeQuery("SELECT * FROM student");
				System.out.println("id  , firstname   , lastname   ,  dateOfBirth   ,  sex ");
		
	         while (rs.next()) {
	 			
					String idnumber = rs.getString("id");
					String firstname = rs.getString("firstname");
					String lastname = rs.getString("lastname");
					String dateOfBirth = rs.getString("dateOfBirth");
					String sex = rs.getString("sex");
					
					System.out.println(idnumber+"  "+firstname+"  "+lastname+"  "+dateOfBirth+"  "+sex);
				}
	         
	         System.out.println("Thank you for adding a new Student!");
	         
	         
	         rs.close();
	         stmt.close();
	                 
		} 
		catch(SQLException e) {
		System.out.println("SQL Exception Occured" + e);
		}
		
	}
	
	public void delete() {
		try {
		         
		         Statement stmt = conn.createStatement();
		         inputs = new Scanner(System.in);
		         System.out.println("Enter Student ID number to be Removed");
		         String idnumber = inputs.nextLine();
		         
		         stmt.executeUpdate("DELETE FROM student WHERE id = " + idnumber);
		         System.out.println("Student Deleted");
		         
		         ResultSet rs = stmt.executeQuery("SELECT * FROM student");
					System.out.println("id  , firstname   , lastname   ,  dateOfBirth   ,  sex ");
			
					while (rs.next()) {
			
						String id = rs.getString("id");
						String firstname = rs.getString("firstname");
						String lastname = rs.getString("lastname");
						String dateOfBirth = rs.getString("dateOfBirth");
						String sex = rs.getString("sex");
						
						System.out.println(id+"  "+firstname+"  "+lastname+"  "+dateOfBirth+"  "+sex);
					}
					rs.close();
					stmt.close();
					
		   }
		catch(SQLException e) {
		System.out.println("SQL Exception Occured" + e);
		}

	}
	public void modify() {
		try {
		
			Statement stmt = conn.createStatement();
			inputs = new Scanner(System.in);
	        
			 System.out.println("Enter the Student ID number");
	         String idnumber = inputs.nextLine();
			 System.out.println("Update the Student Firstname");
	         String infirstname = inputs.nextLine();
	         //System.out.println("Enter the Student Lastname");
	         //String inlastname = inputs.nextLine(); //System.console().readLine();
	         //System.out.println("Enter the Student Date of Birth");
	         //String indateOfBirth = inputs.nextLine();
	         //System.out.println("Enter the Student Sex");
	         //String insex = inputs.nextLine();
	         
			
			String sql = "UPDATE student set firstname = "+infirstname+" where id = "+idnumber+"";
			stmt.executeUpdate(sql);
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM student");
	         System.out.println("id  ,  firstname  ,   lastname   ,   dateOfBirth   ,    sex");
	         
	         while (rs.next()) {
	            int id = rs.getInt("id");
	            String firstname = rs.getString("firstname");
	            String lastname = rs.getString("lastname");
	            String dateOfBirth = rs.getString("dateOfBirth");
	            String sex = rs.getString("sex");
	            System.out.println(id+"   "+firstname+"    "+lastname+"   "+dateOfBirth+"    "+sex);
	         }
	         rs.close();
	         stmt.close();
		} 
		catch(SQLException e) {
		System.out.println("SQL Exception Occured" + e);
		}

		}

}


