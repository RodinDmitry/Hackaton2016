package explicitteam.miptevents;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Anatoly on 20.11.2016.
 */

public class FilterAdapter extends BaseAdapter {
    private final ArrayList<Pair<String, Boolean>> list;
    private final LayoutInflater inflater;

    public FilterAdapter(Context context, ArrayList<Pair<String, Boolean>> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = (View) inflater.inflate(R.layout.filter_itemlay, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.tagtext);
        textView.setText('#' + list.get(position).first);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkBox.setChecked(list.get(position).second);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Pair<String, Boolean> item = list.get(position);
                list.add(position, new Pair(item.first, new Boolean(isChecked)));
                System.out.println("changed");
            }
        });
        return view;
    }

    public ArrayList<String> getNewTagSet() {
        ArrayList<String> tags = new ArrayList<String>();
        for (Pair<String , Boolean> tag: list) {
            if (tag.second) {
                tags.add(tag.first);
            }
        }
        return tags;
    }
}
