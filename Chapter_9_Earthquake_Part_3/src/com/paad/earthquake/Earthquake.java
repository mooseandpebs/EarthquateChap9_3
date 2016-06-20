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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

public class Earthquake extends Activity implements 
	EarthquakeListFragment.Callback {

	private final static String TAG = "Earthquake";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Use the Search Manager to find the SearchableInfo related to this
		// Activity.
	}

	static final private int MENU_PREFERENCES = Menu.FIRST + 1;
	static final private int MENU_UPDATE = Menu.FIRST + 2;
	private static final int SHOW_PREFERENCES = 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// super.onCreateOptionsMenu(menu);
		try {
			getMenuInflater().inflate(R.menu.preferences, menu);
			updateFromPreferences();
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search_view_id).getActionView();
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			handleIntent(getIntent());
		} catch (Exception e) {
			Log.e(TAG, "err:" + e);
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
		try {
			super.onNewIntent(intent);
			setIntent(intent);
			handleIntent(intent);
		} catch (Exception e) {
			Log.e(TAG, "onNewIntent err:" + e);
		}
	}
	public static final String UPDATE_EARTHQUAKE_SERVICE_EXTRA_KEY = "UPDATE_EARTHQUAKE_SERVICE_EXTRA_KEY";
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.settings:
			launchOptionSettings();
			return true;
		case R.id.start_update_service:
			Intent intent = new Intent(this, EarthquakeUpdateService.class);
			intent.putExtra(UPDATE_EARTHQUAKE_SERVICE_EXTRA_KEY, true);
			startService(intent);
			return true;
		case R.id.stop_update_service:
			stopService(new Intent(this, EarthquakeUpdateService.class));
			return true;
		case R.id.start_auto_update_service:
			startAutoUpdate();
			return true;
		case R.id.stop_auto_update_service:
			stopAutoUpdate();
			return true;
		}
		return false;
	}

	public int minimumMagnitude = 0;
	public boolean autoUpdateChecked = false;
	public int updateFreq = 0;

	private void updateFromPreferences() {
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		minimumMagnitude = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_MIN_MAG, "3"));
		updateFreq = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_UPDATE_FREQ, "60"));

		autoUpdateChecked = prefs.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false);
	}

	static final String PREF_FRAGMENT = "JsonPreferencesFragment";

	void launchOptionSettings() {
		try {
			getFragmentManager().beginTransaction().replace(R.id.container, new EarthquakePreferencesFragment())
					.addToBackStack(PREF_FRAGMENT).commit();
		} catch (Exception e) {
			Log.e(TAG, "launchOptionSettings err:" + e);
		}

	}

	public void handleIntent(Intent intent) {
		try {
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				parseIntent(intent);
			}
		} catch (Exception e) {
			Log.e(TAG, "handleIntent err:" + e);
		}
	}

	public void startAutoUpdate() {
		try {

		} catch (Exception e) {
			Log.e(TAG, "startAutoUpdate err:" + e);
		}

	}

	public void stopAutoUpdate() {
		try {

		} catch (Exception e) {
			Log.e(TAG, "stopAutoUpdate err:" + e);
		}

	}

	private static String QUERY_EXTRA_KEY = "QUERY_EXTRA_KEY";

	private String EARTHQUAKESEARCHRESULTSFRAGMENT = "EarthquakeSearchResultsFragment";

	private void parseIntent(Intent intent) {
		// If the Activity was started to service a Search request,
		// extract the search query.
		try {
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				String searchQuery = intent.getStringExtra(SearchManager.QUERY);
				//EarthquakeSearchResultsFragment frag;
				EarthquakeListFragment frag
					= (EarthquakeListFragment) getFragmentManager()
						.findFragmentByTag(EARTHQUAKESEARCHRESULTSFRAGMENT);
				if (frag == null) {
					frag = new EarthquakeListFragment();
				}
				frag.setQuery(searchQuery);
				getFragmentManager().beginTransaction().replace(R.id.container, frag,
						EARTHQUAKESEARCHRESULTSFRAGMENT)
						.addToBackStack(EARTHQUAKESEARCHRESULTSFRAGMENT).commit();

			}
		} catch (Exception e) {
			Log.e(TAG, "parseIntent" + e);
		}
	}
	public void positionToMapCalled(Quake _Quakedata)
	{
		
	}

}