package explicitteam.miptevents;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import explicitteam.miptevents.Database.CDatabase;
import explicitteam.miptevents.Database.DatabasePackage;

import static explicitteam.miptevents.ApllUtils.getLoginString;

public class FiltersActivity extends AppCompatActivity {

    ArrayList<String> tags;
    FilterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Intent intent = getIntent();
        tags = intent.getStringArrayListExtra("tags");
        adapter = new FilterAdapter(this, new ArrayList<Pair<String, Boolean>>());
        ListView listView = (ListView) findViewById(R.id.list_view_filters);
        AsyncTask<Void, Void, ArrayList<Pair<String, Boolean>>> task =
                new AsyncTask<Void, Void, ArrayList<Pair<String, Boolean>>>(){
            @Override
            protected ArrayList<Pair<String, Boolean>> doInBackground(Void... params) {
                CDatabase dbase = new CDatabase(getLoginString());
                Set<String> st = dbase.getTags();
                ArrayList<Pair<String, Boolean>> res = new ArrayList<Pair<String, Boolean>>();
                for (String tag: st) {
                    if (tags.contains(tag)) {
                        res.add(new Pair<>(tag, true));
                    } else {
                        res.add(new Pair<>(tag, false));
                    }
                }
                dbase.close();
                return res;
            }
        }.execute();
        try {
            adapter = new FilterAdapter(this, task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        tags = adapter.getNewTagSet();
        intent.putStringArrayListExtra("tags", tags);
        startActivity(intent);
        super.onBackPressed();
    }
}
