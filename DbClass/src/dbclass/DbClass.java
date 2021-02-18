/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclass;
import java.sql.*;
import java.io.*;
/**
 *
 * @author hradw
 */
public class DbClass {

    Statement stm;
    Connection con;

    public DbClass(String ipAddress, String portNumber, String DbName, String usrName, String paswrd)
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection
            (
            "jdbc:mysql://"+ipAddress+":"+portNumber+"/"+DbName,
            usrName,
            paswrd
            ); 
        }
        catch(SQLException ex)
        {
                ex.printStackTrace();
        }

    }
    void selectFromDB(String tblName)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select * from "+tblName+"");
            ResultSet rs = stm.executeQuery(queryString);
            
            while(rs.next())
            {
                System.out.print(rs.getString(1)+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3)+" ");
                System.out.print(rs.getString(4)+" ");
                System.out.print(rs.getString(5)+" ");
                System.out.print(rs.getString(6)+" ");
                System.out.print(rs.getString(7)+" ");
                System.out.print(rs.getString(8)+" ");
                
                
                System.out.println();
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    void showScoreBoard()
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select username, win, draw, lose from players");
            ResultSet rs = stm.executeQuery(queryString);
            while(rs.next())
            {
                System.out.print(rs.getString(1)+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3)+" ");
                System.out.print(rs.getString(4));
                System.out.println();
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        } 
    }
    String checkPlayer(String plyerId, String question)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select "+question+" from players where player_id = "+plyerId);
            ResultSet rs = stm.executeQuery(queryString);
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    void updateScore(String usrName, String result)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select "+result+"  from players where username= '"+usrName+"'");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next();
            int oldScore = Integer.parseInt(rs.getString(1));
            String newScore=String.valueOf(oldScore + 1); 
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET "+result+"="+newScore+" WHERE username= '"+usrName+"'");
            stm.execute(queryStringUpdate);    
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        } 
        
    }
    boolean insertInPlayers( String usrName, String pswrd, String win, 
                            String draw, String lose, String status, String isPlaying)
    {
        try
        {
            stm = con.createStatement();
            String queryStringInsert = new 
                String("insert into players (username, password, win, draw, lose, status, isPlaying)values('"+usrName+"', '"+pswrd+"', "
                        +win+", "+draw+", "+lose+", '"+status+"', '"+isPlaying+"')");
            stm.execute(queryStringInsert);
            return true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    boolean checkIfuserExist(String usrName)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select username from players where username = '"+usrName+"'");
            ResultSet rs = stm.executeQuery(queryString);
            if(rs.next() == false)
            {
                //System.out.println("user dose not exist ");
                return false;
            }
            else
            {
                //System.out.println(rs.getString(1)+" "); 
                return true;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
            
        }
        
    }
    boolean checkUserPassword(String usrName, String passwd)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select password from players where username = '"+usrName+"'");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next();
            if(new String(passwd).equals(rs.getString(1)))
            {
                //System.out.println("hello");
                return true;
            }
            else
            {
                
                return false;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
            
        }
        
    }
    
    void insertInGame(String gameId, String player1, String player2 )
    {
        try
        {
            stm = con.createStatement();
            String queryStringInsert = new String("insert into game values("+gameId+", "+player1+", "+player2+")");
            stm.execute(queryStringInsert);
            /*for resting***************/
            stm = con.createStatement();
            String queryString = new String("select * from game");
            ResultSet rs = stm.executeQuery(queryString);
            while(rs.next())
            {
                System.out.print(rs.getString(1)+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3));
                System.out.println();
            }
            /***************************/
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    void deleteFromTable(String tblName,String clmn, String info)
    {
        try
        {
            stm = con.createStatement();
            //String queryStringDelete = new String("delete from "+tblName+"where "+clmn+"="+info);
            String queryStringDelete = new String("delete from "+tblName+" where "+clmn+"="+info);
            stm.execute(queryStringDelete);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        } 
    }
    void closeConnection()
    {
        try
        {
            stm.close();
            con.close(); 
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
}
