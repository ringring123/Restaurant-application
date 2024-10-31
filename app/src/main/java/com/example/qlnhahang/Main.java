package com.example.qlnhahang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.DailyMenu.QLDailyMenu;
import com.example.qlnhahang.Employees.QLNhanVien;
import com.example.qlnhahang.MenuItems.QLMonAn;
import com.example.qlnhahang.Revenue.QLRevenue;
import com.example.qlnhahang.Tables.QLTable;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void qlnhanvien(View view){
        Intent nhanvien = new Intent(Main.this, QLNhanVien.class);
        startActivity(nhanvien);
    }
    public void qlmonan(View view){
        Intent monan = new Intent(Main.this, QLMonAn.class);
        startActivity(monan);
    }
    public void menutheongay(View view){
        Intent menutheongay = new Intent(Main.this, QLDailyMenu.class);
        startActivity(menutheongay);
    }
    public void qlban(View view){
        Intent ban = new Intent(Main.this, QLTable.class);
        startActivity(ban);
    }
    public void doanhthu(View view){
        Intent doanhthu = new Intent(Main.this, QLRevenue.class);
        startActivity(doanhthu);
    }
}