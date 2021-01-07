/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.implement;

import MVC.db.koneksiDB;
import MVC.model.transaksi.TransaksiModel;
import MVC.service.TransaksiDao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Heinz
 */
public class TransaksiDaoImpl implements TransaksiDao{
    private Connection conn = null;
    private String insertTransaksi = "INSERT INTO tb_transaksi (id_mobil, peminjam, harga, tgl_pinjaman, tgl_kembali, lama, total) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private String updateTransaksi = "UPDATE tb_transaksi SET id_mobil=?, peminjam=?, harga=?, tgl_pinjaman=?, tgl_kembali=?, lama=?, total=? WHERE id_transaksi=?";
    private String deleteTransaksi = "DELETE FROM tb_transaksi WHERE id_transaksi = ?";
    private final String selectAll = "SELECT * FROM tb_transaksi";
    private final String selectAllMobil = "SELECT * FROM tb_mobil";

    public TransaksiDaoImpl() {
        try {
            this.conn = koneksiDB.getConnection();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TransaksiDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ResultSet getNopol(){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectAllMobil);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public void insertTransaksi(TransaksiModel transaksi) throws SQLException  {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(insertTransaksi, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, transaksi.getIdMobil());
            stmt.setString(2, transaksi.getPeminjam());
            stmt.setString(3, transaksi.getHarga());
            stmt.setString(4, transaksi.getTgl_pinjaman());
            stmt.setString(5, transaksi.getTgl_kembali());
            stmt.setString(6, transaksi.getLama());
            stmt.setString(7, transaksi.getTotal());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()){
                transaksi.setIdTransaksi(1);
            }
        } finally{
           stmt.close();
        }
        
    }

    @Override
    public void updateTransaksi(TransaksiModel transaksi) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(updateTransaksi);
            stmt.setInt(1, transaksi.getIdMobil());
            stmt.setString(2, transaksi.getPeminjam());
            stmt.setString(3, transaksi.getHarga());
            stmt.setString(4, transaksi.getTgl_pinjaman());
            stmt.setString(5, transaksi.getTgl_kembali());
            stmt.setString(6, transaksi.getLama());
            stmt.setString(7, transaksi.getTotal());
            stmt.setInt(8, transaksi.getIdTransaksi());
            stmt.executeUpdate();
        } finally  {
            stmt.close();
        }
    }

    @Override
    public void deleteTransaksi(int id_transaksi) throws SQLException{
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(deleteTransaksi);
            stmt.setInt(1, id_transaksi);
            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

    @Override
    public List<TransaksiModel> getAllTransaksi() {
        Statement stmt = null;
        List<TransaksiModel> list = new ArrayList<>();
        try{
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(selectAll);
            while(result.next()){
                TransaksiModel transaksi = new TransaksiModel();
                transaksi.setIdTransaksi(result.getInt("id_transaksi"));
                transaksi.setIdMobil(result.getInt("id_mobil"));
                transaksi.setPeminjam(result.getString("peminjam"));
                transaksi.setHarga(result.getString("harga"));
                transaksi.setTgl_pinjaman(result.getString("tgl_pinjaman"));
                transaksi.setTgl_kembali(result.getString("tgl_kembali"));
                transaksi.setLama(result.getString("lama"));
                transaksi.setTotal(result.getString("total"));
                list.add(transaksi);
            }
            return list;
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public ResultSet querySelectKolom(String namaKolom, String namaTable, String kondisi, String value) throws SQLException{
        String SQL = "SELECT "+ namaKolom +" FROM "+ namaTable + " WHERE " + kondisi + "='" + value + "'";
        System.out.println(SQL);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
