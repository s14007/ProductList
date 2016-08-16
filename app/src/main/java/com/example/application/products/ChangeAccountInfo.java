package com.example.application.products;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ChangeAccountInfo extends AppCompatActivity {
    private MyHelper helper;
    private SQLiteDatabase db;
    private Button buttonInsert;
    private Spinner prefectureSpinner;
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

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_create);
        editFirstName = new EditText(this);
        editLastName = new EditText(this);
        editPrefectureId = new EditText(this);
        editAddress = new EditText(this);
//        editMailAddress = new EditText(this);
//        editPassword = new EditText(this);
        buttonInsert = new Button(this);
        buttonInsert.setText("決定");

        prefectureSpinner = new Spinner(this);

        //アカウント情報を取得
        query();
        //都道府県情報を取得
        String prefecture = queryPrefecture();

        editFirstName.setText(cursor.getString(0));
        editLastName.setText(cursor.getString(1));
        editPrefectureId.setText(prefecture);
        editAddress.setText(cursor.getString(3));

        //Spinner set
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(getPrefecture(accountInfo.getPrefectureId()));
        prefectureSpinner.setAdapter(adapter);


        layout.addView(editFirstName);
        layout.addView(editLastName);
//        layout.addView(editPrefectureId);
        layout.addView(prefectureSpinner);
        layout.addView(editAddress);

//        layout.addView(editMailAddress);
//        layout.addView(editPassword);
        layout.addView(buttonInsert);



        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("loh", "click");

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
        Log.e("mailaddress :", accountInfo.getMailAddress());
    }

    private interface order {
        String select = "%s = %d AND %s = %d";
    }

    //CodeId, Codeから都道府県を取得する
    private String queryPrefecture() {
        Log.e("accountInfo :", String.valueOf(accountInfo.getPrefectureId()));
        String name;
        Cursor cursor = db.query(MyHelper.CODE_MASTER_TABLE_NAME,
                new String[]{
                        MyHelper.CodeMColumns.name,
                },
                String.format(order.select,
                        MyHelper.CodeMColumns.codeId, 1,
                        MyHelper.CodeMColumns.code, accountInfo.getPrefectureId()),
                null, null, null, null, null);
        cursor.moveToFirst();
        name = cursor.getString(0);
        cursor.close();
        return name;
    }

    private List<String> getPrefecture() {
        List<String> name = new ArrayList<>();
        Cursor cursor = db.query(MyHelper.CODE_MASTER_TABLE_NAME,
                new String[]{
                        MyHelper.CodeMColumns.name,
                },
                String.format("%s = %d",
                        MyHelper.CodeMColumns.codeId, 1),
                null, null, null, null, null);
        boolean move = cursor.moveToFirst();
        while (move) {
            name.add(cursor.getString(0));
            Log.e("cursor", cursor.getString(0));
            move = cursor.moveToNext();
        }
        cursor.close();
        return name;
    }

    private List<String> getPrefecture(int accountPrefecture) {
        List<String> name = new ArrayList<>();
        Cursor cursor = db.query(MyHelper.CODE_MASTER_TABLE_NAME,
                new String[]{
                        MyHelper.CodeMColumns.name,
                },
                String.format("%s = %d AND %s = %d",
                        MyHelper.CodeMColumns.codeId, 1,
                        MyHelper.CodeMColumns.code, accountPrefecture),
                null, null, null, null, null);
        cursor.moveToFirst();

        //現在登録されている都道府県を先頭に
        name.add(cursor.getString(0));

        List<String> prefectureAll = getPrefecture();

        //同じ都道府県を削除
        prefectureAll.remove(cursor.getString(0));

        name.addAll(prefectureAll);
        Log.e("cursor", cursor.getString(0));
        Log.e("removePrefecture :", name.get(39));
        cursor.close();
        return name;
    }

    private int getSelectPrefecture() {
        Cursor cursor = db.query(MyHelper.CODE_MASTER_TABLE_NAME,
                new String[] {
                        MyHelper.CodeMColumns.code
                }, String.format("%s = %d AND %s = \"%s\"",
                        MyHelper.CodeMColumns.codeId, 1,
                        MyHelper.CodeMColumns.name, prefectureSpinner.getSelectedItem()),
                null,null,null,null);
        cursor.moveToFirst();
        Log.e("logggg :", "" + prefectureSpinner.getSelectedItem());
        int selectPrefecture =
                cursor.getInt(0);
        cursor.close();

        return selectPrefecture;
    }

    private void updateAccount(String mailAddress) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyHelper.AccountColumns.firstName, editFirstName.getText().toString());
        contentValues.put(MyHelper.AccountColumns.lastName, editLastName.getText().toString());

        contentValues.put(MyHelper.AccountColumns.prefectureId, getSelectPrefecture());
        contentValues.put(MyHelper.AccountColumns.address, editAddress.getText().toString());
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
