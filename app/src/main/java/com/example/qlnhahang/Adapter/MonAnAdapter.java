package com.example.qlnhahang.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MenuItems.EditMonAn;
import com.example.qlnhahang.MenuItems.QLMonAn;
import com.example.qlnhahang.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MonAnAdapter extends ArrayAdapter<MenuItems> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<MenuItems> list;

    public MonAnAdapter(Activity context, int layoutId, ArrayList<MenuItems> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, parent, false);
        }

        MenuItems menuItem = list.get(position);

        TextView txttenmonan = convertView.findViewById(R.id.tenmonan);
        TextView txtgiamonan = convertView.findViewById(R.id.giamonan);
        TextView txtmotamonan = convertView.findViewById(R.id.motamonan);
        ImageView imgMonAn = convertView.findViewById(R.id.imagemonan);
        Button btnEdit=convertView.findViewById(R.id.btnEditFood);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditMonAn.class);
                intent.putExtra("id", String.valueOf(menuItem.getMenuItemId()));
                intent.putExtra("name", menuItem.getName());
                intent.putExtra("price", menuItem.getPrice());
                intent.putExtra("description", menuItem.getDescription());
                intent.putExtra("image", menuItem.getImage());
                context.startActivity(intent);
            }
        });

        txttenmonan.setText(menuItem.getName());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String priceFormatted = currencyFormat.format(menuItem.getPrice());
        txtgiamonan.setText(priceFormatted);
        txtmotamonan.setText(menuItem.getDescription());

        if (menuItem.getImage() != null && !menuItem.getImage().isEmpty()) {
            Uri imageUri = Uri.parse(menuItem.getImage());
            Glide.with(context)
                    .load(imageUri)
                    .placeholder(R.drawable.img) // Placeholder khi ảnh chưa được tải
                    .into(imgMonAn);
        } else {
            imgMonAn.setImageResource(R.drawable.img); // Placeholder nếu không có URL
        }

        return convertView;
    }
}
