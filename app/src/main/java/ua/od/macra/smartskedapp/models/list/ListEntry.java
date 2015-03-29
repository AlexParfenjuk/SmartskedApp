package ua.od.macra.smartskedapp.models.list;

import ua.od.macra.smartskedapp.ShedListAdapter;
import ua.od.macra.smartskedapp.models.json.Day;

public class ListEntry {
    private Day day;
    private ShedListAdapter listAdapter;

    public ListEntry(Day day, ShedListAdapter listAdapter) {
        this.day = day;
        this.listAdapter = listAdapter;
    }

    public Day getDay() {
        return day;
    }

    public ShedListAdapter getListAdapter() {
        return listAdapter;
    }
}
