package com.example.qlnhahang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DailyMenuItemsAdapter extends ArrayAdapter<MenuItems> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<MenuItems> list;
    private final Set<MenuItems> selectedItems; // Để lưu các món đã chọn

    public DailyMenuItemsAdapter(Activity context, int layoutId, ArrayList<MenuItems> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.selectedItems = new HashSet<>(); // Khởi tạo Set để quản lý món đã chọn
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView tvMenuItemName = convertView.findViewById(R.id.tvMenuItemName);
        CheckBox cbMenuItem = convertView.findViewById(R.id.checkbox);

        MenuItems item = list.get(position);

        tvMenuItemName.setText(item.getName());

        // Kiểm tra nếu món đã được chọn trước đó
        cbMenuItem.setChecked(selectedItems.contains(item));

        // Lắng nghe sự kiện chọn hoặc bỏ chọn CheckBox
        cbMenuItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item); // Thêm món vào Set khi chọn
            } else {
                selectedItems.remove(item); // Xóa món khỏi Set khi bỏ chọn
            }
        });

        return convertView;
    }

    // Phương thức để lấy các món đã chọn
    public Set<MenuItems> getSelectedItems() {
        return selectedItems;
    }
}
