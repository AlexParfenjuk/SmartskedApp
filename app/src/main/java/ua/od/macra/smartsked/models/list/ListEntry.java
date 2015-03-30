package ua.od.macra.smartsked.models.list;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ua.od.macra.smartsked.R;
import ua.od.macra.smartsked.models.json.Pair;

public class ListEntry {
    private Date date;
    private List<Pair> taskList;
    private Context mContext;

    public ListEntry(Context context, Date date, List<Pair> taskList) {
        this.date = date;
        this.taskList = taskList;
        mContext = context;
    }

    public String getDateString() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
    }

    public String getDOWString() {
        String[] weekDays = mContext.getResources().getStringArray(R.array.weekdays);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 2];
    }

    public List<Pair> getEventList() {
        return taskList;
    }
}
