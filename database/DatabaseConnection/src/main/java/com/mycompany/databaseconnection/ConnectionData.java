/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.databaseconnection;

/**
 *
 * @author ANDS
 */
public class ConnectionData {
    
    private static String link = "rp2.studenti.math.hr";
    private static String username = "student";
    private static String password = "pass.mysql";

    public static String getLink() {
        return link;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }
    
}
