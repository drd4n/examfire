/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.model;

import connection.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Exam;
import model.Users;

/**
 *
 * @author Dan
 */
public class ScoreController {
    String Find_By_Userid_Examid = "select * from EXAMFIRE.SCORE where USERID = ? and EXAMID = ?";
    String Save_Score = "insert into EXAMFIRE.SCORE(USERID, USERSCORE,EXAMID) values(?,?,?)";
    
    public ArrayList<Integer> findByUseridAndExamid(Users user, Exam exam){
       ArrayList<Integer> scores = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(Find_By_Userid_Examid);
            pst.setInt(1, user.getUserid());
            pst.setInt(2, exam.getExamid());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                    scores.add(rs.getInt("USERSCORE"));
                }
                pst.close();
                con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return scores;
}
    
    public void saveScore(int userid,int userscore,int examid){
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst= con.prepareStatement(Save_Score);
            pst.setInt(1, userid);
            pst.setInt(2, userscore);
            pst.setInt(3, examid);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
