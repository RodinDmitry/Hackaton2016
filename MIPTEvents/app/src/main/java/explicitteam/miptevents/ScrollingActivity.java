package explicitteam.miptevents;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import explicitteam.miptevents.Database.CDatabase;
import explicitteam.miptevents.Database.DatabasePackage;

import static explicitteam.miptevents.ApllUtils.getLoginString;
import static explicitteam.miptevents.ApllUtils.writeDate;
import static explicitteam.miptevents.ApllUtils.writeTags;
import static explicitteam.miptevents.R.string.title_activity_scrolling;

public class ScrollingActivity extends AppCompatActivity {
    private DatabasePackage item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View view = findViewById(R.id.scroll_act);
        item = getIntent().getParcelableExtra("item");
        Long locId = item.getLocationId();
        AsyncTask<Long, Object, String> task = new AsyncTask<Long, Object, String>(){
            @Override
            protected String doInBackground(Long... params) {
                CDatabase dbase = new CDatabase(getLoginString());
                String res = dbase.getLocation(params[0]);
                System.out.println(res);
                System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                return res;
            }
        }.execute(locId);


        setTitle(item.getEventName());
        TextView textView = (TextView) findViewById(R.id.date);
        textView.setText(writeDate(item.getEventStartDate(), item.getEventStartTime()));
        textView = (TextView) findViewById(R.id.place);
        try {
            textView.setText(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.main_text);
        textView.setText(item.getEventContent());
        textView = (TextView) findViewById(R.id.tags);
        textView.setText(writeTags(item));
        Button butt = (Button) findViewById(R.id.subscribe);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerOnClick(v);
            }
        });
    }

    public void handlerOnClick(View view) {

    }
}
