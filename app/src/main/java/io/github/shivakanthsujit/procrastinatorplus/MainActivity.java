package io.github.shivakanthsujit.procrastinatorplus;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Button add;
    int listpos;
    ArrayList<tdItem> tdList= new ArrayList<tdItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        add = findViewById(R.id.add);
        final CustomListAdapter whatever = new CustomListAdapter(this, tdList);
        list.setAdapter(whatever);
        /*@Override
        public void onItemClick(AdapterView adapterView, View view, int position, long id) {

            long viewId = view.getId();

            if (viewId == R.id.someName) {
                Toast.makeText(getActivity(), "someName item clicked", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "ListView clicked: " + id, Toast.LENGTH_SHORT).show();

            }
        }
*/

        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tdItem temp = new tdItem();
                tdList.add(temp);
                whatever.notifyDataSetChanged();
            }
        });





        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                tdItem temp = tdList.get(position);
                listpos = position;
            }
        });*/

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}

