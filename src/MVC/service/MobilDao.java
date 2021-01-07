/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.service;

import MVC.model.mobil.MobilModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Muhammad Fahmi Prasetio
 */
public interface MobilDao {
    // Get By ID dan Get By Email
    public void insertMobil(MobilModel mobil) throws SQLException;

    public void updateMobil(MobilModel mobil) throws SQLException;

    public void deleteMobil(Integer id_mobil) throws SQLException;
    
    // Get All data
    public List<MobilModel> getAllMobil();
}
