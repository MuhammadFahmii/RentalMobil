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
    private final SimpleDateFormat sdf;
    private TransaksiDao dao = null;
    private List<TransaksiModel> list;
    
    
    public TransaksiController(TransaksiView view) throws SQLException, IOException{
        // untuk konversi waktu ke Format (yyyy-MM-dd);
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.model = new TransaksiModel();
        this.view = view;
        this.dao = new TransaksiDaoImpl();
    }
    
    /**
     * Untuk mengambil semua data transaksi pada tabel Transaksi
     */
    public void getAllTransaksi(){
        try {
            list = dao.getAllTransaksi();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Gagal mengambil data", "Failed", JOptionPane.ERROR_MESSAGE);
        }
        TableTransaksiModel tableModel = new TableTransaksiModel(list);
        view.getTblTransaksi().setModel(tableModel);
    }
    
    /**
     Untuk insertTransaksi ke table transaksi sekaligus update status table mobil menjadi "Keluar"
     * @return 
     */
    public boolean insertTransaksi(){
        // Validasi
        if(view.getTxtIdMobil().getText().isEmpty()|| view.getTxtLama().getText().isEmpty() 
                || view.getTxtTotal().getText().isEmpty() || view.getTxtNamaPeminjam().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pastikan semua data terisi", "Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(view.getTxtStatus().getText().toUpperCase().equals("KELUAR")){
            JOptionPane.showMessageDialog(view, "Mobil Sedang Keluar", "Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        this.setAtributeModel("insert");
        try {
            dao.insertTransaksi(model, Integer.parseInt(view.getTxtIdMobil().getText()));
            JOptionPane.showMessageDialog(view, "Data Mobil Berhasil Ditambah");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Data Mobil Gagal Ditambah");
            return false;
        }
    }
    
    /**
     * Untuk update data transaksi 
     */
    public void updateTransaksi(){
        // Validasi
        if(view.getTxtIdTransaksi().getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Data transaksi tidak ditemukan", "Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.setAtributeModel("update");
        try {
            dao.updateTransaksi(model);
            JOptionPane.showMessageDialog(view, "Data Berhasil Diubah");
        } catch (HeadlessException | SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Untuk set model attibute
     * @param method 
     */
    private void setAtributeModel(String method){
        model.setIdMobil(Integer.parseInt(view.getTxtIdMobil().getText()));
        model.setPeminjam(view.getTxtNamaPeminjam().getText());
        model.setHarga(view.getTxtHarga().getText());
        model.setTgl_pinjaman(sdf.format(view.getTglPinjam().getDate()));
        model.setTgl_kembali(sdf.format(view.getTglKembali().getDate()));
        model.setLama(view.getTxtLama().getText());
        model.setTotal(view.getTxtTotal().getText());
        if(method.equals("update")) model.setIdTransaksi(Integer.parseInt(view.getTxtIdTransaksi().getText()));
    }
    
    /**
     * Untuk delete data transaksi ke table transaksi sekaligus update status table mobil menjadi "Tersedia"
     */
    public void deleteTransaksi(){
        // Validasi
        if(view.getTxtIdTransaksi().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Silahkan pilih mobil terlebih dahulu", "Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int idTransaksi = Integer.parseInt(view.getTxtIdTransaksi().getText());
            int idMobil = Integer.parseInt(view.getTxtIdMobil().getText());
            if( JOptionPane.showConfirmDialog(view, "Apakah Anda Yakin?") == JOptionPane.OK_OPTION){
                dao.deleteTransaksi(idTransaksi, idMobil);
                JOptionPane.showMessageDialog(view, "Data Mobil Berhasil Dikembalikan", "Success", JOptionPane.INFORMATION_MESSAGE);    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Data Mobil Gagal Dikembalikan", "Failed", JOptionPane.ERROR_MESSAGE);    
        }
    }
    
    /**
     * Untuk reset textfield
     */
    public void reset(){
        view.getTxtIdTransaksi().setText("");
        view.getTxtIdMobil().setText("");
        view.getTxtNamaPeminjam().setText("");
        view.getTxtHarga().setText("");
        view.getTglPinjam().setCalendar(null);
        view.getTglKembali().setCalendar(null);
        view.getTxtLama().setText("");
        view.getTxtTotal().setText("");
        view.getCmbNoPol().setSelectedIndex(-1);
        view.getTxtMerek().setText("");
        view.getTxtTipe().setText("");
        view.getTxtStatus().setText("");
        view.getCmbNoPol().setEnabled(true);
        view.getTglKembali().setEnabled(true);
        view.getTglPinjam().setEnabled(true);
    }
    
    /**
     * Untuk mendapatkan no polisi pada table mobil
     */
    public void getNopol(){
        try {
            ResultSet rs = dao.querySelect("tb_mobil");
            while(rs.next()){
                view.getCmbNoPol().addItem(rs.getString("nopol"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Gagal mengambil no polisi", "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Untuk mengisi textfield ketika nopol combo box ditekan
     * @param nopol 
     */
    public void fillField(String nopol){
        ResultSet rs = null;
        view.getTxtNamaPeminjam().requestFocus();
        try {
            rs = dao.querySelect("id_mobil, harga, tipe, merek, status", "tb_mobil", "nopol", nopol);
            while(rs.next()){
                view.getTxtIdMobil().setText(String.valueOf(rs.getInt("id_mobil")));
                view.getTxtHarga().setText(rs.getString("harga"));
                view.getTxtMerek().setText(rs.getString("merek"));
                view.getTxtTipe().setText(rs.getString("tipe"));
                view.getTxtStatus().setText(rs.getString("status"));
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
        view.getTxtNamaPeminjam().setText(String.valueOf(list.get(row).getPeminjam()));
        view.getTxtHarga().setText(String.valueOf(list.get(row).getHarga()));
        view.getTxtLama().setText(String.valueOf(list.get(row).getLama()));
        view.getTxtTotal().setText(String.valueOf(list.get(row).getTotal()));
        view.getTxtIdMobil().setEditable(false);
        view.getTglKembali().setEnabled(false);
        view.getTglPinjam().setEnabled(false);
        view.getCmbNoPol().setEnabled(false);
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
