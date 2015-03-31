package ua.od.macra.smartsked.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartsked.R;
import ua.od.macra.smartsked.models.json.Lesson;
import ua.od.macra.smartsked.models.json.ShedTask;

public class ShedListAdapter extends BaseAdapter {

    private static final int TYPE_NOPAIR = 0;
    private static final int TYPE_PAIR = 1;
    private List<Pair<Integer, ShedTask>> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public ShedListAdapter(Context context, List<Pair<Integer, ShedTask>> tasks) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = tasks;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).second instanceof Lesson ? TYPE_PAIR: TYPE_NOPAIR;
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
        View view = convertView;
        String[] breakTimes = mContext.getResources().getStringArray(R.array.break_time);
        String[] pairTimes = mContext.getResources().getStringArray(R.array.pair_time);
        switch (getItemViewType(position)) {
            case TYPE_PAIR: {
                Lesson lesson = (Lesson) mData.get(position).second;
                view = mInflater.inflate(R.layout.ssked_pair_list_item, null, false);
                ((TextView) view.findViewById(R.id.pair_time_text)).setText(pairTimes[position]);
                ((TextView) view.findViewById(R.id.pair_aud_text)).setText(lesson.place);
                ((TextView) view.findViewById(R.id.pair_discip_text)).setText(lesson.name);
                ((TextView) view.findViewById(R.id.pair_number_text)).setText(String.valueOf(mData.get(position).first));
                ((TextView) view.findViewById(R.id.pair_type_text)).setText(lesson.type);
                ((TextView) view.findViewById(R.id.break_time_text)).setText(breakTimes[position]);
                break;
            }
            case TYPE_NOPAIR: {
                view = mInflater.inflate(R.layout.ssked_no_pair_list_item, null, false);
                ((TextView) view.findViewById(R.id.pair_time_text)).setText(pairTimes[position]);
                ((TextView) view.findViewById(R.id.pair_number_text)).setText(String.valueOf(position + 1));
                ((TextView) view.findViewById(R.id.break_time_text)).setText(breakTimes[position]);
                break;
            }
        }
        return view;
    }
}
