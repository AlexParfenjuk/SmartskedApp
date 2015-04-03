package ua.od.macra.smartsked.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.od.macra.smartsked.R;
import ua.od.macra.smartsked.Strings;
import ua.od.macra.smartsked.models.json.Lesson;
import ua.od.macra.smartsked.models.json.ShedTask;

public class ShedListAdapter extends BaseAdapter {

    private static final int TYPE_NOPAIR = 0;
    private static final int TYPE_PAIR = 1;
    private List<Pair<Integer, ShedTask>> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Activity mContext;
    private Date mDate;

    public ShedListAdapter(Activity context, List<Pair<Integer, ShedTask>> tasks, Date date) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = tasks;
        mDate = date;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).second instanceof Lesson ? TYPE_PAIR : TYPE_NOPAIR;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        String[] breakTimes = mContext.getResources().getStringArray(R.array.break_time);
        String[] pairTimes = mContext.getResources().getStringArray(R.array.pair_time);

        List<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile("\\d{1,2}").matcher(pairTimes[position]);
        while (m.find()) {
            matches.add(m.group(0));
        }
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(matches.get(0)));
        c.set(Calendar.MINUTE, Integer.valueOf(matches.get(1)));
        c.set(Calendar.SECOND, 0);
        Date dateFrom = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(matches.get(2)));
        c.set(Calendar.MINUTE, Integer.valueOf(matches.get(3)));
        Date dateTo = c.getTime();
        c = Calendar.getInstance();
        Date currTime = new Date(c.getTimeInMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        boolean current = (currTime.after(dateFrom) && currTime.before(dateTo) && (c.get(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK)));

        if (getItemViewType(position) == TYPE_PAIR) {
            Lesson lesson = (Lesson) mData.get(position).second;
            view = mInflater.inflate(R.layout.ssked_pair_list_item, null, false);
            if (current){
                view.findViewById(R.id.pair_time_text).setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
                view.findViewById(R.id.pair_aud_text).setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
                view.findViewById(R.id.pair_discip_text).setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
                view.findViewById(R.id.pair_type_text).setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
                view.findViewById(R.id.pair_number_text).setBackgroundColor(Strings.COLOR_ACTIVE_PAIR);
                ((TextView)view.findViewById(R.id.pair_number_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_time_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_aud_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_discip_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_type_text)).setTextColor(Color.WHITE);
            }
            ((TextView) view.findViewById(R.id.pair_time_text)).setText(pairTimes[position]);
            ((TextView) view.findViewById(R.id.pair_aud_text)).setText(lesson.place);
            ((TextView) view.findViewById(R.id.pair_discip_text)).setText(lesson.name);
            ((TextView) view.findViewById(R.id.pair_number_text)).setText(String.valueOf(mData.get(position).first));
            ((TextView) view.findViewById(R.id.pair_type_text)).setText(lesson.type);
            ((TextView) view.findViewById(R.id.break_time_text)).setText(breakTimes[position]);
            if (position == 5) view.findViewById(R.id.breakLayout).setVisibility(View.GONE);
        } else {
            view = mInflater.inflate(R.layout.ssked_no_pair_list_item, null, false);
            if (current){
                view.findViewById(R.id.pair_time_text).setBackgroundColor(Strings.COLOR_ACTIVE_NOPAIR);
                view.findViewById(R.id.pair_nopair_text).setBackgroundColor(Strings.COLOR_ACTIVE_NOPAIR);
                view.findViewById(R.id.pair_number_text).setBackgroundColor(Strings.COLOR_ACTIVE_NOPAIR);
                ((TextView)view.findViewById(R.id.pair_number_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_time_text)).setTextColor(Color.WHITE);
                ((TextView)view.findViewById(R.id.pair_nopair_text)).setTextColor(Color.WHITE);
            }
            ((TextView) view.findViewById(R.id.pair_time_text)).setText(pairTimes[position]);
            ((TextView) view.findViewById(R.id.pair_number_text)).setText(String.valueOf(position + 1));
            ((TextView) view.findViewById(R.id.break_time_text)).setText(breakTimes[position]);
            if (position == 5) view.findViewById(R.id.breakLayout).setVisibility(View.GONE);
        }
        return view;
    }
}