package com.ev;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileFormatConversion {
	public static void main(String args[]) {
		try {
			String fileString = new String(Files.readAllBytes(Paths
					.get("D:\\Work\\Upload\\mcbx7160.uspc")),
					StandardCharsets.UTF_8);
			/*System.out.println("Contents (Java 7 with character encoding ) : "
					+ fileString);*/
			
			int	 initial = fileString.indexOf("INIT_UPLOAD");
			
			int last = fileString.indexOf(']');
			String newStr = fileString.substring(initial, last).trim();
			newStr=newStr.replace("INIT_UPLOAD", "\"INIT_UPLOAD\"");
			StringBuffer sb = new StringBuffer(newStr);
			sb.deleteCharAt(sb.length()-1);
			sb.append(']');
			//System.out.println(sb);
			//System.out.println("--------------" + sb.charAt(sb.length()-1));
			newStr = sb.toString();
			newStr = newStr.replace('\'', '\"');
			String finalStr = newStr.replace("\"\"\"", "\"");
			System.out.println("Finally.................\n"+finalStr);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
