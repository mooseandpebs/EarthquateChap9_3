package com.paad.earthquake;


import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class EarthquakeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{

	EarthquakeAdapter mEarthquakeAdapter;
	Callback mCallback;
	private String mQuery;

	public interface Callback {
		public void positionToMapCalled(Quake _Quakedata);
	}

	public void setQuery(String _Query) {
		mQuery = _Query;
	}

	public String getQuery() {
		return (mQuery);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			mEarthquakeAdapter = new EarthquakeAdapter();
			setListAdapter(mEarthquakeAdapter);
		} catch (Exception e) {

		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (!(activity instanceof Callback)) {
			throw new IllegalStateException("Activity must implement fragments Callback interface");
		}
		mCallback = (Callback) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Create a new Adapter and bind it to the List View
		mEarthquakeAdapter = new EarthquakeAdapter();
		setListAdapter(mEarthquakeAdapter);

		getLoaderManager().initLoader(0, null, this);

		Thread t = new Thread(new Runnable() {
			public void run() {
				refreshEarthquakes();
			}
		});
		t.start();
	}

	private static final String TAG = "EarthquakeListFragment";

	Handler handler = new Handler();

	public void refreshEarthquakes() {
		handler.post(new Runnable() {
			public void run() {
				getLoaderManager().restartLoader(0, null, EarthquakeListFragment.this);
			}
		});

		getActivity().startService(new Intent(getActivity(), EarthquakeUpdateService.class));
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = new String[] { EarthquakeProvider.KEY_ID, EarthquakeProvider.KEY_SUMMARY };

		Earthquake earthquakeActivity = (Earthquake) getActivity();
		String where = EarthquakeProvider.KEY_MAGNITUDE + " > " + earthquakeActivity.minimumMagnitude;

		CursorLoader loader = new CursorLoader(getActivity(), EarthquakeProvider.CONTENT_URI, projection, where, null,
				null);

		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mEarthquakeAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		mEarthquakeAdapter.swapCursor(null);
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

}