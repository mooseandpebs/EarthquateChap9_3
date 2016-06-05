package com.paad.earthquake;

import android.app.ListFragment;
import android.app.SearchManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;

public class EarthquakeSearchResultsFragment extends ListFragment
		implements OnQueryTextListener, LoaderCallbacks<Cursor> {
	private static final String TAG = "EarthquakeSearchResultsFragment";
	private SimpleCursorAdapter mAdapter;
	private String mQuery = "";

	public EarthquakeSearchResultsFragment() {
		// TODO Auto-generated constructor stub
	}

	public void setQuery(String _Query) {
		mQuery = _Query;
	}

	public String getQuery() {
		return (mQuery);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, null,
				new String[] { EarthquakeProvider.KEY_SUMMARY }, new int[] { android.R.id.text1 }, 0);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
		sendQuery();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("What's up");

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

	private void sendQuery() {
		// Perform the search, passing in the search query as an argument
		// to the Cursor Loader
		if (!mQuery.isEmpty()) {
			Bundle args = new Bundle();
			args.putString(QUERY_EXTRA_KEY, mQuery);

			// Restart the Cursor Loader to execute the new query.
			getLoaderManager().restartLoader(0, args, this);
		} else {
			Log.e(TAG, "query was empty in send Query");
		}
	}

	// implements of OnQueryTextListener
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

}
