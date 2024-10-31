package com.example.qlnhahang.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.qlnhahang.Class.DailyMenu;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BanAnMenuAdapter extends ArrayAdapter<MenuItems> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<MenuItems> list;

    public BanAnMenuAdapter(Activity context, int layoutId, ArrayList<MenuItems> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
        }

        // Get the current DailyMenu item
        MenuItems m = list.get(position);

        // Find the TextViews in the layout and set their values
        TextView tvMenuItemName = convertView.findViewById(R.id.tvTableMenuItemName);
        TextView tvMenuItemPrice = convertView.findViewById(R.id.tvTableMenuItemPrice);
        EditText etMenuItemQuantity = convertView.findViewById(R.id.etQuantity);
        tvMenuItemName.setText(m.getName());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvMenuItemPrice.setText(currencyFormat.format(m.getPrice()));
        etMenuItemQuantity.setText(String.valueOf(m.getQuantity()));
        return convertView;
    }
}
