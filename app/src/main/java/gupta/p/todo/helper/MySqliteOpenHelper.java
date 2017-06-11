package gupta.p.todo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import gupta.p.todo.table.ToDo_tbl;

/**
 * Created by pgupta on 9/6/17.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper{

    Context context;
    public MySqliteOpenHelper(Context context) {
        super(context, "mydb.db", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "on Create", Toast.LENGTH_SHORT).show();
        ToDo_tbl.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "on Upgrade", Toast.LENGTH_SHORT).show();
        ToDo_tbl.updateTable(db);
    }
}
