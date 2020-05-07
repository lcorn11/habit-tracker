package com.habittracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.Arrays;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Managers.HabitListManager;
import com.habittracker.R;

public class HabitsListView extends AppCompatActivity {
    ListView hLV;
    ArrayAdapter<String> habitAdapter;
    SearchView searchView;
    private HabitListManager habitList;
    private User user;
    private Button btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_habits );

        setTitle( "All Habits" );
        Intent intent = getIntent();
        habitList = (HabitListManager) intent.getSerializableExtra( "habitList" );

        hLV = (ListView) findViewById( R.id.habitListView );
        searchView = (SearchView) findViewById( R.id.action_search);

        ArrayList<String> habitNames = habitList.getHabitNames( habitList.getHabits() );
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, habitNames );
        habitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habitNames);
        hLV.setAdapter(adapter);

        configList();
       // setupHomeButton();
    }

  /*  private void setupHomeButton() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setHomeButtonEnabled( true );
        }
    }*/

/*    protected void onMenuHomePressed() {
        onBackPressed();
    }*/


    @Override
    protected void onResume() {
        super.onResume();

        configList();
        reloadList();
      //  adapter.notifyDataSetChanged();
    }

    private void configList() {
        ListView list = findViewById( R.id.habitListView );
        reloadList(); //load list for the first time

        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition( position );
                Habit clickedHabit = habitList.getHabit( selected );

                Intent nextActivity = new Intent( HabitsListView.this, ReportView.class );
                nextActivity.putExtra( "habit", clickedHabit );
                startActivity( nextActivity );
            }
        } );
    }

  public void reloadList() {
        ListView list = findViewById( R.id.habitListView );
        ArrayList<String> habitNames = habitList.getHabitNames( habitList.getHabits() );
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, habitNames );
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.search_menu, menu );
        MenuItem menuItem = menu.findItem( R.id.action_search );
//get searchview
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                habitAdapter.getFilter().filter(newText);
                return true;
            }
        });
         return super.onCreateOptionsMenu( menu );
   }


    /*@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    list = list1;
                } else {
                    List<Habit> filterList = new ArrayList<>();
                    for (Habit data : list1) {
                        if (data.getHabitName().toLowerCase().contains( charString )) {
                            filterList.add( data );
                        }
                    }
                    list = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }
*/
      /*       @Override
           protected void publishResults(CharSequence constraint, FilterResults results) {

                list = (List<Habit>) results.values;
                mhabitListManager.notify();
                // notifyDataSetChanged(); this is recyclerview. I need a listview method
            }
        };
    }*/
}
 /*  public void onHomeBtnClicked(View v){
        Intent termIntent = new Intent(getBaseContext(), MainView.class);
        startActivity(termIntent);
    }*/