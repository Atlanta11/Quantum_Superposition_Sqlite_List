package enterprise.sample.com.quantum_superposition;

import android.provider.BaseColumns;

/**
 * Created by ginov on 4/12/2016.
 */

public class db {

    public static final String DB_NAME = "list.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}
