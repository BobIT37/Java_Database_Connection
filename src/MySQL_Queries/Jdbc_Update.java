package MySQL_Queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Jdbc_Update {

	public static void main(String[] args) throws SQLException {
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		//1. Get connection to database
		
		try{
			
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo"
		  		+ "?useUnicode=true"
		  		+ "&useJDBCCompliantTimezoneShift=true"
		  		+ "&useLegacyDatetimeCode=false"
		  		+ "&serverTimezone=UTC", "ilhan", "ilhan");
					
					System.out.println("Database connection successful!\n");
					
		//2. Create a statement			
				
		myStmt = myConn.createStatement();
		
		//3. Before the update to display the employee's info
		
		System.out.println("BEFORE THE UPDATE....");
		//helper method
		displayEmployee(myConn, "John", "Doe");
		
		//UPDATE THE Employee
		System.out.println("\nEXECUTING THE UPDATE FOR: John Doe\n");
		
		int rowsAffected = myStmt.executeUpdate(
				"update employees "+
		        "set email='john.doe@foo.com' "+
		        "where last_name='Doe' and first_name='John'"				

				);
		
		//Verify Updating
		System.out.println("AFTER THE UPDATE...");
		//helper method
		displayEmployee(myConn, "John", "Doe");
		
		
	     				
		}catch(Exception exc){
			exc.printStackTrace();
		}
		finally {
			close(myConn, myStmt, myRs);
			}
		
	}
	private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException{
	   PreparedStatement myStmt = null;
	   ResultSet myRs = null;
	   
	   try {
		   //Prepare statement
		   myStmt = myConn
				   .prepareStatement("select last_name, first_name, email from employees where last_name=? and first_name=?");
		   myStmt.setString(1, lastName);
		   myStmt.setString(2, firstName);
		   
		   //execute SQL query
		   myRs = myStmt.executeQuery();
		   
		   //Process result set
		   while(myRs.next()){
			   String theLastName = myRs.getString("last_name");
			   String theFirstName = myRs.getString("first_name");
			   String email = myRs.getString("email");
			   
			   System.out.printf("%s, %s, %s\n", theFirstName, theLastName, email);
		   }
		
	   } catch (Exception exc) {
		exc.printStackTrace();
	      }finally {
			close(myStmt, myRs);
		}
	  
	}
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException{
		
		if(myRs !=null){
			myRs.close();
		}
		if(myStmt !=null){
			myStmt.close();
		}
		if(myConn !=null){
			myConn.close();
		}
	}
	
	private static void close(Statement myStmt, ResultSet myRs) throws SQLException{
		
		close(null, myStmt, myRs);
		
	}

}
