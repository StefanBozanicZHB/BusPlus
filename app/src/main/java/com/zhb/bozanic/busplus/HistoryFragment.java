package com.zhb.bozanic.busplus;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhb.bozanic.busplus.db.Model;
import com.zhb.bozanic.busplus.db.ViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private ViewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<Model>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getItemAndPersonList().observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> itemAndPeople) {
                recyclerViewAdapter.addItems(itemAndPeople);
            }
        });

        return rootView;

    }

}
