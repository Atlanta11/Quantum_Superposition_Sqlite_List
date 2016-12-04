package enterprise.sample.com.quantum_superposition;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ginov on 4/12/2016.
 */



public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, db.DB_NAME, null, db.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE + " ( " +
                enterprise.sample.com.quantum_superposition.db.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE);
        onCreate(db);
    }
}
