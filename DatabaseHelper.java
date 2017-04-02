import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Globals.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void execute(String Statment) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(Statment);
            LogUtils.LOGV(" execute SQL : ", Statment);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            db.close();
            db = null;
        }
    }

    public void executeSQL(String Statment) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.execSQL(Statment);
            LogUtils.LOGV(" execute SQL : ", Statment);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            db.close();
            db = null;
        }
    }

    public int insert(String Statment) {
        int retVal = 0;
        Cursor cur = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            LogUtils.LOGV("SQL : ", Statment);
            db.execSQL(Statment);

            cur = db.rawQuery("SELECT last_insert_rowid()", null);
            cur.moveToPosition(0);

            retVal = cur.getInt(0);

        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statment);
        } finally {
            if (cur != null)
                cur.close();

            cur = null;

            db.close();
            db = null;
        }

        return retVal;
    }

    public long insert(String tableName, ContentValues values) {
        long insertedID = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            insertedID = db.insert(tableName, null, values);
            LogUtils.LOGV("SQL : ", "------- insertedID : " + insertedID);
        } catch (Exception e) {
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
        } finally {
            db.close();
            db = null;
        }
        return insertedID;
    }


    public void update(String tableName, ContentValues values, String condition, String[] args) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            //db.beginTransaction();
            id = db.update(tableName, values, condition, args);
            LogUtils.LOGV(" update SQL : ", condition + "ROW AFFECTED" + id);
            //db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - update", e.getMessage());
            LogUtils.LOGV("SQL : update() ", "tableName ::" + tableName + " condition::" + condition);
        } finally {
            db.close();
        }
        db = null;
    }

    public Cursor query(String Statement) {
        Cursor cur = null;
        SQLiteDatabase db;
        if (Statement.contains("select")) {
            db = this.getReadableDatabase();
        } else {
            db = this.getWritableDatabase();
        }


        try {
            cur = db.rawQuery(Statement, null);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());
            LogUtils.LOGV("SQL : ", Statement);
        } finally {
            db.close();
        }
        db = null;

        return cur;
    }

    public boolean checkExists(String tableName, String column, String _value) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isExist = false;
        try {
            isExist = DatabaseUtils.queryNumEntries(db, tableName, column + "=?", new String[]{_value}) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return isExist;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Cursor cur = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            cur = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.LOGE("DataBaseHelper - ExecuteQuery", e.getMessage());

        } finally {
            db.close();
            db = null;
        }

        return cur;
    }

    public static String getDBStr(String str) {

        str = str != null ? str.replaceAll("\'", "\'\'") : null;
        return str;

    }

    public void upgrade(int level) {
        switch (level) {
            case 0:
                doUpdate0();
            case 1:
                //doUpdate1();
            case 2:
                // doUpdate2();
            case 3:
                // doUpdate3();

        }
    }


    public static final String DASHBOARD_TABLE = "DASHBOARD_TABLE";

    //DASHBOARD TABLE COLUMNS
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";

    private void doUpdate0() {

        String tbl_Dashboard = "CREATE TABLE IF NOT EXISTS " + DASHBOARD_CLAIM_TABLE + " (" + MASTER_CLAIM_NUMBER + " text primary key ,"
                + VEHICLE_REGISTRATION_NO + " text,"
                + VEHICLE_MODEL + " text,"
                + INSURED_NAME + " text,"
                + TAT + " text,"
                + WORKSHOP_NAME + " text,"
                + CLAIM_STAGE + " text,"
                + SERIAL_NO + " text,"
                + START_TIME + " text,"
                + CLAIM_TYPE + " text,"
                + INSURED_CITY + " text,"
                + EMAIL + " text,"
                + MOBILE + " text,"
                + INBOX_STATUS + " text,"
                + CLAIM_STATUS + " text,"
                + COMPLETION_PERCENTAGE + " text,"
                + COLOR_CODING + " text,"
                + POLICY_NUMBER + " text,"
                + SURVEYOR_CODE + " text,"
                + CLAIM_NUMBER + " text)";
        this.execute(tbl_Dashboard);
    }

    public SQLiteDatabase getConnection() {
        SQLiteDatabase dbCon = this.getWritableDatabase();
        return dbCon;
    }
}
