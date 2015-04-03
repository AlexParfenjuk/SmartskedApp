package ua.od.macra.smartsked;

import android.graphics.Color;

public interface Strings {
    String EXTRA_JSON_FILENAME = Strings.class.getPackage().getName() + "EXTRA_JSON_FILENAME";
    String EXTRA_GROUP_NAME = Strings.class.getPackage().getName() + "EXTRA_GROUP_NAME";

    String PAIR_ID = "id";
    String PAIR_GROUP_ID = "group_id";
    String PAIR_NAME = "name";
    String PAIR_TYPE = "type";
    String PAIR_PLACE = "place";
    String PAIR_TEACHER = "teacher_name";
    String PAIR_INDEX = "index";

    String PREF_INST_INDEX = "PREF_INST_INDEX";
    String PREF_FACULT_INDEX = "PREF_FACULT_INDEX";
    String PREF_GROUP_INDEX = "PREF_GROUP_INDEX";

    String FILE_NAME_INST = "institutes_list.json";
    String FILE_NAME_FACULT = "faculties_list.json";
    String FILE_NAME_GROUPS= "groups_list.json";
    int COLOR_ACTIVE_PAIR = Color.parseColor("#6AB649");
    int COLOR_ACTIVE_NOPAIR = Color.parseColor("#B0E77E");
    int COLOR_MAIN = Color.parseColor("#1d242c");
}
