package gupta.p.todo.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by pgupta on 9/6/17.
 */

public class ToDo_tbl {
    public static String TABLE_NAME = "todo_tbl";
    public static String ID = "id";
    public static String TODO = "todo";
    public static String DATE = "date";
    public static String TIME = "time";

    public static String createQuery="CREATE TABLE \"main\".\"todo_tbl\" (\n" +
            "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    \"todo\" TEXT,\n" +
            "    \"date\" TEXT,\n" +
            "    \"time\" TEXT\n" +
            ");\n";
    public static void createTable(SQLiteDatabase db) {
        db.execSQL(createQuery);
        Log.d("1234", "createTable: table created");
    }
    public static void updateTable(SQLiteDatabase db) {
        String sql = "drop table if exists "+TABLE_NAME;
        db.execSQL(sql);
        Log.d(TAG, "updateTable: table updated");
        createTable(db);
    }
    public static long insert (SQLiteDatabase db, ContentValues cv) {
        return db.insert(TABLE_NAME, null, cv);
    }
    public static Cursor select(SQLiteDatabase db,String selection) {
        return db.query(TABLE_NAME, null, selection, null, null, null, null, null);
    }
    public static int delete(SQLiteDatabase db,String whereClause) {
        return db.delete(TABLE_NAME, whereClause, null);
    }
    public static int update(SQLiteDatabase db,String whereClause,ContentValues cv) {
        return db.update(TABLE_NAME,cv,whereClause,null);
    }
}
