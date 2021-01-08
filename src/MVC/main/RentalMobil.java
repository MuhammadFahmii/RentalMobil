/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.main;

import MVC.view.MainView;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class RentalMobil {
    public static void main(String[] args) { 
        MainView main = null;
        try {
            main = new MainView();
            main.setVisible(true);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(main, "Database Tidak Ditemukan");
            System.exit(0);
        }
    }
}
