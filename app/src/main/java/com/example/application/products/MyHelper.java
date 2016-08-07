package com.example.application.products;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by application on 16/07/26.
 */
public class MyHelper extends SQLiteOpenHelper {
    private static MyHelper singleton = null;

    private static final String DB_NAME = "products.db";
    private static final int DB_VERSION = 1;
    static final String TABLE_NAME = "products";
    static final String ACCOUNT_TABLE_NAME = "Account";
    static final String CODE_MASTER_TABLE_NAME = "CodeM";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    Columns._ID + " INTEGER primary key autoincrement," +
                    Columns.ID + " TEXT," +
                    Columns.productName + " TEXT," +
                    Columns.PRICE + " INTEGER," +
                    Columns.STOCK + " INTEGER," +
                    Columns.PREFECTURE + " TEXT);";

    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE " + ACCOUNT_TABLE_NAME + "(" +
                    AccountColumns.firstName + " TEXT," +
                    AccountColumns.lastName + " TEXT," +
                    AccountColumns.prefectureId + " INTEGER," +
                    AccountColumns.address + " TEXT," +
                    AccountColumns.mailAddress + " TEXT primary key," +
                    AccountColumns.password + " TEXT);";

    private static final String CREATE_TABLE_CODE_MASTER =
            "CREATE TABLE " + CODE_MASTER_TABLE_NAME + "(" +
                    CodeMColumns.codeId + " INTEGER," +
                    CodeMColumns.code + " INTEGER," +
                    CodeMColumns.name + " TEXT," +
                    "primary key(" +
                    CodeMColumns.codeId + ", " +
                    CodeMColumns.code + "));";


    public interface AccountColumns extends BaseColumns {
        String firstName = "FirstName";
        String lastName = "LastName";
        String prefectureId = "PrefectureId";
        String address = "Address";
        String mailAddress = "MailAddress";
        String password = "Password";
    }

    public interface Columns extends BaseColumns {
        public static final String ID = "id";
        public static final String productName = "name";
        public static final String PRICE = "price";
        public static final String STOCK = "stock";
        public static final  String PREFECTURE = "prefecture";
    }

    public interface CodeMColumns extends BaseColumns {
        String codeId = "CodeId";
        String code = "Code";
        String name = "Name";
    }

    static synchronized MyHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new MyHelper(context);
        }
        return singleton;
    }

    /**
     * コンストラクタ
     */
    private MyHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        Log.d("MyHelper", "MyHelper");

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("MyHelper", "onCreate");

        // CREATE文を実行する
        db.execSQL(CREATE_TABLE_CODE_MASTER);
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(CREATE_TABLE_ACCOUNT);

        //initTable();
        // table row insert
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 1, '北海道');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 2, '青森');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 3, '岩手');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 4, '宮城');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 5, '秋田');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 6, '山形');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 7, '福島');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 8, '茨城');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 9, '栃木');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 10, '群馬');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 11, '埼玉');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 12, '千葉');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 13, '東京');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 14, '神奈川');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 15, '新潟');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 16, '富山');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 17, '石川');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 18, '福井');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 19, '山梨');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 20, '長野');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 21, '岐阜');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 22, '静岡');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 23, '愛知');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 24, '三重');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 25, '滋賀');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 26, '京都');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 27, '大阪');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 28, '兵庫');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 29, '奈良');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 30, '和歌山');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 31, '鳥取');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 32, '島根');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 33, '岡山');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 34, '広島');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 35, '山口');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 36, '徳島');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 37, '香川');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 38, '愛媛');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 39, '高知');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 40, '福岡');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 41, '佐賀');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 42, '長崎');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 43, '熊本');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 44, '大分');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 45, '宮崎');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 46, '鹿児島');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (1, 47, '沖縄');");
        db.execSQL("insert into CodeM(CodeId,Code,Name) values (2, 1, '30');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
