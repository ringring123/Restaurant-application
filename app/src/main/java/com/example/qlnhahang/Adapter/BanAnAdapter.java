package com.example.qlnhahang.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlnhahang.Class.DailyMenu;
import com.example.qlnhahang.Class.Tables;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class BanAnAdapter extends ArrayAdapter<Tables> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<Tables> list;

    public BanAnAdapter(Activity context, int layoutId, ArrayList<Tables> list) {
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
        Tables tables = list.get(position);
        // Find the TextViews in the layout and set their values
        TextView tablenumberTextView = convertView.findViewById(R.id.tablenumber);
        TextView statusTextView = convertView.findViewById(R.id.status);
        tablenumberTextView.setText(String.valueOf(tables.getTableNumber()));
        statusTextView.setText(tables.getStatus());
        return convertView;
    }
}
