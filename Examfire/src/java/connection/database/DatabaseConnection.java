/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
    //เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้
/**
 *
 * @author Dan
 */
public class DatabaseConnection {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    //เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้

/**
 *
 * @author tisanai
 */
    //เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String URL = "jdbc:derby://localhost:1527/examfire";
    private static final String USERNAME = "examfire";
    private static final String PASSWORD = "examfire";
    //เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }//เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้

}
    //เผื่อได้ใช้ เผื่อบาง function ทำไม่ได้จริงๆค่อยใช้

