package com.zhb.bozanic.busplus;


import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhb.bozanic.busplus.db.Model;
import com.zhb.bozanic.busplus.db.ViewModel;

import java.util.Calendar;
import java.util.Date;


public class InputFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private View rootView;

    private Date date, currentDate;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private Model lastItemModel;
    private ViewModel viewModel;

    private EditText edtUplata, edtIsplata;

    private TextView txtStatus;

    private final String FILENAME = "testfile.txt";

    String status;

    public InputFragment() {
    }

    private void setCurrentDate() {

        Calendar c = Calendar.getInstance();
        currentDate = new Date();
        c.setTime(currentDate);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_input, container, false);

        initialisationComponents();

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        setCallendarControl();

        setCurrentDate();

        setListenerOKs();

        lastItem();

        return rootView;
    }

    private void lastItem() {
        viewModel.getLastModel().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model lastVezba) {
                try{
                    status = lastVezba.getNewStatus();
                    txtStatus.setText("TRENUTNO STANJE: "+status+"RSD");
                }
                catch (Exception e){
                    
                }

            }
        });
    }

    private void setListenerOKs() {

        rootView.findViewById(R.id.btnOkUplata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtUplata.getText().toString().matches("")) {

                    int oldStatus = Integer.parseInt(status);
                    int dif = Integer.parseInt(edtUplata.getText().toString());
                    int resualt = oldStatus + dif;

                    viewModel.addItem(new Model(
                            status,
                            Integer.toString(resualt),
                            edtUplata.getText().toString(),
                            date
                    ));

                    Toast.makeText(getContext(), "Uspesno upisano", Toast.LENGTH_SHORT).show();
                    lastItem();
                    SharedPrefManager.getInstance(getContext()).saveUplata(edtUplata.getText().toString());

                } else {
                    Toast.makeText(getContext(), "Nespesno upisano", Toast.LENGTH_SHORT).show();

                }
            }
        });


        rootView.findViewById(R.id.btnOkIsplata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtIsplata.getText().toString().matches("")) {

                    int oldStatus = Integer.parseInt(status);
                    int dif = Integer.parseInt(edtIsplata.getText().toString());
                    int resualt = oldStatus - dif;

                    viewModel.addItem(new Model(
                            status,
                            Integer.toString(resualt),
                            "-"+edtIsplata.getText().toString(),
                            date
                    ));

                    Toast.makeText(getContext(), "Uspesno upisano", Toast.LENGTH_SHORT).show();
                    lastItem();
                    SharedPrefManager.getInstance(getContext()).saveIsplata(edtIsplata.getText().toString());

                } else {
                    Toast.makeText(getContext(), "Nespesno upisano", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void initialisationComponents() {

        edtUplata = rootView.findViewById(R.id.edtUplata);
        edtIsplata = rootView.findViewById(R.id.edtIsplata);

        txtStatus = rootView.findViewById(R.id.txtStatus);

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), InputFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        edtIsplata.setText(SharedPrefManager.getInstance(getContext()).getIsplata());
        edtUplata.setText(SharedPrefManager.getInstance(getContext()).getUplata());

    }


    private void setCallendarControl() {
        rootView.findViewById(R.id.btnDataIsplata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        rootView.findViewById(R.id.btnDataUplata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
    }

}
