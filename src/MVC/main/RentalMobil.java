/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.main;

import MVC.view.MainView;
import java.io.IOException;
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
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sql = new java.sql.Date(utilDate.getTime());
            System.out.println(sql);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(main, ex.getMessage(), "Database Tidak Ditemukan", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(main, ex.getMessage(), "Path Konfig DB tidak ditemukan", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } 
    }
}
