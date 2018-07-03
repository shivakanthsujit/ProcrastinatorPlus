package io.github.shivakanthsujit.procrastinatorplus;

import android.app.Activity;
import android.content.Context;
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
            holder.con = row.findViewById(R.id.confirm);
            holder.del = row.findViewById(R.id.del);
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
                TextView t = (TextView)v;
                String tt = t.getText().toString();
                td.get(pos).head = tt;
                //Toast.makeText(context,tt , Toast.LENGTH_SHORT).show();
            }
        });
        holder.tdS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private int pos = position;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false)
                {

                TextView t = (TextView)v;
                String tt = t.getText().toString();
                td.get(pos).sub = tt;
                //Toast.makeText(context,tt , Toast.LENGTH_SHORT).show();
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
                    li.setBackgroundColor(Color.GRAY);
                }
                boolean t = (c.isChecked());
                if(t==false)
                {
                    LinearLayout li = row.findViewById(R.id.lo);
                    li.setBackgroundColor(Color.WHITE);
                }

            }
        });

        Button b2 = (Button) row.findViewById(R.id.del);
        b2.setTag(position);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int pos = (int)arg0.getTag();
                td.remove(pos);
                CustomListAdapter.this.notifyDataSetChanged();            }
        });

        Button b1 = (Button) row.findViewById(R.id.confirm);
        b1.setTag(position);
        holder.con.setOnClickListener(new View.OnClickListener() {
            private int pos = position;

            @Override
            public void onClick(View arg0) {
                int pos = (int)arg0.getTag();

                String temp = td.get(pos).head ;

                String temp2 = "Task " + td.get(pos).head + " has been created";
                Toast.makeText(context,temp2 , Toast.LENGTH_SHORT).show();
                }
        });

        return row;



    }


}
 class tdHolder {
    EditText tdH;
    EditText tdS;
    CheckBox tdC;
    Button con;
    Button del;
    int pos;
}