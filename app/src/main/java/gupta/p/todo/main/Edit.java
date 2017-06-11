package gupta.p.todo.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import gupta.p.todo.R;
import gupta.p.todo.helper.MySqliteOpenHelper;
import gupta.p.todo.table.ToDo_tbl;


public class Edit extends AppCompatActivity {
    Button buttonDate, buttonTime;
    EditText editTextAbout;
    ImageButton imageButtonAdd;
    String timeET,dateET,todoET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();

        Intent i=getIntent();
        int id=i.getIntExtra("ID",0);

        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = ToDo_tbl.select(db, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idData = cursor.getInt(0);
                if (idData == id) {
                    todoET = cursor.getString(1);
                    dateET = cursor.getString(2);
                    timeET = cursor.getString(3);
                    break;
                }
            }
            cursor.close();
            db.close();
        }
        editTextAbout.setText(todoET);
        buttonDate.setText(dateET);
        buttonTime.setText(timeET);
        setListeners(id);
    }


    private void init() {
        imageButtonAdd = (ImageButton) findViewById(R.id.imageButtonAddU);
        editTextAbout = (EditText) findViewById(R.id.editTextAboutU);
        buttonDate = (Button) findViewById(R.id.buttonDateU);
        buttonTime = (Button) findViewById(R.id.buttonTimeU);
    }


    private void setListeners(final int id) {
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClocK();
            }
        });
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoET = editTextAbout.getText().toString();
                String textTime=buttonTime.getText().toString();
                String textDate=buttonDate.getText().toString();
                if(todoET.equals("")) {
                    Toast.makeText(Edit.this, "Add Reminder Name", Toast.LENGTH_SHORT).show();
                }
                else if(textDate.equals("Select Date")) {
                    Toast.makeText(Edit.this, "Add Date", Toast.LENGTH_SHORT).show();
                }
                else if(textTime.equals("Select Time")) {
                    Toast.makeText(Edit.this, "Add Time", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateDatabase(id);
                }
            }
        });
    }

    private void updateDatabase(int id) {


        MySqliteOpenHelper helper = new MySqliteOpenHelper(Edit.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ToDo_tbl.TODO, todoET);
        cv.put(ToDo_tbl.DATE, dateET);
        cv.put(ToDo_tbl.TIME, timeET);

        String whereClause = ToDo_tbl.ID +" = '"+id+"'";
        int l = ToDo_tbl.update(db, whereClause,cv);
        if (l>0) {
            Toast.makeText(Edit.this, "Updated", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Edit.this,MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        else {
            Toast.makeText(Edit.this, "Try Again", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void showCalendar() {
        Calendar calendar= Calendar.getInstance();
        final int date=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(Edit.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                buttonDate.setText(""+dayOfMonth+"-"+month+"-"+year);
                dateET=""+dayOfMonth+"-"+month+"-"+year;
            }
        },year,month,date);
        datePickerDialog.show();
    }
    private void showClocK() {
        Calendar calendar= Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(Edit.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                buttonTime.setText(""+hourOfDay+":"+minute);
                timeET=""+hourOfDay+":"+minute;
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
}

