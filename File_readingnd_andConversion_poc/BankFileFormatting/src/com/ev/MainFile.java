package com.ev;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFile {
	private static BufferedReader bReader;
	private static String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static String Connection_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String USER_NAME = "system";
	private static String PASSWORD = "oracle";
	
	public static void main(String args[]) throws Exception {
		System.out.println("Enter the file path and table name");
		 bReader = new BufferedReader(new InputStreamReader(System.in));
		String txtFile = bReader.readLine();
		String tableName = bReader.readLine();
		generateFileToDB(txtFile,tableName);
		System.out.println("File updated successfully in DB");
	}
	
	private static void generateFileToDB(String txtFile, String tableName) throws Exception {
	    final Path path = Paths.get("path", "to", "folder");
	    String[] csvAr = txtFile.split("\\");
	    csvAr[csvAr.length-1]="excelSchema.csv";
	    StringBuffer csvFile = new StringBuffer();
	    for(String csvStr : csvAr)
	    {
	    	csvFile.append(csvStr);
	    }
	    final Path txt = path.resolve(txtFile);
	    final Path csv = path.resolve(csvFile.toString());
	    try (
	            final Stream<String> lines = Files.lines(txt);
	            final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, StandardOpenOption.CREATE_NEW))) {
	        lines.map((line) -> line.split("\\s+")).
	                map((line) -> Stream.of(line).collect(Collectors.joining(","))).
	                forEach(pw::println);
	        insertingToDB(csv.toString(),tableName);
	    }
	}
	
	private static void insertingToDB(String csv, String tableName)
	{
		String createStmt ="CREATE TABLE "+tableName; 
		String sqlInsert = "INSERT INTO "+tableName+"(";
		String endInsertSql = ") VALUES(?,?,?,?) ";
		StringBuffer sqlInsertBuffer = new StringBuffer(sqlInsert);
		Connection con = null;
		Statement stmt = null;
		try { 
		        bReader = new BufferedReader(new FileReader(csv));
		        String firstLine = bReader.readLine();
		        String[] columnNames = firstLine.split(",");
		        int rowLength = columnNames.length;
		        StringBuffer createSqlBuffer = new StringBuffer();
		        createSqlBuffer.append(createStmt);
		        //Loop to generate create and Insert query using first line
		        for(int i=0;i<rowLength;i++)
		        {
		        	sqlInsertBuffer.append(columnNames[i]);
		        	createSqlBuffer.append(columnNames[i] +"VARCHAR(255), ");
		        }
		        createSqlBuffer.append(" PRIMARY KEY ( "+columnNames[0]+" ))");
		        String createdSql = createSqlBuffer.toString();
		        sqlInsertBuffer.append(endInsertSql);
		        String sql = sqlInsertBuffer.toString();
		        String line = ""; 
		        Class.forName(DRIVER_NAME);  
          	  
                con=DriverManager.getConnection(Connection_URL,USER_NAME,PASSWORD);  
                System.out.println("Connected database successfully...");
             
                System.out.println("Creating table in given database...");
                stmt = con.createStatement();
                stmt.executeUpdate(createdSql);
	           System.out.println("Created table in given database...");
           
	           //Inserting into table
            	while ((line = bReader.readLine()) != null) {
		            	if (line != null) 
		                {
		                	PreparedStatement ps =null;
		                    String[] array = line.split(",+");
		                    for(int j=0;j<array.length;j++)
		                    {
				                 ps = con.prepareStatement(sql);
				                 ps.setString(j+1,array[j]);
		                    }
			                 ps.executeUpdate();
			                 ps. close();
		                    }
		            	
		        }
            	throw new ArrayIndexOutOfBoundsException();
		    } catch (FileNotFoundException ex) {
		        ex.printStackTrace();
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		finally{
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

}
