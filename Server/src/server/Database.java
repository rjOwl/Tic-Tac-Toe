/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hradw
 */
public class Database {
    
    Statement stm;
    Connection con;
    
    private static Database s = null;  
    private/*public */Database(String ipAddress, String portNumber, String DbName, String usrName, String paswrd)
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
    public static synchronized Database getInstance() 
    {
      if (s== null)
      {
           s = new Database("localhost","3306","tictactoe","root","12345"); 
      }
      return s; 
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
                System.out.println();
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    void resetUsers()
    {
        try
        {
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET status = 'offline', isPlaying = 'no', "
                    + "gameId = null, playAgain = 'false', threadNumber = null WHERE status = 'online' ");
            stm.execute(queryStringUpdate); 
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
    }
    String showScoreBoard()
    {
        try
        {
            String result = "";
            stm = con.createStatement();
            String queryString = new String("select count(username) from players");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next();
            String numberOfPlayers = rs.getString(1);
            result += numberOfPlayers+",";
            
            stm = con.createStatement();
            String queryString2 = new String("select username, win, draw, lose from players order by win desc, draw desc, lose");
            ResultSet rs2 = stm.executeQuery(queryString2);
            while(rs2.next())
            {
                result += rs2.getString(1)+","+rs2.getString(2)+","+rs2.getString(3)+","+rs2.getString(4)+",";
            }
            result += "end";
            System.out.print(result);
            return result;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return "false";
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
    void updatePlayerStatus(String userName, String mood, String info)
    {
        try
        {
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET "+mood+"='"+info+"' WHERE username= '"+userName+"'");
            stm.execute(queryStringUpdate);    
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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
    boolean checkIfRoomIsAvailable(String gameId)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select count(gameId) from players where gameId= "+gameId+"");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next();
            if(new String("2").equals(rs.getString(1)))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;  
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
    void setGameId(String userName, String gameId)
    {
        try
        {
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET gameId = "+gameId+" WHERE username= '"+userName+"'");
            stm.execute(queryStringUpdate);           
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();            
        }  
    }
    String checkOtherOpponent(String userName, String gameId)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select username from players where gameId= "+gameId+" and "
                    + "username != '"+userName+"'");
            ResultSet rs = stm.executeQuery(queryString);
            
            
            
            if(!rs.next())
            {
                return "playerNotFound";
            }
            else
            {
                String otherPlayer = rs.getString(1);
                return otherPlayer;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return "nan";
        } 
        
    }
    String removeGameId(String player)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select gameId from players where username= '"+player+"' and isPlaying = 'yes'");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next(); 
            String gameId = rs.getString(1);
            
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET gameId = null WHERE username= '"+player+"'");
            stm.execute(queryStringUpdate);  
            
            stm = con.createStatement();
            String queryString2 = new String("select username from players where gameId= "+gameId+" and isPlaying = 'yes'");
            ResultSet rs2 = stm.executeQuery(queryString2);
            rs2.next(); 
            String otherPlayer = rs2.getString(1);
            
            stm= con.createStatement();
            String queryStringUpdate2 = new String("UPDATE players SET gameId = null WHERE username= '"+otherPlayer+"'");
            stm.execute(queryStringUpdate2);
            
            
            return otherPlayer;
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return "nan";
        } 
        
    }
    String getPlayertoPlayAgain(String player)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select gameId from players where username= '"+player+"' and isPlaying = 'yes'");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next(); 
            String gameId = rs.getString(1);
            
            stm = con.createStatement();
            String queryString2 = new String("select username from players where gameId= "+gameId+" "
                    + "and username != '"+player+"'");
            ResultSet rs2 = stm.executeQuery(queryString2);
            rs2.next(); 
            String otherPlayer = rs2.getString(1);
            
            stm = con.createStatement();
            String queryString3 = new String("select playAgain from players where gameId= "+gameId+" "
                    + "and username = '"+otherPlayer+"'");
            ResultSet rs3 = stm.executeQuery(queryString3);
            rs3.next(); 
            String otherPlayerStatus = rs3.getString(1);
            if(new String(otherPlayerStatus).equals("true"))
            {
                return otherPlayer;
            }
            else
            {
                return "playerNotReady";
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return "nan";
        }
        
    }
    void setThreadNumber(String userName, String threadNumber)
    {
        try
        {
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET threadNumber = "+threadNumber+""
                    + " WHERE username= '"+userName+"'");
            stm.execute(queryStringUpdate); 
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            
        } 
    }
    String resetUserDataUsingThreadNumber(int threadNumber)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select username from players where threadNumber= "+threadNumber+"");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next(); 
            String userName = rs.getString(1);
            
            stm= con.createStatement();
            String queryStringUpdate = new String("UPDATE players SET status = 'offline', isPlaying = 'no', "
                    + "gameId = null, playAgain = 'false' where threadNumber = "+threadNumber+"");
            stm.execute(queryStringUpdate); 
            
            stm= con.createStatement();
            String queryStringUpdate1 = new String("UPDATE players SET threadNumber = null "
                    + "where threadNumber = "+threadNumber+"");
            stm.execute(queryStringUpdate1); 
            return userName;
        }
        catch(SQLException ex)
        {
            //ex.printStackTrace();
            System.out.println("No user loged in or registered");
            return "nan";
            
        } 
        
    }
    void saveVectorIndex(String gameId, String vectorIndex)
    {
        try
        {
            stm = con.createStatement();
            String queryStringInsert = new 
                String("insert into gameIdVectorIndex values ("+gameId+","+vectorIndex+")");
            stm.execute(queryStringInsert);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            
        }       
    }
    String getVectorIndex (String gameId)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select vIndex from gameIdVectorIndex where gId= "+gameId+"");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next(); 
            String vectorIndex = rs.getString(1);
            return vectorIndex;
        }
        catch(SQLException ex)
        {
            //ex.printStackTrace();
            System.out.println("No user loged in or registered");
            return "nan";
        } 
    }
    String getRoomIdByPlayer(String player)
    {
        try
        {
            stm = con.createStatement();
            String queryString = new String("select gameId from players where username= '"+player+"'");
            ResultSet rs = stm.executeQuery(queryString);
            rs.next(); 
            String vectorIndex = rs.getString(1);
            return vectorIndex;
        }
        catch(SQLException ex)
        {
            //ex.printStackTrace();
            System.out.println("No user loged in or registered");
            return "nan";
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
