package com.example.qlnhahang.Revenue;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class QLRevenue extends AppCompatActivity {
    EditText etMenuDate, etRevenue;
    private Calendar calendar;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qlrevenue);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etMenuDate = findViewById(R.id.etMenuDate);
        etRevenue = findViewById(R.id.total);
        myDatabase = new MyDatabase(this);
        calendar = Calendar.getInstance();
        etMenuDate.setOnClickListener(v -> showDatePicker());
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
    public void xem(View view){
        String date = etMenuDate.getText().toString();
        double revenue = myDatabase.getTotalForDate(date);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedRevenue = currencyFormat.format(revenue);

        // Hiển thị số tiền đã định dạng trong EditText
        etRevenue.setText(formattedRevenue);
    }
}