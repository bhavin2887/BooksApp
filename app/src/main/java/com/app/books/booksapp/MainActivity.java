package com.app.books.booksapp;

/*import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BookDataStorage bookDataStorageObject = new BookDataStorage(this);

	
	ListView listView;
	List<RowItem> rowItems;

	// Setting the Title of the Third TabLayout
		@Override
		protected void onResume() {
			super.onResume();
			setTitle(R.string.history);
		}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		rowItems = new ArrayList<RowItem>();
		try {

			// Checking the Internet Available or not
				bookDataStorageObject.open();
				Cursor cursor_contacts = bookDataStorageObject.getAllData();
				cursor_contacts.moveToFirst();

				 while(cursor_contacts.isAfterLast() == false){
					 String author = cursor_contacts.getString(cursor_contacts.getColumnIndex(BookDataStorage.AUTHOR));
					 String book = cursor_contacts.getString(cursor_contacts.getColumnIndex(BookDataStorage.BOOK_NAME));
					 String link = cursor_contacts.getString(cursor_contacts.getColumnIndex(BookDataStorage.YOUTUBE));
					 RowItem item = new RowItem(author,book,link);
					 rowItems.add(item);

					 cursor_contacts.moveToNext();
				  }
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bookDataStorageObject.close();
		}
			
		listView = (ListView) findViewById(R.id.list_main);
		CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems,isConnectingToInternet());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast toast = Toast.makeText(getApplicationContext(), "Item "
								+ (position + 1) + ": " + rowItems.get(position),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
				int val = position;
				if (isConnectingToInternet()) {
					Intent mainIntent = new Intent(MainActivity.this, YouTubePlay.class);
					//mainIntent.putExtra("val", rowItems.get(position).getYoutube_link());
					startActivity(mainIntent);
				}else{
					Toast.makeText(getApplicationContext(),
							"Please check your Internet Connection.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}*/

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static com.app.books.booksapp.HistoryFragment.ACTIVITY_RESULT_QR_DRDROID;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private Toolbar mToolbar;
	private FragmentDrawer drawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		drawerFragment = (FragmentDrawer)
				getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
		drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
		drawerFragment.setDrawerListener(this);

		// display the first navigation drawer view on app launch
		displayView(0);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,
					Manifest.permission.CAMERA},1);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {

				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.
				} else {

					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		/*if(id == R.id.action_search){
			Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
			return true;
		}*/

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDrawerItemSelected(View view, int position) {
		displayView(position);
	}

	private void displayView(int position) {
		Fragment fragment = null;
		String title = getString(R.string.app_name);
		switch (position) {
			case 0:
				fragment = new HistoryFragment();
				title = getString(R.string.title_home);
				break;
			case 1:
				fragment = new HelpFragment();
				title = getString(R.string.title_messages);
				break;
			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.container_body, fragment);
			fragmentTransaction.commit();

			// set the toolbar title
			getSupportActionBar().setTitle(title);
		}
	}
}