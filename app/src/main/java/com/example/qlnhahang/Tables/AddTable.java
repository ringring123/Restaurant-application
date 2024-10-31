package com.example.qlnhahang.Tables;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Class.Tables;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTable extends AppCompatActivity {
    MyDatabase myDatabase;
    EditText etTableName, etTableCapacity, etMenuDate;
    Spinner spinnerTableStatus;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDatabase = new MyDatabase(this);
        etTableName = findViewById(R.id.etTableName);
        etTableCapacity = findViewById(R.id.etTableCapacity);
        spinnerTableStatus = findViewById(R.id.spinnerTableStatus);
        etMenuDate = findViewById(R.id.etMenuDate);
        calendar = Calendar.getInstance();
        etMenuDate.setOnClickListener(v -> showDatePicker());
        ArrayList<String> table_status = new ArrayList<>();
        table_status.add("Trống");
        table_status.add("Đang sử dụng");
        table_status.add("Đã đặt");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, table_status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableStatus.setAdapter(adapter);
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
    public void thembanan(View view){
        int tableName = Integer.parseInt((String.valueOf(etTableName.getText())));
        int tableCapacity = Integer.parseInt((String.valueOf(etTableCapacity.getText())));
        String tableStatus = spinnerTableStatus.getSelectedItem().toString();
        String menuDate = etMenuDate.getText().toString();
        Tables table = new Tables(tableName, tableCapacity, tableStatus, menuDate);
        int result = myDatabase.addTable(table);
        if(result != -1){
            Toast.makeText(this, "Thêm bàn ăn thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddTable.this, QLTable.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Thêm bàn ăn thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}