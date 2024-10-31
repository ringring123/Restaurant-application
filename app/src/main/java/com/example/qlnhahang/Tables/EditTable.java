package com.example.qlnhahang.Tables;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Adapter.BanAnMenuAdapter;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.Class.Tables;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTable extends AppCompatActivity {
    MyDatabase myDatabase;
    EditText etTableName, etTableCapacity, etMenuDate;
    Spinner spinnerTableStatus;
    ArrayAdapter<MenuItems> menuItemsAdapter;
    ArrayList<MenuItems> menuItemsList;
    ListView dailymenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDatabase = new MyDatabase(this);
        etTableName = findViewById(R.id.etTableName);
        etTableCapacity = findViewById(R.id.etTableCapacity);
        spinnerTableStatus = findViewById(R.id.spinnerTableStatus);
        dailymenu = findViewById(R.id.dailymenu);
        etMenuDate = findViewById(R.id.etMenuDate);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        int number = Integer.parseInt(getIntent().getStringExtra("number"));
        String capacity = getIntent().getStringExtra("capacity");
        String status = getIntent().getStringExtra("status");
        String date = getIntent().getStringExtra("date");

        etTableName.setText(String.valueOf(number));
        etTableCapacity.setText(capacity);
        etMenuDate.setText(date);

        ArrayList<String> tableStatusList = new ArrayList<>();
        tableStatusList.add("Trống");
        tableStatusList.add("Đang sử dụng");
        tableStatusList.add("Đã đặt");


        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tableStatusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableStatus.setAdapter(statusAdapter);

        // Set the selected item
        int statusPosition = tableStatusList.indexOf(status);
        spinnerTableStatus.setSelection(statusPosition);
        menuItemsList = myDatabase.getMenuItemsForDate(id,date);
        menuItemsAdapter = new BanAnMenuAdapter(this, R.layout.layout_table_menu, menuItemsList);
        dailymenu.setAdapter(menuItemsAdapter);
    }
    public void xoa(View view) {
        // Lấy ID của bàn ăn từ Intent
        int id = Integer.parseInt(getIntent().getStringExtra("id"));

        // Tạo hộp thoại xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Xóa bàn ăn")
                .setMessage("Bạn có chắc chắn muốn xóa bàn ăn này?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa bàn ăn từ cơ sở dữ liệu
                    myDatabase.deleteTable(id);

                    // Quay lại danh sách bàn ăn
                    Intent intent = new Intent(EditTable.this, QLTable.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()) // Đóng hộp thoại nếu người dùng chọn "Không"
                .show(); // Hiển thị hộp thoại
    }
    public void capnhat(View view) {
        int name = Integer.parseInt(etTableName.getText().toString());
        int capacity = Integer.parseInt(etTableCapacity.getText().toString());
        String status = spinnerTableStatus.getSelectedItem().toString();
        String date = etMenuDate.getText().toString();
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        Tables tb = new Tables(id, name, capacity, status, date);
        myDatabase.updateTable(tb);
        Intent intent = new Intent(EditTable.this, QLTable.class);
        startActivity(intent);

        for (int i = 0; i < dailymenu.getCount(); i++) {
            View viewItem = dailymenu.getChildAt(i);
            EditText etQuantity = viewItem.findViewById(R.id.etQuantity);
            int quantity = Integer.parseInt(etQuantity.getText().toString());
            int menuItemId = ((MenuItems) dailymenu.getItemAtPosition(i)).getMenuItemId();

            myDatabase.updateMenuItemQuantity(id, menuItemId, quantity, date);
        }

        Intent intent1 = new Intent(EditTable.this, QLTable.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        finish();
    }
    public void hoanthanh(View view) {
        // Lấy ID của bàn ăn từ Intent
        int tableId = Integer.parseInt(getIntent().getStringExtra("id"));
        String date = etMenuDate.getText().toString();

        // Tính tổng doanh thu cho bàn
        double revenue = myDatabase.getRevenueForTableAndDate(tableId, date);

        // Hiển thị hộp thoại thông báo số tiền cần thanh toán
        showPaymentDialog(tableId, revenue, date);
    }

    private void showPaymentDialog(int tableId, double amount, String date) {
        // Tạo đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo Thanh Toán");

        // Định dạng số tiền theo tiền tệ
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedAmount = currencyFormat.format(amount);

        // Cấu hình nội dung và nút của hộp thoại
        builder.setMessage("Số tiền cần thanh toán: " + formattedAmount);
        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            // Xử lý khi người dùng chọn "Đồng ý"
            // Thêm doanh thu vào cơ sở dữ liệu
            myDatabase.addRevenue(date, amount);

            // Xóa tất cả dữ liệu món ăn của bàn từ cơ sở dữ liệu
            myDatabase.deleteTableMenuItems(tableId);

            // Quay lại danh sách bàn ăn
            Intent intent = new Intent(EditTable.this, QLTable.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kết thúc Activity hiện tại

            dialog.dismiss(); // Đóng hộp thoại
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> {
            // Xử lý khi người dùng chọn "Hủy"
            dialog.dismiss(); // Đóng hộp thoại
        });

        // Hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
