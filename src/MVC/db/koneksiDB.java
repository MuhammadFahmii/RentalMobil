/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.db;
import com.mysql.jdbc.Connection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class koneksiDB {
    //inisialisasi variabel dengan Connection file class JDBC
    private static Connection conn;    
    //inisialisasi fungsi class properties untuk memanggil file konfig database
    private static final Properties propert = new Properties();
    
    /**
     * Inisialisasi DataAccessObj
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.io.FileNotFoundException 
     */
    
    //method konek ke database
    public static Connection getConnection() throws SQLException, IOException{
        //cek koneksi
        if(conn == null){
            propert.load(new FileInputStream("C:\\Users\\Heinz\\Documents\\NetBeansProjects\\RentalMobil\\src\\MVC\\db\\konfigDB.properties"));
            conn = (Connection) DriverManager.getConnection(propert.getProperty("jdbc.url"),propert.getProperty("jdbc.username"),propert.getProperty("jdbc.password"));
        }
        return conn;
    }
}
