package com.paad.earthquake;

import android.app.ListFragment;
import android.app.SearchManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.content.CursorLoader;
import android.content.Intent;

public class EarthquakeSearchResultsFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	
	public EarthquakeSearchResultsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,null,
				new String[] {EarthquakeProvider.KEY_SUMMARY},new int[] {android.R.id.text1},0);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
		parseIntent(getActivity().getIntent());
	}
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
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

}
