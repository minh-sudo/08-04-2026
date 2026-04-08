package com.example.android1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvStatus.setText("now_showing".equals(movie.getStatus()) ? "Đang chiếu" : "Sắp chiếu");

        // Dùng Glide để load ảnh từ link URL
        Glide.with(context)
                .load(movie.getPosterUrl())
                .into(holder.imgPoster);

        // THÊM ĐOẠN NÀY ĐỂ BẮT SỰ KIỆN CLICK
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            // Gửi dữ liệu sang màn hình sau
            intent.putExtra("MOVIE_ID", movie.getId());
            intent.putExtra("MOVIE_TITLE", movie.getTitle());
            intent.putExtra("MOVIE_POSTER", movie.getPosterUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvStatus;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvStatus = itemView.findViewById(R.id.tvMovieStatus);
        }
    }
}
