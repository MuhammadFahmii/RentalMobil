/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.controller;

import MVC.implement.MobilDaoImpl;
import MVC.model.mobil.MobilModel;
import MVC.model.mobil.TableMobilModel;
import MVC.model.transaksi.TableTransaksiModel;
import MVC.service.MobilDao;
import MVC.view.MobilView;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class MobilController {
    private final MobilModel model;
    private final MobilView view;
    private final MobilDao dao;
    private List<MobilModel> list;
    
    public MobilController(MobilView view) throws SQLException, IOException {
        this.model = new MobilModel();
        this.dao = new MobilDaoImpl();
        this.view = view;
        this.list = dao.getAllMobil();
    }
    
    public void getAllMobil(){
        list = dao.getAllMobil();
        TableMobilModel tableModel = new TableMobilModel(list);
        view.getTblMobil().setModel(tableModel);
    }
  
    public void fillTableByTable(int row){
        view.getTxtIdMobil().setText(list.get(row).getId_mobil().toString());
        view.getTxtMerek().setText(list.get(row).getMerek());
        view.getTxtTipe().setText(list.get(row).getTipe());
        view.getTxtTahun().setText(list.get(row).getTahun().toString());
        view.getTxtNopol().setText(list.get(row).getNopol());
        view.getTxtHarga().setText(list.get(row).getHarga().toString());
        view.getTxtStatus().setText(list.get(row).getStatus());
    }
    
    public void insertMobil(MobilView view) {
        String merek = view.getTxtMerek().getText();
        String tipe = view.getTxtTipe().getText();
        int tahun = Integer.parseInt(view.getTxtTahun().getText());
        String nopol = view.getTxtNopol().getText();
        int harga = Integer.parseInt(view.getTxtHarga().getText());
        String status = view.getTxtStatus().getText();

        model.setMerek(merek);
        model.setTipe(tipe);
        model.setTahun(tahun);
        model.setNopol(nopol);
        model.setHarga(harga);
        model.setStatus(status);
        
        try {
            dao.insertMobil(model);
            JOptionPane.showMessageDialog(view, "Mobil Berhasil Ditambahkan");
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMobil(MobilView view) {

        //jika tidak ada yang diseleksi kasih peringatan
        if (view.getTblMobil().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan Seleksi baris data yang akan diubah");
            return;
        }

        String merek = view.getTxtMerek().getText();
        String tipe = view.getTxtTipe().getText();
        int tahun = Integer.parseInt(view.getTxtTahun().getText());
        String nopol = view.getTxtNopol().getText();
        int harga = Integer.parseInt(view.getTxtHarga().getText());
        String status = view.getTxtStatus().getText();
        int idMobil = Integer.parseInt(view.getTxtIdMobil().getText());

        model.setId_mobil(idMobil);
        model.setMerek(merek);
        model.setTipe(tipe);
        model.setTahun(tahun);
        model.setNopol(nopol);
        model.setHarga(harga);
        model.setStatus(status);
        try {
            dao.updateMobil(model);
            JOptionPane.showMessageDialog(view, "Data Mobil Berhasil Di Ubah");
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteMobil(MobilView view) {
        //jika tidak ada yang diseleksi kasih peringatan
        if (view.getTblMobil().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan Seleksi baris data yang akan dihapus");
            return;
        }

        if (JOptionPane.showConfirmDialog(view, "Anda yakin akan menghapus?")
                == JOptionPane.OK_OPTION) {

            int idMobil = Integer.parseInt(view.getTxtIdMobil().getText());
            model.setId_mobil(idMobil);

            try {
                dao.deleteMobil(Integer.parseInt(view.getTxtIdMobil().getText()));
                JOptionPane.showMessageDialog(view, "Data Mobil Berhasil Di Hapus");
            } catch (SQLException ex) {
                Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reset(){
        view.getTxtIdMobil().setText("");
        view.getTxtMerek().setText("");
        view.getTxtTipe().setText("");
        view.getTxtTahun().setText("");
        view.getTxtNopol().setText("");
        view.getTxtHarga().setText("");
        view.getTxtStatus().setText("");
    }
}
