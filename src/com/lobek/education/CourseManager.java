package com.lobek.education;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @author Andrew Oseghale
 *
 */
public class CourseManager implements DataTypeManager {
	
	private Connection conn;

	public CourseManager(Connection conn) {
		this.conn = conn;
	}
	
	public int getRecordCount() {
		int rtn = 0;
		
		try {
			Statement stmt = this.conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM course");
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
		Scanner console = new Scanner(System.in);
		
		do {
			String consoleLine = console.nextLine();
			c = consoleLine.charAt(0);
			
			switch(c) {
				case 'l':
					listAll();
					System.out.format("%n%n");
					printInstructions();
					break;
										
				case 'b': // Go Back
					break;
					
				default:
					System.out.println("You entered an unsupported command (" + c + ")");
					
			}
		} while (c != 'b');
	}
	
	private void printInstructions() {
		System.out.format("%n%n");
		System.out.println("=======================================================================");
		System.out.format ("| COURSES MANAGER (TOTAL RECORDS: %3d)                                |%n", getRecordCount());
		System.out.println("=======================================================================");
		System.out.println("USAGE: enter a command below to manage the courses");
		System.out.println("l                           List all courses");
		System.out.println("a                           Add a new course");
		System.out.println("d                           Delete a course");
		System.out.println("m                           Modify a course");
		System.out.println("b                           Go Back");
		System.out.print("Please enter your command to continue: ");
	}
	
	public void listAll() {
		
	}

}
