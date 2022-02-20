package com.amit.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.amit.todoapp.databinding.ActivityShowDataBinding;

public class Show_data extends AppCompatActivity {

    ActivityShowDataBinding binding;

    String title,description,datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        title=intent.getStringExtra("title");
        description=intent.getStringExtra("description");
        datepicker=intent.getStringExtra("datepicker");

        binding.titleDetail.setText(title);
        binding.descriptionDetail.setText(description);
        binding.datepikDetail.setText(datepicker);


        binding.thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(Show_data.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

     // TODO Disable back button..............
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "Please Click thank you button",
                    Toast.LENGTH_SHORT).show();
        return false;
    }
}