/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model.mobil;

import java.util.Objects;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public class MobilModel {
    private int id_mobil;
    private String merek;
    private String tipe;
    private int tahun;
    private String nopol;
    private int harga;
    private String status;
    
    public Integer getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(Integer id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Integer getTahun() {
        return tahun;
    }

    public void setTahun(Integer tahun) {
        this.tahun = tahun;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
