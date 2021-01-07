/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model.transaksi;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Heinz
 */
public class TableTransaksiModel extends AbstractTableModel{
    private List<TransaksiModel> list = new ArrayList<>();
    
    public TableTransaksiModel(List<TransaksiModel> list){
        this.list = list;
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "ID Transaksi";
            case 1:
                return "ID Mobil";
            case 2:
                return "Peminjam";
            case 3:
                return "Harga";
            case 4:
                return "Tgl Pinjaman";
            case 5:
                return "Tgl Kembali";
            case 6:
                return "Lama";
            case 7:
                return "Total";
            default:
                return null;
        }
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getIdTransaksi();
            case 1:
                return list.get(rowIndex).getIdMobil();
            case 2:
                return list.get(rowIndex).getPeminjam();
            case 3:
                return list.get(rowIndex).getHarga();
            case 4:
                return list.get(rowIndex).getTgl_pinjaman();
            case 5:
                return list.get(rowIndex).getTgl_kembali();
            case 6:
                return list.get(rowIndex).getLama() + " Hari";
            case 7:
                return list.get(rowIndex).getTotal();
            default:
                return null;
        }
    }
    
}
