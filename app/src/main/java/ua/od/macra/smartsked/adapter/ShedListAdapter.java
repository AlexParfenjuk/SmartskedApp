package ua.od.macra.smartsked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartsked.R;
import ua.od.macra.smartsked.models.json.Break;
import ua.od.macra.smartsked.models.json.NoPair;
import ua.od.macra.smartsked.models.json.Pair;
import ua.od.macra.smartsked.models.json.ShedTask;

public class ShedListAdapter extends BaseAdapter {

    private static final int TYPE_NOPAIR = 0;
    private static final int TYPE_PAIR = 1;
    private List<Pair> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public ShedListAdapter(Context context, List<Pair> tasks) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = tasks;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? TYPE_NOPAIR : TYPE_PAIR;
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
        LinearLayout view = new LinearLayout(mContext);
        switch (getItemViewType(position)) {
            case TYPE_BREAK: {
                Break br = (Break) mData.get(position);
                mInflater.inflate(R.layout.ssked_break_list_item, view);
                break;
            }
            case TYPE_PAIR: {
                Pair pair = (Pair) mData.get(position);
                mInflater.inflate(R.layout.ssked_pair_list_item, view);
                ((TextView) view.findViewById(R.id.pair_time_text)).setText(pair.getTimeString());
                ((TextView) view.findViewById(R.id.pair_aud_text)).setText(pair.aud);
                ((TextView) view.findViewById(R.id.pair_discip_text)).setText(pair.name);
                ((TextView) view.findViewById(R.id.pair_number_text)).setText(pair.number);
                ((TextView) view.findViewById(R.id.pair_type_text)).setText(pair.type);
                ((TextView) view.findViewById(R.id.break_time_text)).setText(br.getTimeString());
                break;
            }
            case TYPE_NOPAIR: {
                NoPair noPair = (NoPair) mData.get(position);
                mInflater.inflate(R.layout.ssked_no_pair_list_item, view);
                ((TextView) view.findViewById(R.id.pair_time_text)).setText(noPair.getTimeString());
                ((TextView) view.findViewById(R.id.pair_number_text)).setText(noPair.number);
                break;
            }
        }
        return view;
    }
}
