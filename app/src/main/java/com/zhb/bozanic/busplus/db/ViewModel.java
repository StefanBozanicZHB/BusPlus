package com.zhb.bozanic.busplus.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.zhb.bozanic.busplus.AppDatabase;

import java.util.List;


public class ViewModel extends AndroidViewModel {

    private final LiveData<List<Model>> itemAndPersonList;

    private AppDatabase appDatabase;

    private LiveData<Model> lastModel;

    public ViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndPersonList = appDatabase.itemAndPersonModel().getAllItems();
    }

    public LiveData<Model> getLastModel() {

        lastModel = appDatabase.itemAndPersonModel().getLastId();

        return lastModel;
    }

    public LiveData<List<Model>> getItemAndPersonList() {
        return itemAndPersonList;
    }

    public void deleteItem(Model model) {
        // uvek se generise AsyncTask za insert, delete, updata
        new deleteAsyncTask(appDatabase).execute(model);
    }

    public void addItem(Model model) {
        // uvek se generise AsyncTask za insert, delete, updata
        new addAsyncTask(appDatabase).execute(model);
    }

    private static class deleteAsyncTask extends AsyncTask<Model, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Model... params) {
            db.itemAndPersonModel().deleteItem(params[0]);
            return null;
        }
    }

    private static class addAsyncTask extends AsyncTask<Model, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Model... params) {
            db.itemAndPersonModel().addItem(params[0]);
            return null;
        }
    }

}