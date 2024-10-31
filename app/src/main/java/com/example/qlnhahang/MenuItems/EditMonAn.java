package com.example.qlnhahang.MenuItems;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.Employees.EditEmployee;
import com.example.qlnhahang.Employees.QLNhanVien;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

public class EditMonAn extends AppCompatActivity {
    MyDatabase myDatabase;
    EditText etMenuItemName, etMenuItemPrice, etMenuItemDescription;
    ImageView ivMenuItemImage;
    Button btnSelectImage, btnUpdateMenuItem, btnDeleteMenuItem;
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private Uri oldImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mon_an);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDatabase = new MyDatabase(this);
        etMenuItemName = findViewById(R.id.etMenuItemName);
        etMenuItemPrice = findViewById(R.id.etMenuItemPrice);
        etMenuItemDescription = findViewById(R.id.etMenuItemDescription);
        ivMenuItemImage = findViewById(R.id.ivMenuItemImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateMenuItem = findViewById(R.id.btnUpdateMenuItem);
        btnDeleteMenuItem = findViewById(R.id.btnDeleteMenuItem);

        String menuItem = getIntent().getStringExtra("name");
        double menuItemPrice = getIntent().getDoubleExtra("price", 0.0);
        String menuItemDescription = getIntent().getStringExtra("description");
        String menuItemImage = getIntent().getStringExtra("image");
        oldImageUri = menuItemImage != null ? Uri.parse(menuItemImage) : null;
        etMenuItemName.setText(menuItem);
        etMenuItemPrice.setText(String.valueOf(menuItemPrice));
        etMenuItemDescription.setText(menuItemDescription);

        if (menuItemImage != null) {
            Glide.with(this)
                    .load(Uri.parse(menuItemImage))
                    .placeholder(R.drawable.img) // Placeholder image
                    .into(ivMenuItemImage);
        }

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        btnUpdateMenuItem.setOnClickListener(v -> updateMenuItem());
        btnDeleteMenuItem.setOnClickListener(v -> deleteMenuItem());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.img) // Placeholder image
                    .into(ivMenuItemImage);
        }
    }

    private void updateMenuItem() {
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = etMenuItemName.getText().toString();
        String priceStr = etMenuItemPrice.getText().toString();
        String description = etMenuItemDescription.getText().toString();

        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Điền tất cả thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        // Sử dụng ảnh mới nếu người dùng đã chọn ảnh mới, nếu không thì giữ ảnh cũ
        String image = imageUri != null ? imageUri.toString() : oldImageUri != null ? oldImageUri.toString() : null;

        MenuItems u = new MenuItems(id, name, price, description, image);
        // Update database
        myDatabase.updateMenuItem(u);
        Intent intent = new Intent(EditMonAn.this, QLMonAn.class);
        startActivity(intent);
    }

    private void deleteMenuItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa món ăn này?");
        builder.setCancelable(true);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int menuid = Integer.parseInt(getIntent().getStringExtra("id"));
                myDatabase.deleteMenuItem(menuid);
                Intent intent = new Intent(EditMonAn.this, QLMonAn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //Tránh quay lại màn hình sửa

                startActivity(intent);
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
