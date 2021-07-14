/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

/**
 *
 * @author ANDS
 */
public class ConnectionData {
    
    private static final String LINK = "jdbc:mysql://rp2.studenti.math.hr?characterEncoding=utf-8";    
    private static final String USERNAME = "student";
    private static final String PASSWORD = "pass.mysql";

    public static String getLink() {
        return LINK;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getUsername() {
        return USERNAME;
    }
    
}
