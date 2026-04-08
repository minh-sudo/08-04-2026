package com.example.android1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private Context context;
    private List<Showtime> showtimeList;

    public ShowtimeAdapter(Context context, List<Showtime> showtimeList) {
        this.context = context;
        this.showtimeList = showtimeList;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimeList.get(position);
        holder.tvStart.setText(showtime.getStartTime());
        holder.tvTheater.setText(showtime.getTheaterName());
        holder.tvPrice.setText(showtime.getPrice() + "đ");
    }

    @Override
    public int getItemCount() {
        return showtimeList.size();
    }

    public static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvStart, tvTheater, tvPrice;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStart = itemView.findViewById(R.id.tvStartTime);
            tvTheater = itemView.findViewById(R.id.tvTheaterName);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
        }
    }
}
