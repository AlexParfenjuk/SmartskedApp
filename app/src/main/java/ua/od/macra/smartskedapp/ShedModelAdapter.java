package ua.od.macra.smartskedapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.od.macra.smartskedapp.models.list.ListEntry;


public class ShedModelAdapter extends ArrayAdapter<ListEntry> {

    private LayoutInflater mInflater;

    public ShedModelAdapter(Activity context) {
        super(context, R.layout.ssked_dayshed_list_item, new ArrayList<ListEntry>());
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        ListEntry entry = getItem(position);
        if (view == null) {
            view = mInflater.inflate(R.layout.ssked_dayshed_list_item, parent, false);
            holder = new ViewHolder();
            holder.dayList = (ListView) view.findViewById(R.id.day_list);
            holder.headerDateText = (TextView) view.findViewById(R.id.header_date_text);
            holder.headerDayText = (TextView) view.findViewById(R.id.header_day_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.headerDateText.setText(entry.getDay().date);
        holder.headerDayText.setText(entry.getDay().day);
        holder.dayList.setAdapter(entry.getListAdapter());
        return view;
    }

    static class ViewHolder {
        public TextView headerDateText, headerDayText;
        public ListView dayList;
    }
}
