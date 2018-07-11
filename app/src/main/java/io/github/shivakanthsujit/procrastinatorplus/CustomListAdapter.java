package io.github.shivakanthsujit.procrastinatorplus;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<tdItem> td;


    View row;


    public CustomListAdapter(Activity context, ArrayList<tdItem> t) {

        super(context, R.layout.todo, t);
        this.context = context;
        this.td = t;

    }
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return td.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        row = view;
        tdHolder holder = null;
        if(row == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.todo, null, true);

            holder = new tdHolder();
            holder.tdH = row.findViewById(R.id.head);
            holder.tdS = row.findViewById(R.id.sub);
            holder.tdC = row.findViewById(R.id.check);

            holder.del = row.findViewById(R.id.del);
            holder.tdL = row.findViewById(R.id.lo);
            LinearLayout li = row.findViewById(R.id.lo);

            holder.tdH.setText(td.get(position).head);
            holder.tdS.setText(td.get(position).sub);
            if (td.get(position).done == 0) {
                holder.tdC.setChecked(false);
                li.setBackgroundColor(Color.WHITE);
            } else if (td.get(position).done == 1) {
                holder.tdC.setChecked(false);
                li.setBackgroundColor(Color.GRAY);
            }
            row.setTag(holder);
        }
        else
        {
            holder = (tdHolder) row.getTag();
        }

        holder.tdH.setOnClickListener(new View.OnClickListener() {
            private int pos = position;

            @Override
            public void onClick(View v) {
                TextView t = (TextView)v;
                if(t.isSelected()){
                    t.setSelected(false);
                    t.setEnabled(true);
                    t.requestFocus();


                }
            }
        });
        holder.tdH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private int pos = position;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false && td.size()!=0)
                {

                    TextView t = (TextView)v;
                    String tt = t.getText().toString();
                    td.get(pos).head = tt;
                    storeDB(td);
                }}
        });
        holder.tdS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private int pos = position;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false && td.size()!=0)
                {

                TextView t = (TextView)v;
                String tt = t.getText().toString();
                td.get(pos).sub = tt;
                storeDB(td);
            }}
        });
        holder.tdS.setOnClickListener(new View.OnClickListener() {
            private int pos = position;
            @Override
            public void onClick(View v) {
                TextView t = (TextView)v;
                if(t.isSelected()){
                    t.setSelected(false);
                    t.setEnabled(true);
                    t.requestFocus();


                }
            }
        });


        final CheckBox c = holder.tdC;
        holder.tdC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            private int pos = position;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c.isChecked())
                {
                    LinearLayout li = row.findViewById(R.id.lo);
                    Toast.makeText(context,"Good job." , Toast.LENGTH_SHORT).show();
                    td.get(pos).done = 1;
                    storeDB(td);
                    li.setBackgroundColor(Color.LTGRAY);
                }
                boolean t = (c.isChecked());
                if(t==false)
                {
                    LinearLayout li = row.findViewById(R.id.lo);
                    Toast.makeText(context,"What are you doing?." , Toast.LENGTH_SHORT).show();
                    td.get(pos).done = 0;
                    storeDB(td);
                    li.setBackgroundColor(Color.WHITE);
                }

            }
        });

        Button b2 = (Button) row.findViewById(R.id.del);
        b2.setTag(position);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int posdel = (int)arg0.getTag();
                Toast.makeText(context, td.get(posdel).head+ " " + posdel+"", Toast.LENGTH_SHORT).show();
                td.remove(posdel);

                storeDB(td);
                CustomListAdapter.this.notifyDataSetChanged();
                CustomListAdapter.this.notifyDataSetInvalidated();



            }
        });


        return row;



    }

    public void storeDB(ArrayList<tdItem> t)
    {
        SQLiteDatabase DB = this.context.openOrCreateDatabase("tasks.db",0, null);

        DB.execSQL("CREATE TABLE IF NOT EXISTS task (head VARCHAR(200), sub VARCHAR(200), is_done INT)");
        int i;
        DB.execSQL("delete from task");
        for(i=0;i<t.size();++i)
        {
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
        SQLiteDatabase DB = this.context.openOrCreateDatabase("tasks.db", MODE_PRIVATE, null);
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
}
 class tdHolder {
    EditText tdH;
    EditText tdS;
    CheckBox tdC;
    LinearLayout tdL;
    Button del;
    int pos;
}