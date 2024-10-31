package com.example.qlnhahang.MenuItems;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

public class AddMOnAn extends AppCompatActivity {
    MyDatabase myDatabase;
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etMenuItemName, etMenuItemPrice, etMenuItemDescription;
    private ImageView ivMenuItemImage;
    private Button btnSelectImage, btnAddMenuItem;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mon_an);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etMenuItemName = findViewById(R.id.etMenuItemName);
        etMenuItemPrice = findViewById(R.id.etMenuItemPrice);
        etMenuItemDescription = findViewById(R.id.etMenuItemDescription);
        ivMenuItemImage = findViewById(R.id.ivMenuItemImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddMenuItem = findViewById(R.id.btnAddMenuItem);

        myDatabase = new MyDatabase(this);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnAddMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenuItem();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            // Sử dụng Glide để hiển thị ảnh từ URI
            Glide.with(this)
                    .load(imageUri)
                    .into(ivMenuItemImage);
        }
    }

    private void addMenuItem() {
        String name = etMenuItemName.getText().toString();
        String priceStr = etMenuItemPrice.getText().toString();
        String description = etMenuItemDescription.getText().toString();
        double price = Double.parseDouble(priceStr);
        String imageUriStr = imageUri != null ? imageUri.toString() : "";

        if (name.isEmpty() || priceStr.isEmpty() || imageUriStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        MenuItems menuItem = new MenuItems(name, price, description, imageUriStr);
        myDatabase.AddMenuItem(menuItem);
        Toast.makeText(this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();

        Intent quaylai = new Intent(AddMOnAn.this, QLMonAn.class);
        quaylai.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(quaylai);
    }
}
