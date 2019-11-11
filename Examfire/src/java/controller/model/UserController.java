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
import model.User;

/**
 *
 * @author Dan
 */
public class UserController {
    private final static String FIND_BY_ID = "select * from users where userid = ?";
    private final static String FIND_BY_USERNAME = "select * from users where username = ?";
    
    public User findById(int id){
        User usr = null;
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(FIND_BY_ID);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
        }catch{
            
        }
}
}
