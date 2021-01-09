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

    public TransaksiDaoImpl() throws SQLException, IOException{
       this.conn = koneksiDB.getConnection();
    }
       
    /**
     * Query untuk insert data ke table Transaksi
     */
    @Override
    public void insertTransaksi(TransaksiModel transaksi) throws SQLException  {
        String SQL = "INSERT INTO tb_transaksi (id_mobil, peminjam, harga, tgl_pinjaman, tgl_kembali, lama, total) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, transaksi.getIdMobil());
            stmt.setString(2, transaksi.getPeminjam());
            stmt.setInt(3, transaksi.getHarga());
            stmt.setString(4, transaksi.getTgl_pinjaman());
            stmt.setString(5, transaksi.getTgl_kembali());
            stmt.setString(6, transaksi.getLama());
            stmt.setString(7, transaksi.getTotal());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()){
                transaksi.setIdTransaksi(1);
            }
            this.updateStatus(transaksi.getIdMobil(), "Keluar");
        } finally{
           stmt.close();
        }
        
    }

    /**
     * Query untuk update transaksi
     * @param transaksi
     * @throws SQLException 
     */
    @Override
    public void updateTransaksi(TransaksiModel transaksi) throws SQLException {
        String SQL = "UPDATE tb_transaksi SET id_mobil=?, peminjam=?, harga=?, tgl_pinjaman=?, tgl_kembali=?, lama=?, total=? WHERE id_transaksi=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQL);
            stmt.setInt(1, transaksi.getIdMobil());
            stmt.setString(2, transaksi.getPeminjam());
            stmt.setInt(3, transaksi.getHarga());
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

    /**
     * Query untuk delete Transaksi
     * @param id_transaksi
     * @param idMobil
     * @throws SQLException 
     */
    @Override
    public void deleteTransaksi(TransaksiModel transaksi) throws SQLException{
        String SQL = "DELETE FROM tb_transaksi WHERE id_transaksi = ?";

        PreparedStatement stmt1 = null;
        try {
            stmt1 = conn.prepareStatement(SQL);
            stmt1.setInt(1, transaksi.getIdMobil());
            stmt1.executeUpdate();
            this.updateStatus(transaksi.getIdMobil(), "Tersedia");
        } finally {
            stmt1.close();
        }
    }

    /**
     * Query untuk ambil data transaksi
     * @return 
     */
    @Override
    public List<TransaksiModel> getAllTransaksi() throws SQLException{
        String SQL = "SELECT * FROM tb_transaksi";
        Statement stmt = null;
        List<TransaksiModel> list = new ArrayList<>();
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(SQL);
        while(result.next()){
            TransaksiModel transaksi = new TransaksiModel();
            transaksi.setIdTransaksi(result.getInt("id_transaksi"));
            transaksi.setIdMobil(result.getInt("id_mobil"));
            transaksi.setPeminjam(result.getString("peminjam"));
            transaksi.setHarga(result.getInt("harga"));
            transaksi.setTgl_pinjaman(result.getString("tgl_pinjaman"));
            transaksi.setTgl_kembali(result.getString("tgl_kembali"));
            transaksi.setLama(result.getString("lama"));
            transaksi.setTotal(result.getString("total"));
            list.add(transaksi);
        }
        return list;
    }

    /**
     * Query Select berdasarkan param
     * @param namaKolom
     * @param namaTable
     * @param kondisi
     * @param value
     * @return
     * @throws SQLException 
     */
    @Override
    public ResultSet querySelect(String namaKolom, String namaTable, String kondisi , String value) throws SQLException{
        String SQL = "SELECT "+ namaKolom +" FROM "+ namaTable + " WHERE " + kondisi + "='" + value + "'";
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
    
    /**
     * Query Select table
     * @param namaTable
     * @return 
     */
    @Override
    public ResultSet querySelect(String namaTable){
        String selectAllMobil = "SELECT * FROM " + namaTable;
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
    
    /**
     * Update status mobil di table mobil menjadi sesuai status
     * @param idMobil
     * @param status
     * @throws SQLException 
     */
    public void updateStatus(int idMobil, String status) throws SQLException{
        final String updateMobil = "UPDATE tb_mobil SET status=? WHERE id_mobil=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(updateMobil);
            stmt.setString(1, status);
            stmt.setInt(2, idMobil);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stmt.close();
        }
    }
}
