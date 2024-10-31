package com.example.qlnhahang.DailyMenu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Adapter.DailyMenuItemsAdapter;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class EditDailyMenu extends AppCompatActivity {
    EditText etMenuDate;
    ListView dailymenu;
    MyDatabase myDatabase;
    DailyMenuItemsAdapter adapter;
    ArrayList<MenuItems> menuItemsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_daily_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etMenuDate = findViewById(R.id.etMenuDate);
        dailymenu = findViewById(R.id.dailymenu);
        myDatabase = new MyDatabase(this);
        String date = getIntent().getStringExtra("date");
        etMenuDate.setText(date);
        menuItemsList = myDatabase.getAllMenuItems();
        adapter = new DailyMenuItemsAdapter(this, R.layout.list_daily_menu, menuItemsList);
        dailymenu.setAdapter(adapter);
    }
    public void capnhat(View view){
        String date = etMenuDate.getText().toString();
        ArrayList<MenuItems> selectedItems = new ArrayList<>(adapter.getSelectedItems());
        myDatabase.updateDailyMenu(date, selectedItems);
        Intent intent = new Intent(EditDailyMenu.this, QLDailyMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
    public void xoa(View view){
        String date = etMenuDate.getText().toString();
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa thực đơn cho ngày " + date + " không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa thực đơn nếu người dùng chọn "Có"
                    myDatabase.deleteDailyMenu(date);
                    Toast.makeText(this, "Xóa thực đơn thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển hướng đến QLDailyMenu
                    Intent intent = new Intent(EditDailyMenu.this, QLDailyMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    // Đóng hộp thoại nếu người dùng chọn "Không"
                    dialog.dismiss();
                })
                .show();
    }
}