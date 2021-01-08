/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.controller;

import MVC.model.transaksi.TransaksiModel;
import MVC.implement.TransaksiDaoImpl;
import MVC.model.transaksi.TableTransaksiModel;
import MVC.service.TransaksiDao;
import MVC.view.TransaksiView;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Heinz
 */
public class TransaksiController {
    private final TransaksiModel model;
    private final TransaksiView view;
    // untuk konversi waktu ke Format (yyyy-MM-dd);
    private final SimpleDateFormat sdf;
    private TransaksiDao dao = null;
    private List<TransaksiModel> list;
    
    
    public TransaksiController(TransaksiView view){
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.model = new TransaksiModel();
        this.view = view;
        try {
            this.dao = new TransaksiDaoImpl();
            this.list = dao.getAllTransaksi();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk mengambil semua data transaksi pada tabel
     */
    public void getAllTransaksi(){
        list = dao.getAllTransaksi();
        TableTransaksiModel tableModel = new TableTransaksiModel(list);
        view.getTblTransaksi().setModel(tableModel);
    }
    
    /**
     Untuk insertTransaksi ke table transaksi sekaligus update status table mobil
     */
    public void insertTransaksi(){
        if(view.getTxtIdMobil().getText().isEmpty() || view.getTxtHarga().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pastikan semua data terisi");
        }
        model.setIdMobil(Integer.parseInt(view.getTxtIdMobil().getText()));
        model.setPeminjam(view.getTxtPeminjam().getText());
        model.setHarga(view.getTxtHarga().getText());
        model.setTgl_pinjaman(sdf.format(view.getTglPinjam().getDate()));
        model.setTgl_kembali(sdf.format(view.getTglKembali().getDate()));
        model.setLama(view.getTxtLama().getText());
        model.setTotal(view.getTxtTotal().getText());
        
        try {
            dao.insertTransaksi(model, Integer.parseInt(view.getTxtIdMobil().getText()));
            JOptionPane.showMessageDialog(view, "Data Berhasil Ditambah");
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTransaksi(){
        model.setIdMobil(Integer.parseInt(view.getTxtIdMobil().getText()));
        model.setPeminjam(view.getTxtPeminjam().getText());
        model.setHarga(view.getTxtHarga().getText());
        model.setTgl_pinjaman(sdf.format(view.getTglPinjam().getDate()));
        model.setTgl_kembali(sdf.format(view.getTglKembali().getDate()));
        model.setLama(view.getTxtLama().getText());
        model.setTotal(view.getTxtTotal().getText()); 
        model.setIdTransaksi(Integer.parseInt(view.getTxtIdTransaksi().getText()));
        try {
            dao.updateTransaksi(model);
            JOptionPane.showMessageDialog(view, "Data Berhasil Diubah");
        } catch (HeadlessException | SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk delete data transaksi ke table transaksi sekaligus update status table mobil
     */
    public void deleteTransaksi(){
        try {
            if(view.getTxtIdTransaksi().getText().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Silahkan pilih mobil terlebih dahulu", "Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idTransaksi = Integer.parseInt(view.getTxtIdTransaksi().getText());
            int idMobil = Integer.parseInt(view.getTxtIdMobil().getText());
            if( JOptionPane.showConfirmDialog(view, "Apakah Anda Yakin?") == JOptionPane.OK_OPTION){
                dao.deleteTransaksi(idTransaksi, idMobil);
                JOptionPane.showMessageDialog(view, "Data Berhasil Dihapus");    
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk reset textfield
     */
    public void reset(){
        view.getTxtIdTransaksi().setText("");
        view.getTxtIdMobil().setText("");
        view.getTxtPeminjam().setText("");
        view.getTxtHarga().setText("");
        view.getTglPinjam().setCalendar(null);
        view.getTglKembali().setCalendar(null);
        view.getTxtLama().setText("");
        view.getTxtTotal().setText("");
    }
    
    /**
     * Untuk mendapatkan no polisi pada table mobil
     */
    public void getNopol(){
        ResultSet rs = dao.querySelect("tb_mobil");
        try {
            while(rs.next()){
                view.getCmbNoPol().addItem(rs.getString("nopol"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk isi text field ketika nopol combo box ditekan
     * @param nopol 
     */
    public void fillField(String nopol){
        ResultSet rs = null;
        try {
            rs = dao.querySelect("id_mobil, harga, tipe, merek", "tb_mobil", "nopol", nopol);
            while(rs.next()){
                view.getTxtIdMobil().setText(String.valueOf(rs.getInt("id_mobil")));
                view.getTxtHarga().setText(rs.getString("harga"));
                view.getTxtMerek().setText(rs.getString("merek"));
                view.getTxtTipe().setText(rs.getString("tipe"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk mengisi textField ketika table ditekan
     * @param row 
     */
    public void fillFieldByTable(int row){
        ResultSet rs = null;
        view.getTxtIdTransaksi().setText(String.valueOf(list.get(row).getIdTransaksi()));
        view.getTxtIdMobil().setText(String.valueOf(list.get(row).getIdMobil()));
        view.getTxtPeminjam().setText(String.valueOf(list.get(row).getPeminjam()));
        view.getTxtHarga().setText(String.valueOf(list.get(row).getHarga()));
        view.getTxtLama().setText(String.valueOf(list.get(row).getLama()));
        view.getTxtTotal().setText(String.valueOf(list.get(row).getTotal()));
        view.getTxtIdMobil().setEditable(false);
        view.getCmbNoPol().setEditable(false);
        try {
            rs = dao.querySelect("nopol", "tb_mobil", "id_mobil", String.valueOf(list.get(row).getIdMobil()));
            while(rs.next()){
                view.getCmbNoPol().setSelectedItem(rs.getString("nopol"));
            }
            view.getTglPinjam().setDate(sdf.parse(list.get(row).getTgl_pinjaman()));
            view.getTglKembali().setDate(sdf.parse(list.get(row).getTgl_kembali()));
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
