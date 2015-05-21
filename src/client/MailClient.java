package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MailClient {
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the recipient:");
		String rcpt=scan.nextLine();
		System.out.println("Please enter the sender:");
		String sender=scan.nextLine();
		System.out.println("Please enter the subject:");
		String subject=scan.nextLine();
		System.out.println("Please enter the content:");
		String content=scan.nextLine();
		
		sendMail(rcpt,sender,subject,content);
		
		
	}
	
	public static void sendMail(String rcpt, String sender, String subject, String content){
		
		int port=587;
		String host = "smtp.gmail.com";
		DataInputStream in = null;
		DataOutputStream out = null;
		Socket socket = null;
		
		try {
			socket=new Socket(host,port);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(socket !=null && in !=null && out != null){
			try{
				out.writeBytes("HELO\n");
				out.writeBytes("MAIL From: "+sender+"\n");
				out.writeBytes("RCPT To: " + rcpt+"\n");
				out.writeBytes("DATA\n");
				out.writeBytes("From: " +sender+"\n");
				out.writeBytes("Subject: "+subject+"\n");
				out.writeBytes(content+"\n");
				out.writeBytes("\n. \n");
				out.writeBytes("QUIT");
				
                String responseLine;
                BufferedReader lines = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                while ((responseLine = lines.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("Ok") != -1) {
                      break;
                    }
                }
                
                out.close();
                in.close();
                socket.close();				
			}
			
			catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
		}
		
	}
	
	

}
