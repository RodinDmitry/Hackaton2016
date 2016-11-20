package explicitteam.miptevents;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import explicitteam.miptevents.Database.DatabasePackage;

import static explicitteam.miptevents.ApllUtils.writeDate;
import static explicitteam.miptevents.ApllUtils.writeTags;

/**
 * Created by Anatoly on 20.11.2016.
 */

public class EventAdapter extends BaseAdapter {

    private final List<DatabasePackage> list;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public EventAdapter(Context context, List<DatabasePackage> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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
        if (view == null) {
            view = (View) layoutInflater.inflate(R.layout.list_item, parent, false);
        }
        DatabasePackage item = getItemData(position);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(item.getTitle());
        textView = (TextView) view.findViewById(R.id.date);
        textView.setText(writeDate(item.getDate()));
        textView = (TextView) view.findViewById(R.id.description);
        textView.setText(item.getDescription());
        textView = (TextView) view.findViewById(R.id.tags);
        textView.setText(writeTags(item));

        return view;
    }

    private DatabasePackage getItemData(int position) {
        return (DatabasePackage) getItem(position);
    }
}
