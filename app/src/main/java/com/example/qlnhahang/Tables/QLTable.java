package com.example.qlnhahang.Tables;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Adapter.BanAnAdapter;
import com.example.qlnhahang.Class.Tables;
import com.example.qlnhahang.Main;
import com.example.qlnhahang.MainActivity;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class QLTable extends AppCompatActivity {
    MyDatabase myDatabase;
    ArrayList<Tables> list;
    ArrayAdapter<Tables> adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qltable);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDatabase = new MyDatabase(this);
        list = myDatabase.getAllTables();
        adapter = new BanAnAdapter(this,R.layout.layout_table, list);
        listView = findViewById(R.id.listbanan);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QLTable.this, EditTable.class);
                Tables tb = list.get(i);
                intent.putExtra("id",String.valueOf(tb.getTableId()));
                intent.putExtra("number", String.valueOf(tb.getTableNumber()));
                intent.putExtra("capacity", String.valueOf(tb.getCapacity()));
                intent.putExtra("status", tb.getStatus());
                intent.putExtra("date", tb.getTabledate());
                startActivity(intent);
            }
        });
    }
    public void thembanan(View view){
        Intent thembanan = new Intent(QLTable.this, AddTable.class);
        startActivity(thembanan);
    }
    public void quaylai(View view){
        finish();
    }
}