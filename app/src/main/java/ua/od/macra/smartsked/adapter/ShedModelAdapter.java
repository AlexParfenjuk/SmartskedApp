package ua.od.macra.smartsked.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ua.od.macra.smartsked.R;
import ua.od.macra.smartsked.Strings;
import ua.od.macra.smartsked.models.list.ListEntry;


public class ShedModelAdapter extends ArrayAdapter<ListEntry> {

    private LayoutInflater mInflater;
    private Activity mContext;

    public ShedModelAdapter(Activity context) {
        super(context, R.layout.ssked_dayshed_list_item, new ArrayList<ListEntry>());
        mContext = context;
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
            holder.dayList.setDividerHeight(0);
            holder.headerDateText = (TextView) view.findViewById(R.id.header_date_text);
            holder.headerDayText = (TextView) view.findViewById(R.id.header_day_text);
            holder.headerLayout = (LinearLayout) view.findViewById(R.id.header_layout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Calendar cal = Calendar.getInstance();
        int currDOW = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(entry.getDate());
        holder.headerDateText.setBackgroundColor(Strings.COLOR_MAIN);
        holder.headerDayText.setBackgroundColor(Strings.COLOR_MAIN);
        holder.headerLayout.setBackgroundColor(Strings.COLOR_MAIN);
        boolean current = cal.get(Calendar.DAY_OF_WEEK) == currDOW;
        if (current) {
            holder.headerDateText.setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
            holder.headerDayText.setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
            holder.headerLayout.setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
        }
        holder.headerDateText.setText(entry.getDateString());
        holder.headerDayText.setText(entry.getDOWString());
        holder.dayList.setAdapter(new ShedListAdapter(mContext,
                entry.getEventPairList(),
                entry.getDate()));
        return view;
    }

    static class ViewHolder {
        public TextView headerDateText, headerDayText;
        public ListView dayList;
        public LinearLayout headerLayout;
    }
}
