package com.example.userlistapp;


import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        fetchUsers();
    }

    private void fetchUsers() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://randomuser.me/api/?results=20") // Modify the URL as needed
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String responseData = response.body().string();
                Type userListType = new TypeToken<List<User>>() {}.getType();
                UserResponse userResponse = new Gson().fromJson(responseData, UserResponse.class);
                List<User> users = userResponse.getResults();

                runOnUiThread(() -> userAdapter.setUsers(users));
            } catch (IOException e) {
                Log.e(TAG, "Error fetching users", e);
            }
        }).start();
    }
}
