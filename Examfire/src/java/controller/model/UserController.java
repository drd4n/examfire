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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Dan
 */
public class UserController {
    private final static String FIND_BY_USERNAME = "select * from users where username = ?";
    private final static String REGISTER= "insert into users (username, password, userfullname, email) values (?, ?, ?, ?);";
    
    static User CastResultSetToUser(ResultSet rs) {
        try {
            User usr = new User(rs.getInt("USERID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("USERFULLNAME"), rs.getString("EMAIL"));
            if (usr.getUserId() != 0) {
                return usr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public User findByUsername(String username){
        User usr = null;
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(FIND_BY_USERNAME);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                usr = CastResultSetToUser(rs);
            }
            rs.close();
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usr;
}
    public void RegisterUser(User user){
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(REGISTER);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserFullName());
            pst.setString(4, user.getEmail());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
