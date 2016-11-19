package explicitteam.miptevents;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import explicitteam.miptevents.Database.DatabasePackage;
import explicitteam.miptevents.R;

/**
 * Created by Anatoly on 19.11.2016.
 */

public class EventListAdapter extends BaseAdapter {
    private final List<DatabasePackage> list;
    private final LayoutInflater layoutInflater;

    public EventListAdapter(List<DatabasePackage> list, Context context) {
        this.list = list;
        this.layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view != null) {
            view = layoutInflater.inflate(R.layout.mylist_layout, parent, false);
        }
        DatabasePackage item = getDatabasePackage(position);
        TextView txtView = (TextView) view.findViewById(R.id.title);
        txtView.setText(item.getTitle());
        txtView = (TextView) view.findViewById(R.id.date);
        txtView.setText(item.getDate().toString());
        txtView = (TextView) view.findViewById(R.id.description);
        txtView.setText(item.getDescription());
        txtView = (TextView) view.findViewById(R.id.tags);
        txtView.setText(item.getTagType() + ' ' + item.getTagTheme()
                + ' ' + item.getTagDepartment());
        return view;
    }

    private DatabasePackage getDatabasePackage(int position) {
        return (DatabasePackage) getItem(position);
    }
}
