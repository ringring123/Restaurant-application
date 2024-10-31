package com.example.qlnhahang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.Class.User;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class NhanvienAdapter extends ArrayAdapter<Employees> {
    Activity Context;
    int IdLayout;
    ArrayList<Employees> listEmployee;

    public NhanvienAdapter( Activity context, int idLayout, ArrayList<Employees> listEmployee) {
        super(context, idLayout,listEmployee);
        Context = context;
        IdLayout = idLayout;
        this.listEmployee = listEmployee;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = Context.getLayoutInflater();
        convertView = myInflater.inflate(IdLayout,null);
        Employees u = listEmployee.get(position);
        TextView txtname = convertView.findViewById(R.id.txtname);
        TextView txtphone = convertView.findViewById(R.id.txtphone);
        TextView txtposition = convertView.findViewById(R.id.txtposition);
        TextView txtsalary = convertView.findViewById(R.id.txtsalary);
        txtname.setText(u.getFullName());
        txtphone.setText(u.getPhoneNumber());
        txtposition.setText(u.getPosition());
        txtsalary.setText(String.valueOf(u.getSalary()));
        return convertView;
    }
}
