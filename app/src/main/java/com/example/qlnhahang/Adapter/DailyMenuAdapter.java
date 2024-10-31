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
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class DailyMenuAdapter extends ArrayAdapter<DailyMenu> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<DailyMenu> list;

    public DailyMenuAdapter(Activity context, int layoutId, ArrayList<DailyMenu> list) {
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
        DailyMenu dailyMenu = list.get(position);

        // Find the TextViews in the layout and set their values
        TextView menudateTextView = convertView.findViewById(R.id.menudate);
        TextView somonanTextView = convertView.findViewById(R.id.somonan);
        MyDatabase myDatabase = new MyDatabase(context);
        menudateTextView.setText(dailyMenu.getDate());
        somonanTextView.setText(String.valueOf(myDatabase.getCountOfMenuItemsByDate(dailyMenu.getDate())));
        return convertView;
    }
}
