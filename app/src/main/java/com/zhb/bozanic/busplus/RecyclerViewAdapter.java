package com.zhb.bozanic.busplus;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhb.bozanic.busplus.db.Model;
import com.zhb.bozanic.busplus.db.ViewModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    int row_index;

    private List<Model> vezbeModelList;

    Context ctx;

    ViewModel viewModel;

    public RecyclerViewAdapter(List<Model> borrowModelList) {
        this.vezbeModelList = borrowModelList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.ctx = parent.getContext();

        viewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ViewModel.class);

        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final Model vezbeModel = vezbeModelList.get(position);

        holder.txtName.setText(vezbeModel.getDifStatus()+" RSD");
        holder.txtDetails.setText("stara: "+ vezbeModel.getOldStatus() + " RSD; nova: " + vezbeModel.getNewStatus()+" RSD");
        holder.txtDate.setText(vezbeModel.getDate().toLocaleString().substring(0, 11));


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                viewModel.deleteItem(vezbeModel);
                Toast.makeText(ctx, "Deleting: ", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        if (row_index == position) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#80db00c2"));
//            holder.txtName.setTextColor(Color.parseColor("#c4038d"));
//            holder.txtDate.setTextColor(Color.parseColor("#c4038d"));
//            holder.txtDetails.setTextColor(Color.parseColor("#c4038d"));

            holder.txtName.setTextSize(22);
            holder.txtName.setTypeface(null, Typeface.BOLD);
            holder.txtDate.setTypeface(null, Typeface.BOLD);
            holder.txtDetails.setTypeface(null, Typeface.BOLD);

        } else {

            holder.cardView.setCardBackgroundColor(Color.parseColor("#0Ddb00c2"));
//            holder.txtName.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.txtDetails.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.txtDate.setTextColor(Color.parseColor("#FFFFFF"));

            holder.txtName.setTextSize(18);
            holder.txtName.setTypeface(null, Typeface.NORMAL);
            holder.txtDate.setTypeface(null, Typeface.NORMAL);
            holder.txtDetails.setTypeface(null, Typeface.NORMAL);

        }

        holder.itemView.setTag(vezbeModel);
    }

    @Override
    public int getItemCount() {
        return vezbeModelList.size();
    }

    public void addItems(List<Model> borrowModelList) {
        this.vezbeModelList = borrowModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDetails;
        private TextView txtDate;
        private CardView cardView;

        RecyclerViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.difTextView);
            txtDetails = view.findViewById(R.id.statusTextView);
            txtDate = view.findViewById(R.id.dateTextView);
            cardView = view.findViewById(R.id.cardView);
        }
    }

}