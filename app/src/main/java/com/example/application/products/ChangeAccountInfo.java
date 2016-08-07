package com.example.application.products;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangeAccountInfo extends AppCompatActivity {
    private MyHelper helper;
    private SQLiteDatabase db;
    private Button buttonInsert;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editAddress;
    private EditText editMailAddress;
    private EditText editPassword;
    private EditText editPrefectureId;

    private AccountInfo accountInfo;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_info);

        accountInfo = AccountInfo.getInstance();
        helper = MyHelper.getInstance(this);
        db = helper.getReadableDatabase();

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_create);
        editFirstName = new EditText(this);
        editLastName = new EditText(this);
        editPrefectureId = new EditText(this);
        editAddress = new EditText(this);
//        editMailAddress = new EditText(this);
//        editPassword = new EditText(this);
        buttonInsert = new Button(this);

        //アカウント情報を取得
        query();
        //都道府県情報を取得
        String prefecture = queryPrefecture();

        editFirstName.setText(cursor.getString(0));
        editLastName.setText(cursor.getString(1));
        editPrefectureId.setText(prefecture);
        editAddress.setText(cursor.getString(3));

        layout.addView(editFirstName);
        layout.addView(editLastName);
        layout.addView(editPrefectureId);
        layout.addView(editAddress);

//        layout.addView(editMailAddress);
//        layout.addView(editPassword);
        layout.addView(buttonInsert);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("loh", "click");

                Cursor c = db.query(MyHelper.ACCOUNT_TABLE_NAME,
                        new String[]{MyHelper.AccountColumns.mailAddress},null,null,null,null,null);
                c.moveToFirst();
                Log.e("isCheck :", String.valueOf(c.getString(0)));
                updateAccount(accountInfo.getMailAddress());
            }
        });
    }

    //メールアドレスからアカウント情報を取得
    private void query() {
        /*Cursor cursor = db.query(MyHelper.ACCOUNT_TABLE_NAME, new String[]{
                MyHelper.AccountColumns.firstName,
                MyHelper.AccountColumns.lastName,
                MyHelper.AccountColumns.prefectureId,
                MyHelper.AccountColumns.address,
                MyHelper.AccountColumns.mailAddress,
                MyHelper.AccountColumns.password
        }, null, null, null, null, null);*/

        cursor = db.query(MyHelper.ACCOUNT_TABLE_NAME,
                new String[]{
                        MyHelper.AccountColumns.firstName,
                        MyHelper.AccountColumns.lastName,
                        MyHelper.AccountColumns.prefectureId,
                        MyHelper.AccountColumns.address,
                        MyHelper.AccountColumns.password
                },
                String.format("%s = \"%s\"",
                        MyHelper.AccountColumns.mailAddress,
                        accountInfo.getMailAddress()),
                        null, null, null, null, null);
        cursor.moveToFirst();
    }

    //CodeId, Codeから都道府県を取得する
    private String queryPrefecture() {
        String name;
        Cursor cursor = db.query(MyHelper.CODE_MASTER_TABLE_NAME,
                new String[]{
                        MyHelper.CodeMColumns.name,
                },
                String.format("%s = %d AND %s = %d",
                        MyHelper.CodeMColumns.codeId, 1,
                        MyHelper.CodeMColumns.code, accountInfo.getPrefectureId()),
                null, null, null, null, null);
        cursor.moveToFirst();
        name = cursor.getString(0);
        cursor.close();
        return name;
    }

    private void updateAccount(String mailAddress) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyHelper.AccountColumns.firstName, editFirstName.getText().toString());
        contentValues.put(MyHelper.AccountColumns.lastName, editLastName.getText().toString());
        contentValues.put(MyHelper.AccountColumns.prefectureId, 39);
        contentValues.put(MyHelper.AccountColumns.address, editAddress.getText().toString());
//        contentValues.put(MyHelper.AccountColumns.mailAddress, editMailAddress.getText().toString());
        contentValues.put(MyHelper.AccountColumns.password,
                cursor.getString(cursor.getColumnIndex(MyHelper.AccountColumns.password)));
        Log.e("passwordColumns",
                String.valueOf(
                        cursor.getString(cursor.getColumnIndex(MyHelper.AccountColumns.password))));

        Log.e("log", editAddress.getText().toString());

        db.update(MyHelper.ACCOUNT_TABLE_NAME, contentValues,
                String.format("%s = \"%s\"",
                        MyHelper.AccountColumns.mailAddress, mailAddress), null);

    }
}
