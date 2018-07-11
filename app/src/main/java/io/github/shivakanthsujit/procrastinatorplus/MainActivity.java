package io.github.shivakanthsujit.procrastinatorplus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Button add;
    int listpos;
    ArrayList<tdItem> tdList = new ArrayList<tdItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Press the check mark on the right to save your task.", Toast.LENGTH_LONG).show();
        list = findViewById(R.id.list);
        add = findViewById(R.id.add);
        int k = checkDB();
        if (k == 0) {
            storeDB(tdList);
            Toast.makeText(MainActivity.this, "First Task", Toast.LENGTH_LONG).show();
        } else {
            tdList = getDB();
            Toast.makeText(MainActivity.this, tdList.size()+" tasks created", Toast.LENGTH_LONG).show();
        }
        final CustomListAdapter whatever = new CustomListAdapter(this, tdList);
        list.setAdapter(whatever);

        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tdItem temp = new tdItem();
                if (checkDB() == 0)
                {
                    tdList.add(temp);
                    whatever.notifyDataSetChanged();
                    //Toast.makeText(MainActivity.this, "First Task", Toast.LENGTH_LONG).show();
                }
                else
                {
                    tdList = getDB();
                    tdList.add(temp);
                    whatever.notifyDataSetChanged();
                    //Toast.makeText(MainActivity.this, tdList.size()+" tasks created", Toast.LENGTH_SHORT).show();
                }

                storeDB(tdList);
                final CustomListAdapter whatever = new CustomListAdapter(MainActivity.this, tdList);
                list.setAdapter(whatever);
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void storeDB(ArrayList<tdItem> t) {
        SQLiteDatabase DB = openOrCreateDatabase("tasks.db", MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS task (head VARCHAR(200), sub VARCHAR(200), is_done INT)");
        int i;
        DB.execSQL("delete from task");
        for (i = 0; i < t.size(); ++i) {
            ContentValues row1 = new ContentValues();
            row1.put("head", t.get(i).head);
            row1.put("sub", t.get(i).sub);
            row1.put("is_done", t.get(i).done);
            DB.insert("task", null, row1);
        }
        DB.close();
    }

    public ArrayList<tdItem> getDB() {
        ArrayList<tdItem> t = new ArrayList<tdItem>();
        SQLiteDatabase DB = openOrCreateDatabase("tasks.db", MODE_PRIVATE, null);
        Cursor myCursor = DB.rawQuery("select * from task", null);
        int i = 0;
        tdItem temp = null;
        while (myCursor.moveToNext()) {
            temp = new tdItem();
            temp.head = myCursor.getString(0);
            temp.sub = myCursor.getString(1);
            temp.done = myCursor.getInt(2);
            t.add(temp);
            i++;
        }

        myCursor.close();
        DB.close();
        return t;
    }

    public int checkDB() {
        SQLiteDatabase DB = openOrCreateDatabase("tasks.db", MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS task (head VARCHAR(200), sub VARCHAR(200), is_done INT)");

        Cursor myCursor = DB.rawQuery("select * from task", null);
        int i = 0;

        while (myCursor.moveToNext()) {
            i++;
        }

        myCursor.close();
        DB.close();
        return i;
    }

    public static void tt()
    {

    }

}