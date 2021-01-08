/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.implement;

import MVC.db.koneksiDB;
import MVC.model.transaksi.TransaksiModel;
import MVC.model.mobil.MobilModel;
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
    private final String updateTransaksi = "UPDATE tb_transaksi SET id_mobil=?, peminjam=?, harga=?, tgl_pinjaman=?, tgl_kembali=?, lama=?, total=? WHERE id_transaksi=?";
    private final String deleteTransaksi = "DELETE FROM tb_transaksi WHERE id_transaksi = ?";
    private final String selectAll = "SELECT * FROM tb_transaksi";

    public TransaksiDaoImpl() throws SQLException, IOException{
       this.conn = koneksiDB.getConnection();
    }
       
    
    /*
     * Query untuk insert data ke table Transaksi
     */
    @Override
    public void insertTransaksi(TransaksiModel transaksi, int idMobil) throws SQLException  {
        String SQL = "INSERT INTO tb_transaksi (id_mobil, peminjam, harga, tgl_pinjaman, tgl_kembali, lama, total) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
            this.updateStatus(idMobil, "Keluar");
        } finally{
           stmt.close();
        }
        
    }

    /**
     * @param transaksi
     * @throws SQLException 
     */
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
    public void deleteTransaksi(int id_transaksi, int idMobil) throws SQLException{
        PreparedStatement stmt1 = null;
        try {
            stmt1 = conn.prepareStatement(deleteTransaksi);
            stmt1.setInt(1, id_transaksi);
            stmt1.executeUpdate();
            this.updateStatus(idMobil, "Tersedia");
        } finally {
            stmt1.close();
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
    
    @Override
    public ResultSet querySelect(String namaTable){
        String selectAllMobil = "SELECT * FROM tb_mobil";

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
     * Update status mobil di table mobil menjadi Keluar
     * @param idMobil
     * @throws SQLException 
     */
    public void updateStatus(int idMobil, String status) throws SQLException{
        MobilModel mobil = new MobilModel();
        final String getMobil = "SELECT * FROM tb_mobil WHERE id_mobil = ?";
        final String updateMobil = "UPDATE tb_mobil SET merek=?, tipe=?, tahun=?, nopol=?, harga=?, status=? WHERE id_mobil=?";
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
            stmt1 = conn.prepareStatement(getMobil);
            stmt1.setInt(1, idMobil);
            ResultSet rs = stmt1.executeQuery();
            while(rs.next()){
                mobil.setId_mobil(rs.getInt("id_mobil"));
                mobil.setId_mobil(rs.getInt("id_mobil"));
                mobil.setMerek(rs.getString("merek"));
                mobil.setTipe(rs.getString("tipe"));
                mobil.setTahun(rs.getInt("tahun"));
                mobil.setNopol(rs.getString("nopol"));
                mobil.setHarga(rs.getInt("harga"));
                mobil.setStatus(status);
            }
            stmt2 = conn.prepareStatement(updateMobil);
            stmt2.setString(1, mobil.getMerek());
            stmt2.setString(2, mobil.getTipe());
            stmt2.setInt(3, mobil.getTahun());
            stmt2.setString(4, mobil.getNopol());
            stmt2.setInt(5, mobil.getHarga());
            stmt2.setString(6, mobil.getStatus());
            stmt2.setInt(7, mobil.getId_mobil());
            stmt2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stmt1.close();
            stmt2.close();
        }
    }
}
