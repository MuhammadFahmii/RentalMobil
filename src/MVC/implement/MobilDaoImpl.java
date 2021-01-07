/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.implement;

import MVC.db.koneksiDB;
import MVC.model.mobil.MobilModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import MVC.service.MobilDao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class MobilDaoImpl implements MobilDao{

    private Connection conn = null;
    private final String insertMobil = "INSERT INTO tb_mobil (merek, tipe, tahun, nopol, harga, status) VALUES(?, ?, ?, ?, ?, ?)";
    private final String updateMobil = "UPDATE tb_mobil SET merek=?, tipe=?, tahun=?, nopol=?, harga=?, status=? WHERE id_mobil=?";
    private final String deleteMobil = "DELETE FROM tb_mobil WHERE id_mobil=?";
    private final String selectAll = "SELECT * FROM tb_mobil";

    public MobilDaoImpl(){
        try {
            this.conn = koneksiDB.getConnection();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(MobilDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void insertMobil(MobilModel mobil) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(insertMobil, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, mobil.getMerek());
            statement.setString(2, mobil.getTipe());
            statement.setInt(3, mobil.getTahun());
            statement.setString(4, mobil.getNopol());
            statement.setInt(5, mobil.getHarga());
            statement.setString(6, mobil.getStatus());
            statement.executeUpdate();
            ResultSet result  = statement.getGeneratedKeys();
            if (result.next()) {
                mobil.setId_mobil(result.getInt(1));
            }
        } catch (SQLException ex) {
        }
    }

    @Override
    public void updateMobil(MobilModel mobil) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(updateMobil);
            statement.setString(1, mobil.getMerek());
            statement.setString(2, mobil.getTipe());
            statement.setInt(3, mobil.getTahun());
            statement.setString(4, mobil.getNopol());
            statement.setInt(5, mobil.getHarga());
            statement.setString(6, mobil.getStatus());
            statement.setInt(7, mobil.getId_mobil());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteMobil(Integer id_mobil) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(deleteMobil);
            statement.setInt(1, id_mobil);
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            
        }
    }

    @Override
    public List<MobilModel> getAllMobil() {
        Statement statement = null;
        List<MobilModel> list = new ArrayList<>();
        try {
            statement = conn.createStatement();

            ResultSet result = statement.executeQuery(selectAll);
            while (result.next()) {
                MobilModel mobil = new MobilModel();
                mobil.setId_mobil(result.getInt("id_mobil"));
                mobil.setMerek(result.getString("merek"));
                mobil.setTipe(result.getString("tipe"));
                mobil.setTahun(result.getInt("tahun"));
                mobil.setNopol(result.getString("nopol"));
                mobil.setHarga(result.getInt("harga"));
                mobil.setStatus(result.getString("status"));
                list.add(mobil);
            }
            return list;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
    
}
