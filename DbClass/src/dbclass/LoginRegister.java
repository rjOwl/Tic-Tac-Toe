/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclass;

/**
 *
 * @author hradw
 */
public class LoginRegister {
    
    public boolean login(DbClass obj, String userName, String passwd)
    {
        if(obj.checkIfuserExist(userName))
        {
            return obj.checkUserPassword(userName, passwd); 
        }
        else
        {
            return false;
        }
    }
    
    public boolean register(DbClass obj, String userName, String password)
    {
        if(obj.checkIfuserExist(userName))
        {
            return false; //user already exist;
        }
        else
        {
            obj.insertInPlayers(userName, password, "0", "0", "0", "online", "no");
            return true;
        }
    }
    public static void main(String[] args)
    {
        LoginRegister l1 = new LoginRegister();
        
        DbClass d1 = new DbClass("localhost","3306","tictactoe","root","12345");
        if(l1.login(d1, "user3","mkjnk"))
        {
            System.out.println("successful login");
        }
        else
        {
            System.out.println("error, wrong username or passwd");
        }
        if(l1.login(d1, "user3","12365"))
        {
            System.out.println("successful login");
        }
        else
        {
            System.out.println("error, wrong username or passwd");
        }
        if(l1.login(d1, "user100","12365"))
        {
            System.out.println("successful login");
        }
        else
        {
            System.out.println("error, wrong username or passwd");
        }
//        if(l1.register(d1, "user3","12345"))
//        {
//            System.out.println("registeration completed");
//        }
//        else
//        {
//            System.out.println("user already exist");
//        }
//        if(l1.register(d1, "user10","12525"))
//        {
//            System.out.println("registeration completed");
//        }
//        else
//        {
//            System.out.println("user already  exist");
//        }
        //d1.deleteFromTable("players", "player_id", "5");
        //d1.deleteFromTable("game", "game_id", "1");
        //d1.insertInPlayers("user4", "mkjnk", "10", "0", "6", "online", "no");
        //d1.insertInGame("2", "4", "3");
        d1.selectFromDB("players");
        System.out.print("Scoreboard: ");
        d1.showScoreBoard();
        //d1.updateScore("user1", "win");
        //System.out.print("Scoreboard: ");
        //d1.showScoreBoard();
        System.out.println("status is: "+d1.checkPlayer("1","status"));
        System.out.println("is playing? "+d1.checkPlayer("1","isPlaying"));
        d1.checkIfuserExist("user3");
        d1.checkIfuserExist("user5");
        d1.closeConnection();
    } 
    
}
