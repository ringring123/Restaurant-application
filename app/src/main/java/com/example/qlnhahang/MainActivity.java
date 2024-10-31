package com.example.qlnhahang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Class.User;

public class MainActivity extends AppCompatActivity {
    EditText txtusername, txtpass;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtusername = findViewById(R.id.etUsername);
        txtpass = findViewById(R.id.etPassword);
        myDatabase = new MyDatabase(this);
    }

    public void login(View view) {
        String username = txtusername.getText().toString();
        String password = txtpass.getText().toString();
        User user = new User(username, password);
        int result = myDatabase.findUser(user);
        if (result == 1) { // Nếu người dùng và mật khẩu khớp
            Intent intent = new Intent(MainActivity.this, Main.class);
            startActivity(intent);
        } else if (result == -1) { // Nếu người dùng tồn tại nhưng mật khẩu không khớp
            Toast.makeText(this, "Mật khẩu không đúng!", Toast.LENGTH_LONG).show();
        } else { // Nếu người dùng không tồn tại
            Toast.makeText(this, "Tên đăng nhập không tồn tại!", Toast.LENGTH_LONG).show();
        }
    }

    public void signin(View view) {
        txtusername = findViewById(R.id.etUsername);
        txtpass = findViewById(R.id.etPassword);
        if (txtusername.getText().toString().isEmpty() || txtpass.getText().toString().isEmpty())
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        else {
            String username = txtusername.getText().toString();
            String password = txtpass.getText().toString();
            User user = new User(username, password);
            int result = myDatabase.findUser(user);

            if (result == 0) { // Nếu người dùng không tồn tại
                myDatabase.addUser(user);
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            } else if (result == 1) { // Nếu người dùng và mật khẩu khớp (đã đăng ký rồi)
                Toast.makeText(this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
            } else { // Nếu người dùng tồn tại nhưng mật khẩu không khớp
                Toast.makeText(this, "Tên đăng nhập đã tồn tại với mật khẩu khác!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}