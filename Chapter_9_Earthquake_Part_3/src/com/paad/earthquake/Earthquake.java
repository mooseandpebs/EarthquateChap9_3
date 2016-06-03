package com.paad.earthquake;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

public class Earthquake extends Activity {
  
  private final static String TAG = "Earthquake";
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    
    // Use the Search Manager to find the SearchableInfo related to this 
    // Activity.
  }

  
  static final private int MENU_PREFERENCES = Menu.FIRST+1;
  static final private int MENU_UPDATE = Menu.FIRST+2;
  private static final int SHOW_PREFERENCES = 1;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //super.onCreateOptionsMenu(menu);
    try{
    	getMenuInflater().inflate(R.menu.preferences, menu);
    	updateFromPreferences();
	    SearchManager searchManager = 
	    (SearchManager)getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView)menu.findItem(R.id.search_view_id).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    handleIntent(getIntent());
    }catch(Exception e)
    {
    	Log.e(TAG,"err:"+e);
    }
    return true;
  }
@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}
@Override
	protected void onResume() {
		super.onResume();


	}
@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.e(TAG, "got new intent");
		setIntent(intent);
		handleIntent(intent);
	}

  public boolean onOptionsItemSelected(MenuItem item){
    super.onOptionsItemSelected(item);
    switch (item.getItemId()) {

      case R.id.settings: 
    	  launchOptionSettings();
    	  return true;
      case R.id.start_update_service:
    	  startService(new Intent(this, EarthquakeUpdateService.class));
          return true;
      case R.id.stop_update_service:
    	  stopService(new Intent(this, EarthquakeUpdateService.class));
          return true;
      }
    return false;
  }

  
  public int minimumMagnitude = 0;
  public boolean autoUpdateChecked = false;
  public int updateFreq = 0;

  private void updateFromPreferences() {
    Context context = getApplicationContext();
    SharedPreferences prefs = 
      PreferenceManager.getDefaultSharedPreferences(context);

    minimumMagnitude = 
      Integer.parseInt(prefs.getString(PreferencesActivity.PREF_MIN_MAG, "3"));
    updateFreq = 
      Integer.parseInt(prefs.getString(PreferencesActivity.PREF_UPDATE_FREQ, "60"));

    autoUpdateChecked = prefs.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false);
  }

  static final String PREF_FRAGMENT="JsonPreferencesFragment";
  void launchOptionSettings()
  {
  	getFragmentManager().beginTransaction()
  	.replace(R.id.container, new EarthquakePreferencesFragment())
  	.addToBackStack(PREF_FRAGMENT)
  	.commit();
  	
  }
  public void handleIntent(Intent intent)
  {
	  try
	  {
		  parseIntent(intent);
	  }catch(Exception e)
	  {
		  Log.e(TAG, "handleIntent err:"+e);
	  }
  }

  private static String QUERY_EXTRA_KEY = "QUERY_EXTRA_KEY";

  private void parseIntent(Intent intent) {
	    // If the Activity was started to service a Search request,
	    // extract the search query.
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String searchQuery = intent.getStringExtra(SearchManager.QUERY);

	      // Perform the search, passing in the search query as an argument
	      // to the Cursor Loader
	      Bundle args = new Bundle();
	      args.putString(QUERY_EXTRA_KEY, searchQuery);
	      
	      // Restart the Cursor Loader to execute the new query.
	      getLoaderManager().restartLoader(0, args, this);
	    }

  
}