/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model.mobil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class TableMobilModel extends AbstractTableModel{
    private List<MobilModel> list = new ArrayList<>();
    private final DecimalFormat kurs;
    private final DecimalFormatSymbols rp;

    public TableMobilModel(List<MobilModel> list) {
        kurs = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        rp = DecimalFormatSymbols.getInstance();
        this.list = list;
    }
    
    /**
     * Untuk konversi mata uang ke RUPIAH
     * @param biaya
     * @return 
     */
    private String kurs(int biaya){
        rp.setCurrencySymbol("Rp. ");
        rp.setGroupingSeparator('.');
        rp.setMonetaryDecimalSeparator(',');
        kurs.setDecimalFormatSymbols(rp);
        return kurs.format(biaya);
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Merek";
            case 2:
                return "Tipe";
            case 3:
                return "Tahun";
            case 4:
                return "Nopol";
            case 5:
                return "Harga";
            case 6:
                return "Status";
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
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId_mobil();
            case 1:
                return list.get(rowIndex).getMerek();
            case 2:
                return list.get(rowIndex).getTipe();
            case 3:
                return list.get(rowIndex).getTahun();
            case 4:
                return list.get(rowIndex).getNopol();
            case 5:
                return kurs(list.get(rowIndex).getHarga());
            case 6:
                return list.get(rowIndex).getStatus();
            default:
                return null;
        }
    }

}
