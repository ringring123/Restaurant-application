package com.example.qlnhahang.DailyMenu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Adapter.DailyMenuAdapter;
import com.example.qlnhahang.Adapter.DailyMenuItemsAdapter;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class AddDailyMenu extends AppCompatActivity {
    EditText etMenuDate;
    ListView dailymenu;
    private Calendar calendar;
    private ArrayList<MenuItems> menuItemsList;
    private DailyMenuItemsAdapter adapter;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_daily_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMenuDate = findViewById(R.id.etMenuDate);
        dailymenu = findViewById(R.id.dailymenu);
        myDatabase = new MyDatabase(this);
        calendar = Calendar.getInstance();
        etMenuDate.setOnClickListener(v -> showDatePicker());
        menuItemsList = myDatabase.getAllMenuItems();
        adapter = new DailyMenuItemsAdapter(this,R.layout.list_daily_menu,menuItemsList);
        dailymenu.setAdapter(adapter);

    }
    private void showDatePicker() {
        // Khởi tạo đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo và hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Định dạng ngày theo kiểu "dd-MM-yyyy"
                    String date = String.format("%02d-%02d-%d", dayOfMonth, month1 + 1, year1);
                    etMenuDate.setText(date);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    public void them(View view) {
        String date = etMenuDate.getText().toString();
        if (date.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<MenuItems> selectedItemsSet = adapter.getSelectedItems();
        if (selectedItemsSet.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<MenuItems> selectedItemsList = new ArrayList<>(selectedItemsSet);

        // Thêm vào cơ sở dữ liệu
        long result = myDatabase.addDailyMenuWithItems(date, selectedItemsList);

        if (result != -1) {
            Toast.makeText(this, "Thêm thực đơn thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddDailyMenu.this, QLDailyMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }

}