package com.example.android1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imgPoster;
    private TextView tvTitle;
    private FirebaseFirestore db;
    private String movieId; // ID phim nhận từ màn hình trước
    private RecyclerView recyclerViewShowtimes;
    private ShowtimeAdapter showtimeAdapter;
    private List<Showtime> showtimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgPoster = findViewById(R.id.imgDetailPoster);
        tvTitle = findViewById(R.id.tvDetailTitle);
        recyclerViewShowtimes = findViewById(R.id.recyclerViewShowtimes);
        recyclerViewShowtimes.setLayoutManager(new LinearLayoutManager(this));

        showtimesList = new ArrayList<>();
        showtimeAdapter = new ShowtimeAdapter(this, showtimesList);
        recyclerViewShowtimes.setAdapter(showtimeAdapter);

        db = FirebaseFirestore.getInstance();

        // 1. Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        movieId = intent.getStringExtra("MOVIE_ID");
        String title = intent.getStringExtra("MOVIE_TITLE");
        String posterUrl = intent.getStringExtra("MOVIE_POSTER");

        // 2. Hiển thị thông tin phim
        tvTitle.setText(title);
        Glide.with(this).load(posterUrl).into(imgPoster);

        // 3. Tải danh sách suất chiếu
        loadShowtimes();
    }

    private void loadShowtimes() {
        // Tìm các suất chiếu có movieId bằng với ID của phim đang xem
        db.collection("showtimes")
                .whereEqualTo("movieId", movieId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        showtimesList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Showtime showtime = doc.toObject(Showtime.class);
                            if (showtime != null) {
                                showtime.setId(doc.getId());
                                showtimesList.add(showtime);
                            }
                        }
                        
                        // Cập nhật adapter sau khi tải xong dữ liệu
                        showtimeAdapter.notifyDataSetChanged();
                        
                        Log.d("SHOWTIME_DATA", "Tìm thấy " + showtimesList.size() + " suất chiếu");
                        
                    } else {
                        Toast.makeText(this, "Không tìm thấy suất chiếu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
