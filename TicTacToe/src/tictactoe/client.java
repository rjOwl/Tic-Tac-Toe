import java.net.*;  
import java.io.*;  
class client{  
public static void main(String args[])throws Exception{  

		Socket s=new Socket("localhost",5000); 
		
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		  
		String str="",str2="",str3="";  
		while(!str.equals("stop"))
		{  
			str=br.readLine();  
			dout.writeUTF(str);  
			str2=br.readLine();  
			dout.writeUTF(str2);  
			dout.flush(); 
			

			//str2=din.readUTF();  
			//System.out.println("Server says: "+str2);  
		}  
		  
		dout.close();  
		s.close();  
}}