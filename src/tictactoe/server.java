import java.net.*;  
import java.io.*;  

class server{  
public static void main(String args[])throws Exception{  

		ServerSocket ss=new ServerSocket(5000);
		Socket s = ss.accept();  

		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  

		String str="",str2="",str3="";  

		while(!str.equals("stop"))
		{
			str=din.readUTF();  
			str2=din.readUTF();  
			System.out.println("client Data 1: "+str); 
			System.out.println("client Data 2: "+str2);
			
			//str3=br.readLine();  
			//dout.writeUTF(str2);  
			//dout.flush();  
		}  

	din.close();  
	s.close();  
	ss.close();  
}} 