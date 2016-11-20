package explicitteam.miptevents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import explicitteam.miptevents.Database.DatabasePackage;

import static explicitteam.miptevents.ApllUtils.writeDate;
import static explicitteam.miptevents.ApllUtils.writeTags;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.scroll_act);
        DatabasePackage item = getIntent().getParcelableExtra("item");

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(item.getTitle());
        /*textView = (TextView) findViewById(R.id.date);
        textView.setText(writeDate(item.getDate()));
        textView = (TextView) findViewById(R.id.main_text);
        textView.setText(item.getDescription());
        textView = (TextView) findViewById(R.id.tags);
        textView.setText(writeTags(item));*/

    }
}
