package com.zhb.bozanic.busplus.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

// predstavlja jednu tabelu u bazi podataka
@Entity
public class Model {

    // atributi tabela
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String oldStatus;
    private String newStatus;
    private String difStatus;
    @TypeConverters(DateConverter.class)
    private Date date;

    public Model(String oldStatus, String newStatus, String difStatus, Date date) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.difStatus = difStatus;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void setDifStatus(String difStatus) {
        this.difStatus = difStatus;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getDifStatus() {
        return difStatus;
    }

    public Date getDate() {
        return date;
    }
}