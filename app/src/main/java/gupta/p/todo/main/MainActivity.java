package gupta.p.todo.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import gupta.p.todo.Pojo.ToDoPojo;
import gupta.p.todo.R;
import gupta.p.todo.adapter.MyArrayAdapter;
import gupta.p.todo.helper.MySqliteOpenHelper;
import gupta.p.todo.table.ToDo_tbl;

public class MainActivity extends AppCompatActivity {

    Button buttonDate, buttonTime;
    EditText editTextAbout;
    ImageButton imageButtonAdd;
    ListView listView;
    String timeET,dateET,todoET;
    MyArrayAdapter arrayAdapter;
    ArrayList<ToDoPojo> arrayListRem= new ArrayList<>();
    ArrayList<Integer> idArrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListeners();

        fetchDatabaseToArrayList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showListDialog(position);
            }
        });
    }


    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        imageButtonAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        editTextAbout = (EditText) findViewById(R.id.editTextAbout);
        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonTime = (Button) findViewById(R.id.buttonTime);
    }


    private void setListeners() {
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
                    Toast.makeText(MainActivity.this, "Add Reminder Name", Toast.LENGTH_SHORT).show();
                }
                else if(textDate.equals("Select Date")) {
                    Toast.makeText(MainActivity.this, "Add Date", Toast.LENGTH_SHORT).show();
                }
                else if(textTime.equals("Select Time")) {
                    Toast.makeText(MainActivity.this, "Add Time", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertDatabase();
                    arrayListRem.clear();
                    idArrayList.clear();
                    fetchDatabaseToArrayList();
                }
            }
        });
    }

    private void insertDatabase() {
        //value = todoET + "\n" + dateET + "\n" + timeET;

        MySqliteOpenHelper helper = new MySqliteOpenHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ToDo_tbl.TODO, todoET);
        cv.put(ToDo_tbl.DATE, dateET);
        cv.put(ToDo_tbl.TIME, timeET);

        long l = ToDo_tbl.insert(db, cv);
        if (l>0) {
            Toast.makeText(MainActivity.this, "Reminder Set", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
        }
        db.close();

        editTextAbout.setText("");
        buttonTime.setText("Select Time");
        buttonDate.setText("Select Date");
    }


    private void fetchDatabaseToArrayList() {
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        int k=0;
        Cursor cursor = ToDo_tbl.select(db, null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(0);
                String todo = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                char alpha=todo.charAt(0);
                char alphaU=Character.toUpperCase(alpha);

                ToDoPojo todopojo=new ToDoPojo();
                todopojo.setName(todo);
                todopojo.setDate(date);
                todopojo.setTime(time);
                todopojo.setAlpha(""+alphaU);
                if(k%2==0) {
                    todopojo.setImageRes(R.drawable.redback);
                    k++;
                }
                else {
                    todopojo.setImageRes(R.drawable.redback2);
                    k++;
                }
                arrayListRem.add(todopojo);
                idArrayList.add(id);
            }
            cursor.close();
            db.close();

            arrayAdapter = new MyArrayAdapter(MainActivity.this, R.layout.listview_back, arrayListRem);
            listView.setAdapter(arrayAdapter);
        }

    }


    private void showCalendar() {
        Calendar calendar= Calendar.getInstance();
        final int date=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                buttonTime.setText(""+hourOfDay+":"+minute);
                timeET=""+hourOfDay+":"+minute;
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
    private void showListDialog(final int pos)
    {

        String[] arr = {"Delete","Edit"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Options");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if(position==0) {
                    showDelDialog(pos);
                }
                else {
                    int id = idArrayList.get(pos);
                    Intent i=new Intent(MainActivity.this,Edit.class);
                    i.putExtra("ID",id);
                    startActivity(i);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void showDelDialog(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MySqliteOpenHelper helper=new MySqliteOpenHelper(MainActivity.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                int id = idArrayList.get(position);
                String whereCluase = ToDo_tbl.ID +" = '"+id+"'";

                int flag = ToDo_tbl.delete(db, whereCluase);
                if (flag > 0) {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    arrayListRem.clear();
                    idArrayList.clear();
                    fetchDatabaseToArrayList();
                    arrayAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "can't delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog deleteDialog=builder.create();
        deleteDialog.show();
    }
}
