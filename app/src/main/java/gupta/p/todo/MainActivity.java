package gupta.p.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonDate,buttonTime;
    EditText editTextAbout;
    ImageButton imageButtonAdd;
    ListView listView;
    String value;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView) findViewById(R.id.listView);
        imageButtonAdd= (ImageButton) findViewById(R.id.imageButtonAdd);
        editTextAbout = (EditText) findViewById(R.id.editTextAbout);
        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonTime = (Button) findViewById(R.id.buttonTime);

        final ArrayList<String> arrayListRem = new ArrayList<>();

        arrayAdapter= new ArrayAdapter(this,R.layout.list_view,arrayListRem);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                arrayListRem.remove(position);
                arrayAdapter.notifyDataSetChanged();

            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=editTextAbout.getText().toString();
                if(value.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter Value First", Toast.LENGTH_SHORT).show();
                }
                else {
                    arrayListRem.add(value);
                    arrayAdapter.notifyDataSetChanged();
                    editTextAbout.setText("");
                }
            }
        });


    }
}
