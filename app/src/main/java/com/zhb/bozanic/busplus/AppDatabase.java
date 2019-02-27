package com.zhb.bozanic.busplus;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zhb.bozanic.busplus.db.ModelDao;
import com.zhb.bozanic.busplus.db.Model;

// jedna AppDatabase za sve entities
@Database(entities = {Model.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bus_plus")    // contex, klasa gde se nalazi baza podataka, naziv baze podataka
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ModelDao itemAndPersonModel();
}