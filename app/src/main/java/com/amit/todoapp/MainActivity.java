package com.amit.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.amit.todoapp.Adapter.MyAdapter;
import com.amit.todoapp.Adapter.MyCustomListAdapter;
import com.amit.todoapp.Model.PersonDetails;
import com.amit.todoapp.databinding.ActivityMainBinding;
import com.amit.todoapp.databinding.CustomListItemBinding;
import com.amit.todoapp.databinding.InsertDataBinding;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ActivityMainBinding binding;

    private InsertDataBinding bindingInsert;
     MyAdapter myAdapter;
    private Cursor cursor;
    final Calendar myCalendar= Calendar.getInstance();
    Context context;

    private int clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context=this;
       binding.listView.setOnItemClickListener(this);

      myAdapter = new MyAdapter(context);
       myAdapter.openDatabase();

        loadData();
        registerForContextMenu(binding.listView);
        binding.listView.setOnItemLongClickListener(this);

        binding.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         bindingInsert = InsertDataBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(context);
                dialog.setContentView(bindingInsert.getRoot());
                dialog.setCancelable(false);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);

                        updateLabel();

                    }
                };
                bindingInsert.etDatepik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });


                bindingInsert.cencle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                bindingInsert.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myAdapter.insertRecord(context,bindingInsert.etTitle.getText().toString(),
                                bindingInsert.Description.getText().toString(),bindingInsert.etDatepik
                                        .getText().toString());

                        dialog.dismiss();
                        loadData();

                    }

//                    private List<PersonDetails> getDatainList(){
//                       cursor = myAdapter.getAllRecords();
//                        List<PersonDetails> finalList = new ArrayList<>();
//                        if (cursor.getCount() > 0) {
//                            cursor.moveToFirst();
//                            do {
//                                String serNo = cursor.getString(0);// serialNo
//                                String title = cursor.getString(1);// title
//                                String description = cursor.getString(2);// description
//                                String datepicker = cursor.getString(3);// datepicker
//                                PersonDetails personDetails = new PersonDetails(title, description, datepicker);
//                                finalList.add(personDetails);
//                            } while (cursor.moveToNext());
//                        }
//                        return finalList;
//                    }

//                    private void loadData() {
//
//                        if (getDatainList().size() > 0) {
//                            binding.listView.setVisibility(View.VISIBLE);
//                            MyCustomListAdapter myCustomListAdapter = new MyCustomListAdapter(getDatainList(), context);
//                            binding.listView.setAdapter(myCustomListAdapter);
//                        }else {
//                            Toast.makeText(context, "No Data found.", Toast.LENGTH_SHORT).show();
//                            binding.listView.setVisibility(View.GONE);
//
//                        }
//                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);

            }

            private void updateLabel() {
                String myFormat = "EEE / MMM / d /yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                bindingInsert.etDatepik.setText(dateFormat.format(myCalendar.getTime()));

            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.delete_decord,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.delete_Alldata:

                // TODO : Delete related task
                myAdapter.deleteAllRecords(context);
                loadData();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PersonDetails personDetails = (PersonDetails)parent.getItemAtPosition(position);
        Log.d("PersonDetails", ""+personDetails);

        Intent intent = new Intent(MainActivity.this,Show_data.class);

        intent.putExtra("title",personDetails.getTitle());
        intent.putExtra("description",personDetails.getDescription());
        intent.putExtra("datepicker",personDetails.getDatepicker());
        startActivity(intent);
        finish();
    }

    private List<PersonDetails> getDatainList(){

        cursor = myAdapter.getAllRecords();
        List<PersonDetails> finalList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String serNo = cursor.getString(0);// serialNo
                String title = cursor.getString(1);// photo
                String description = cursor.getString(2);// fname
                String datepicker = cursor.getString(3);// lname
                PersonDetails personDetails = new PersonDetails(title, description, datepicker);
                finalList.add(personDetails);
            } while (cursor.moveToNext());
        }
        return finalList;
    }

    private void loadData() {

        if (getDatainList().size() > 0) {
            binding.listView.setVisibility(View.VISIBLE);
            MyCustomListAdapter myCustomListAdapter = new MyCustomListAdapter(getDatainList(), context);
            binding.listView.setAdapter(myCustomListAdapter);
        }else {
            Toast.makeText(context, "No Data found.", Toast.LENGTH_SHORT).show();
            binding.listView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_option, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_one:

                // TODO : DElete record....
                cursor.moveToPosition(clickedPosition);
                String colRow = cursor.getString(0);
                myAdapter.deleteRecord(colRow, context);
                loadData();
                break;

            case R.id.update:

                // TODO : Update record......
                InsertDataBinding updateProfileBinding = InsertDataBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(context);
                dialog.setContentView(updateProfileBinding.getRoot());
                dialog.setCancelable(false);
                updateProfileBinding.save.setText("Update");

                updateProfileBinding.cencle.setText("Updation cancle");

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

                cursor.moveToPosition(clickedPosition);

                String rowId = cursor.getString(0);
                String title = cursor.getString(1);// title
                String description = cursor.getString(2);// description
                String datepicker = cursor.getString(3);// datepicker

                updateProfileBinding.etTitle.setText(title);
                updateProfileBinding.Description.setText(description);
                updateProfileBinding.etDatepik.setText(datepicker);
                dialog.show();

                //TODO Data Saved.......
                updateProfileBinding.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myAdapter.updateRecord(context, rowId, updateProfileBinding.etTitle.getText().toString(),
                                updateProfileBinding.Description.getText().toString(),updateProfileBinding.etDatepik.getText().toString());
                        dialog.dismiss();
                        loadData();

//                        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int day) {
//                                myCalendar.set(Calendar.YEAR, year);
//                                myCalendar.set(Calendar.MONTH,month);
//                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//
//                                updateLabel();
//
//                            }

//                            private void updateLabel() {
//                                String myFormat = "EEE / MMM / d /yyyy";
//                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                                updateProfileBinding.etDatepik.setText(dateFormat.format(myCalendar.getTime()));
//
//                            }
//                        };

//                        updateProfileBinding.etDatepik.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//                                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),
//                                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                            }
//
//                        });

                    }
                });

                updateProfileBinding.cencle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        clickedPosition = position;
        return false;
    }


    //TODO Backpressed Method.......

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Exit ToDo");
        builder.setMessage("Sure you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }
}