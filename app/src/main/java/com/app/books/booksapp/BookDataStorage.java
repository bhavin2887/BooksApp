package com.app.books.booksapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDataStorage {

	/** Database Opening count is Maintained */
	public static int dbOpenCount=0;

	/** Database Closing count is Maintained */
	public static int dbCloseCount=0;
	
	/** Object used for creating, insertion into Database object */
	private static SQLiteDatabase sqlitedb;
	private final Context context;					// Context for passing in current classes constructor
	private DatabaseHelper DBHelperObject;			// DatabaseHelper object for getting getting object
	
	/** Database Name Declaration */
	public static final String DATABASE_NAME = "Book.db";
	
	/** Database Version Number */
	private static final int DATABASE_VERSION = 1;

	/** Inner class object created for synchronizing when RLRDataStorage object is Created */
	private static DatabaseHelper iDbHelper;
	
	// Devices Table Fields


	public static final String DATABASE_MAIN_DATA= "BookScan";
	
	public static final String ISBN= "IsbnNo";
	
	public static final String PUBLISHER_NAME= "PublisherName";
	
	public static final String AUTHOR= "Author";
	
	public static final String PORTAL_ID= "PortalId";

	public static final String YOUTUBE= "Youtube";
	public static final String RED_KG= "Red_kg";
	public static final String BLUE_KG= "Blue_kg";
	public static final String BLACK_KG= "Black_kg";
	public static final String TOTAL_KG= "Total_kg";
	public static final String YELLOW_BAGS_TAKEN= "Yellow_bags_taken";
	public static final String RED_BAGS_TAKEN= "Red_bags_taken";
	public static final String BLUE_BAGS_TAKEN= "Blue_bags_taken";
	public static final String BLACK_BAGS_TAKEN= "Black_bags_taken";
	public static final String YELLOW_BAGS_GIVEN= "Yellow_bags_given";
	public static final String RED_BAGS_GIVEN= "Red_bags_given";
	public static final String BLUE_BAGS_GIVEN= "Blue_bags_given";
	public static final String BLACK_BAGS_GIVEN= "Black_bags_given";
	
	public static final String DATE_TIME= "Date_time";
	public static final String IS_ON_SERVER= "IsOnServer";
	public static final String IMAGE_STRING= "ImageString";
	public static final String VEHICLE_ID= "vehicleID";
	public static final String EMPLOYEE_ID= "employeeID";
	public static final String ROUTE_ID= "routeID";
	
	public static final String CORRECTANSWER= "CorrectAnswer";
	
	public static final String DISTRICT= "District";
	
	public static final String ID= "Id";
	public static final String QEE_ID_STRING= "strQEEID";
	public static final String TALLUKA= "Talluka";
	public static final String BOOK_NAME= "BookName";
	public static final String AREA= "Area";
	public static final String IMAGEUPLOAD= "ImageUpload";
	public static final String ADDRESS= "Address";
	public static final String TELEPHONE= "Telephone";
	public static final String INCHARGE_MOBILE= "Incharge_mobile";
	public static final String NO_OF_BED= "No_of_bed";
	public static final String CERTIFICATE_EXPIRY= "Certificate_expiry";

	public static final String GPCB_ID= "GPCB_ID";

	public static final String PASSWORD= "Password";
	public static final String AUTH_NO= "Auth_no";
	public static final String AUTH_EXPIRY_DATE= "Auth_exp_date";
	public static final String STATUS= "Status";
	public static final String ROUTE_NO= "Route_no";
	public static final String FREQUENCY= "Frequency";
	public static final String R_SEQUENCE= "R_Sequence";
	public static final String CAT= "Cat";
	public static final String Email_Id= "Email_id";
	public static final String Service_Start_From= "Service_start_from";
	public static final String Closed_From= "Closed_from";

	
	/** Database Object for Singleton */
	private static BookDataStorage instance;
	/**
	 * Getting object of DatabaseHelper
	 * @param ctx
	 */
	public BookDataStorage(Context ctx) {
		Log.i("RLRDataStorage","Context");
		this.context = ctx;
		DBHelperObject = new DatabaseHelper(context);
		synchronized (DATABASE_NAME) {
			if (iDbHelper == null)
				iDbHelper = new DatabaseHelper(ctx);
			}
	}

	/**
	 * Opens the database
	 * @return RLRDataStorage object
	 * @throws SQLException
	 */
	public BookDataStorage open() {
		try {
			dbOpenCount = dbOpenCount + 1;
			sqlitedb = DBHelperObject.getWritableDatabase();
			System.out.println("RLRDataStorage.open()"+dbOpenCount);
		} catch (SQLException ex) {
			sqlitedb = DBHelperObject.getReadableDatabase();
			Log.d("log_tag", "Exception is Thrown open get Readable");
			ex.printStackTrace();
		}
		return this;
	}

	/**
	 * Closes the database
	 * @return RLRDataStorage object
	 */
	public void close() {
			DBHelperObject.close();
			System.out.println("RLRDataStorage.close()"+dbCloseCount);
	}
	
	
	private static final String TABLE_MAIN_DATA = "CREATE TABLE "
			+ DATABASE_MAIN_DATA + " ("
			+ ID + " integer autoincreament,"
			+ PORTAL_ID + " integer,"
			+ ISBN + " text,"
			+ PUBLISHER_NAME + " text,"
			+ BOOK_NAME + " text,"
			+ AUTHOR + " text,"
			+ YOUTUBE + " text);";

	/**
	singleton BamDataStorage.
	 *
	 * @param context
	 * @return BamDataStorage database instance.
	 */
	public static BookDataStorage GetFor(Context context) {
		if (instance == null)
			instance = new BookDataStorage(context);
		// if (!instance.isOpen())
		// instance.open();
		return instance;
	}
	
	/**
	 * Creation of Database Tables and Upgradation is done.
	 * @author Bhavin
	 *
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		/**
		 * Get records in a particular table according to sql query
		 * @param tablename
		 * @return a cursor object pointed to the record(s) selected by sql query.
		 */
		public synchronized static Cursor getRecordBySelection(String tablename) {
			return sqlitedb.query(tablename, null, null, null, null, null,null);
		}

		/**
		 * Constructor created for DatabaseHelper
		 * @param context
		 */
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		//	Database Tables are created
		@Override
		public void onCreate(SQLiteDatabase db) {
			//db.execSQL(DATABASE_TEST);			
			db.execSQL(TABLE_MAIN_DATA);	

			//Log.i("DATABASE_TEST",""+DATABASE_TEST);
			Log.i("DATABASE_DETAIL","Creation = "+TABLE_MAIN_DATA);

		}
		
		// Database Upgradation is done
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("BAMDB", "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			//db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TEST);			
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_DATA);
			onCreate(db);
		}

	}

	/**
	 * Insert a record into particular table
	 *
	 * @param tablename
	 * @param values
	 * @returnthe row ID of the newly inserted row, or -1 if an error occurred
	 */
	public synchronized long insert(String tablename, ContentValues values) {
		return sqlitedb.insert(tablename, null, values);
	}
	/**
	 * Getting Profile Value from the Database
	 * @return cursor
	 */
	public Cursor getSkillCodeQuestionId(String Value, int item) {
		return sqlitedb.rawQuery("select * from "+DATABASE_MAIN_DATA+" where option = '"+Value+"' and "+Value+" ='"+item+"'", null);
	}
	public void deleteAllData(String table){
		sqlitedb.delete(table,null,null);
	}
	/**
	 * Getting all the ProfileNames from the Profile Table
	 * @return cursor
	 */
	public Cursor getTableData(String type) {
		return sqlitedb.rawQuery("select * from "+type, null);
	}
	/**
	 * Getting all the ProfileNames from the Profile Table
	 * @return cursor
	 */
	public Cursor getTotalRows() {
		return sqlitedb.rawQuery("select * from "+DATABASE_MAIN_DATA, null);
	}
	/**
	 * Getting all the ProfileNames from the Profile Table
	 * @return cursor
	 */
	public Cursor getCountIsScoreThisQuestion() {
		return sqlitedb.rawQuery("select count(*) from "+DATABASE_MAIN_DATA+" where "+TELEPHONE+" = '0'", null);
	}
	/**
	 * Getting all the ProfileNames from the Profile Table
	 * @return cursor
	 */
	public Cursor getAllData() {
		return sqlitedb.rawQuery("select * from "+DATABASE_MAIN_DATA+" order by rowid desc limit 10", null);
	}
	
	/*public Cursor getDataNotOnServer() {
		// Select All Query
		String selectQuery = "SELECT * FROM " + DATABASE_DATA_COLLECTED + " where "+ IS_ON_SERVER + " = 'False'";
		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = sqlitedb.rawQuery(selectQuery, null);

		return cursor;
	}

	public Cursor updateUploadedFile() {
		String query = "UPDATE "+DATABASE_DATA_COLLECTED+" SET "+IS_ON_SERVER+" = 'True' where "+IS_ON_SERVER+" = 'False'";
		Cursor cu = sqlitedb.rawQuery(query, null);
		cu.moveToFirst();
		cu.close();

		return cu;
	}*/
	    
}
