package com.example.qlnhahang.Employees;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

public class EditEmployee extends AppCompatActivity {
    EditText editTextId, editTextName, editTextPhone, editTextPosition, editTextSalary;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPosition = findViewById(R.id.editTextPosition);
        editTextSalary = findViewById(R.id.editTextSalary);
        myDatabase = new MyDatabase(this);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String position = getIntent().getStringExtra("position");
        String salary =(getIntent().getStringExtra("salary"));

        editTextId.setText(String.valueOf(id));
        editTextName.setText(name);
        editTextPhone.setText(phone);
        editTextPosition.setText(position);
        editTextSalary.setText(String.valueOf(salary));
    }
    public void capnhat(View view){
        int id = Integer.parseInt(editTextId.getText().toString());
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String position = editTextPosition.getText().toString();
        String salary = editTextSalary.getText().toString();
        Employees e = new Employees(id,name,phone,position,salary);
        myDatabase.updateEmployee(e);
        finish();
    }
    public void xoa(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
        builder.setCancelable(true);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int employeeId = Integer.parseInt(editTextId.getText().toString());
                myDatabase.deleteEmployee(employeeId);
                // Create an intent to navigate back to the employee list
                Intent intent = new Intent(EditEmployee.this, QLNhanVien.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //Tránh quay lại màn hình sửa
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}