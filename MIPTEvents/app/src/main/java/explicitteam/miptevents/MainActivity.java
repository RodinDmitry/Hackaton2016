package explicitteam.miptevents;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import explicitteam.miptevents.Database.CDatabase;
import explicitteam.miptevents.Database.DatabasePackage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isInFavmMde = false;
    ArrayList<String> tags;
    List<DatabasePackage> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AsyncTask<Object, Object, List<DatabasePackage>> task = new AsyncTask<Object, Object, List<DatabasePackage>>(){
            @Override
            protected List<DatabasePackage> doInBackground(Object... params) {
                return initListTest();
            }
        }.execute(1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initTags();
        ListView listView = (ListView) findViewById(R.id.list_view);
        try {
            itemList = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        EventAdapter adapter = new EventAdapter(this, itemList);
        listView.setOnItemClickListener(new OnItemClickListener() {
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
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> temp = intent.getStringArrayListExtra("tags");
            if (temp != null) {
                tags = temp;
            }
            Boolean favs;
            favs = intent.getBooleanExtra("favs", false);
            if (favs != null) {
                if (favs) {

                }
            }
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favs) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("favs", true);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_filters) {
            Intent intent = new Intent(this, FiltersActivity.class);
            intent.putExtra("tags", tags);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<DatabasePackage> initListTest() {
        List<DatabasePackage> list ;

        try (CDatabase dbase = new CDatabase("VasyaPuk")) {
            if (dbase != null) {

                list = dbase.getList();
                if (list != null) {
                    return list;
                } else {
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();

            /*list.add(new DatabasePackage(1, "Хакатон на физтехе", "Самый луучший хакатон, на 24 часа." +
                " далее следует длиииииииииииииииииииииииииииииииииииииииииииииииииииииии" +
                "ииииииииииииииииииииииииииииииииииииииииииииииииииинное описание",
                "БФК 112", new Date(System.currentTimeMillis()), 1, 1, 1, "лул"));

        list.add(new DatabasePackage(2, "Хакатон на физтехе", "Самый луучший хакатон, на 24 часа",
                "БФК 112", new Date(System.currentTimeMillis()), 1, 1, 1, "лул"));

        list.add(new DatabasePackage(3, "Хакатон на физтехе", "Самый луучший хакатон, на 24 часа",
                "БФК 112", new Date(System.currentTimeMillis()), 1, 1, 1, "лул"));*/

    }

    private void initTags() {
        tags = new ArrayList<>();
    }
}
