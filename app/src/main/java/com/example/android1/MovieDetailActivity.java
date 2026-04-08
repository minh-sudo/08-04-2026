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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgPoster = findViewById(R.id.imgDetailPoster);
        tvTitle = findViewById(R.id.tvDetailTitle);
        recyclerViewShowtimes = findViewById(R.id.recyclerViewShowtimes);
        recyclerViewShowtimes.setLayoutManager(new LinearLayoutManager(this));

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
                        List<Showtime> showtimesList = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Showtime showtime = doc.toObject(Showtime.class);
                            if (showtime != null) {
                                showtime.setId(doc.getId());
                                showtimesList.add(showtime);
                            }
                        }
                        
                        // In tạm ra Logcat để kiểm tra xem có tải được dữ liệu không
                        Log.d("SHOWTIME_DATA", "Tìm thấy " + showtimesList.size() + " suất chiếu");
                        
                        // TẠI ĐÂY: Bạn sẽ cần tạo một ShowtimeAdapter tương tự như MovieAdapter 
                        // để gán showtimesList vào cái recyclerViewShowtimes.
                        
                    } else {
                        Toast.makeText(this, "Không tìm thấy suất chiếu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
