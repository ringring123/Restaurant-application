package com.example.qlnhahang.Employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Adapter.NhanvienAdapter;
import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.Main;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class QLNhanVien extends AppCompatActivity {
    ArrayList<Employees> listEmployee;
    private MyDatabase myDatabase;
    ListView listView;
    NhanvienAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qlnhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listEmployee);
        myDatabase = new MyDatabase(this);
        listEmployee = myDatabase.getAllEmployees();
        adapter = new NhanvienAdapter(QLNhanVien.this,R.layout.layout_nhanvien,listEmployee);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employees selectedEmployee = listEmployee.get(i);
                Intent intent = new Intent(QLNhanVien.this, EditEmployee.class);
                intent.putExtra("id", String.valueOf(selectedEmployee.getEmployeeId()));
                intent.putExtra("name", selectedEmployee.getFullName());
                intent.putExtra("phone", selectedEmployee.getPhoneNumber());
                intent.putExtra("position", selectedEmployee.getPosition());
                intent.putExtra("salary", String.valueOf(selectedEmployee.getSalary()));
                startActivity(intent);
            }
        });

    }
    public void quaylai(View view){
        Intent quaylai = new Intent(QLNhanVien.this, Main.class);
        startActivity(quaylai);
        finish();
    }
    public void themnhanvien(View view){
        Intent themnhanvien = new Intent(QLNhanVien.this, AddEmployee.class);
        startActivity(themnhanvien);
    }

}