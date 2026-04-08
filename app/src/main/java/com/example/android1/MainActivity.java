package com.example.android1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // THÊM XỬ LÝ NÚT ĐĂNG XUẤT TẠI ĐÂY
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Xóa phiên đăng nhập trên Firebase
            FirebaseAuth.getInstance().signOut();
            
            // Chuyển người dùng về lại màn hình Đăng nhập
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Đóng màn hình chính lại
        });

        recyclerView = findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);

        db = FirebaseFirestore.getInstance();
        loadMovies();
    }

    private void loadMovies() {
        db.collection("movies")
                .whereEqualTo("status", "now_showing") // Chỉ lấy phim đang chiếu
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        movieList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Movie movie = doc.toObject(Movie.class);
                            if (movie != null) {
                                movie.setId(doc.getId()); // Lưu lại ID của document
                                movieList.add(movie);
                            }
                        }
                        movieAdapter.notifyDataSetChanged(); // Cập nhật lại giao diện
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
