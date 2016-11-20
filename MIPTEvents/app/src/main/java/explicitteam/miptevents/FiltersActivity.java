package explicitteam.miptevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import explicitteam.miptevents.Database.DatabasePackage;

public class FiltersActivity extends AppCompatActivity {

    List<Integer> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Intent intent = getIntent();

        ListView listView = (ListView) findViewById(R.id.list_view);
       /* EventAdapter adapter = new EventAdapter(this, initListTest());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                intent.putExtra("position", position);
                DatabasePackage item = (DatabasePackage)parent.getItemAtPosition(position);
                intent.putExtra("item", item);
                System.out.println("thowing item" + (parent.getItemAtPosition(position) != null));
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);*/
    }
}
