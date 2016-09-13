package yunshuo.yneb.com.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "yuns_district.db", null, 1);
    }

    @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "create table yuns_district(_id integer primary key autoincrement,name text,level text,upid text)";
           sqLiteDatabase.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }



}
