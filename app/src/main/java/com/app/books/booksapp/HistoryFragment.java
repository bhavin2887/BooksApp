package com.app.books.booksapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment {

    private BookDataStorage bookDataStorageObject = null;

    ListView listView;
    List<RowItem> rowItems;
    Context con;

    /** Used for sending in startActivityforResult */
    public static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    /** Intent for sending the intent to other activity */
    Intent intentScan;

    /** Decide orientation of device(whether landscape or portrait) */
    public int orientation_value = -1;

    /** Final string used for QRCode scanning */
    public static final String SCAN_MODE = "SCAN_MODE";

    /** Final string used for QR_CODE_MODE */
    public static final String QR_CODE_MODE = "QR_CODE_MODE";

    /** Final string used for ORIENTATION */
    public static final String ORIENTATION = "orientation";

    CustomBaseAdapter adapter;

    Button btn;
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main, container, false);


        rowItems = new ArrayList<RowItem>();
        try {
            bookDataStorageObject = new BookDataStorage(getContext());
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

            cursor_contacts.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bookDataStorageObject.close();
        }

        listView = (ListView)rootView.findViewById(R.id.list);
        btn = (Button) rootView.findViewById(R.id.btn_empty);

        adapter = new CustomBaseAdapter(con, rowItems,isConnectingToInternet());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isConnectingToInternet()) {
                    Intent mainIntent = new Intent(getContext(), YouTubePlay.class);
                    mainIntent.putExtra("val", rowItems.get(position).getYoutube_link());
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(getContext(),
                            "Please check your Internet Connection.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                //startActivity(intent);
                startActivityForResult(intent, ACTIVITY_RESULT_QR_DRDROID);

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        con = activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ACTIVITY_RESULT_QR_DRDROID == requestCode && null != data
                && data.getExtras() != null) {
            String contents_badgenumber = data.getStringExtra("SCAN_RESULT");
            String result = data.getExtras().getString(getString(R.string.com_google_zxing_client_android_scan_result));
            Log.i("" + requestCode, "" + result);

            //Toast.makeText(getApplicationContext(), "contents_badgenumber=" + contents_badgenumber.toString(), Toast.LENGTH_LONG).show();

            if (isConnectingToInternet()) {
                new WebserviceCheckURL().execute(contents_badgenumber);
            } else {
                Toast.makeText(getActivity(),
                        "Please check your Internet Connection.",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.i("" + requestCode, "Result Not Displayed.");
        }
    }

    private class WebserviceCheckURL extends
            AsyncTask<String, String, SoapObject> {

        private String resp;
        SoapObject soap;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                soap = WebService.webService_GetDataFromPortal.callWebservice(getContext(), params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return soap;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            BookDataStorage bookDataStorageObjectNew = null;
            System.out.println("Myval" + soapObject);
            if (soapObject != null) {
                // CheckHospitalIsActiveResponse{CheckHospitalIsActiveResult=anyType{isActive=false;
                // }; }

				/*String Book_Id =  soapObject.getProperty("Book_Id").toString();
				String Ylink =  soapObject.getProperty("Ylink").toString();*/

                try {

                    if(soapObject.getProperty("Book_Id").toString().equals("0")){
                        Toast.makeText(getActivity(), "Sorry! There�s no Booksupp for this book yet.", Toast.LENGTH_SHORT).show();
                    }else{
                        bookDataStorageObjectNew = new BookDataStorage(getContext());

                        bookDataStorageObjectNew.open();

                        ContentValues cv = new ContentValues();
                        cv.put(BookDataStorage.PORTAL_ID, soapObject.getProperty("Book_Id").toString());
                        cv.put(BookDataStorage.ISBN, soapObject.getProperty("BISBN").toString());
                        cv.put(BookDataStorage.PUBLISHER_NAME, soapObject.getProperty("Pname").toString());
                        cv.put(BookDataStorage.BOOK_NAME, soapObject.getProperty("Bname").toString());
                        cv.put(BookDataStorage.AUTHOR, soapObject.getProperty("Aname").toString());
                        cv.put(BookDataStorage.YOUTUBE, soapObject.getProperty("Ylink").toString());

                        bookDataStorageObjectNew.insert(BookDataStorage.DATABASE_MAIN_DATA, cv);

                        rowItems = new ArrayList<RowItem>();
                        try {
                            bookDataStorageObject = new BookDataStorage(getContext());
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

                            adapter = new CustomBaseAdapter(con, rowItems,isConnectingToInternet());
                            listView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            bookDataStorageObject.close();
                        }

                        adapter.notifyDataSetChanged();
                        /*if(rowItems.isEmpty()){
                            btn.setVisibility(View.VISIBLE);
                        }*/

                        Intent it = new Intent(getActivity(),YouTubePlay.class);
                        it.putExtra("val", soapObject.getProperty("Ylink").toString());
                        startActivity(it);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    bookDataStorageObjectNew.close();
                }
            }
        }
    }
}