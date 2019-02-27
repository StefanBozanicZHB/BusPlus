package com.zhb.bozanic.busplus.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

// za generisanje upita
// prvi je tip povratne informacije, pa naziv
@Dao
@TypeConverters(DateConverter.class)
public interface ModelDao {

    // kada se vracaju vise odgovora treba obgriliti sa LiveData
    // Aplikacija odgovara na svaku promenu u DB. Room generise neophodan kod za updata kada je baza promenjena
    @Query("select * from Model ORDER BY id DESC")
    LiveData<List<Model>> getAllItems();

    @Query("select * from Model where id = :id")
    Model getItembyId(String id);

    @Query("SELECT * FROM Model WHERE id = (SELECT MAX(id) FROM Model)")
    LiveData<Model> getLastId();

    @Insert(onConflict = REPLACE) // import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
    void addItem(Model model);

    @Delete
    void deleteItem(Model model);
}