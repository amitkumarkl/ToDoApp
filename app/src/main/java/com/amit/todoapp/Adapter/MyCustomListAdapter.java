package com.amit.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amit.todoapp.Model.PersonDetails;
import com.amit.todoapp.databinding.CustomListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MyCustomListAdapter extends BaseAdapter {

    private List<PersonDetails> personDetails = new ArrayList<>();
    private Context context;

    public MyCustomListAdapter(List<PersonDetails> personDetails, Context context) {
        this.personDetails = personDetails;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personDetails.size();
    }

    @Override
    public Object getItem(int position) {
       return personDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        CustomListItemBinding binding = CustomListItemBinding.inflate(LayoutInflater.from(context), parent, false);
        view = binding.getRoot();

        binding.tvTitle.setText(personDetails.get(position).getTitle());
        binding.tvDiscription.setText(personDetails.get(position).getDescription());
        binding.tvDatepik.setText(personDetails.get(position).getDatepicker());
        return view;
    }
}
