package enterprise.sample.com.quantum_superposition;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DbHelper mHelper;
    private ListView Superposition;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DbHelper(this);
        Superposition = (ListView) findViewById(R.id.list);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new Message")
                        .setMessage("")
                        .setView(taskEditText)
                        .setPositiveButton("Add Text", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel Operation", null)
                        .create();
                dialog.show();
                return true;

            case R.id.action_insert_dummy_data:

                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE, "SuperPosition \nOne \nTwo");
                db.insertWithOnConflict(enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
                updateUI();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View view) {

        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        Toast.makeText(this, "Delete with succes '"+task+"'", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE,
                enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(enterprise.sample.com.quantum_superposition.db.TaskEntry.TABLE,
                new String[]{enterprise.sample.com.quantum_superposition.db.TaskEntry._ID, enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {

            int idx = cursor.getColumnIndex(enterprise.sample.com.quantum_superposition.db.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item,
                    R.id.task_title,
                    taskList);
            Superposition.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
