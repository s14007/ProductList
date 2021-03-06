package com.example.application.products;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.products.Dialog.EditTextDialog;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private MyHelper myHelper;
    private Handler mHandler;

    private List<ProductItem> selectProduct;

    private AccountInfo accountInfo;
    private SQLiteDatabase db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, Menu.NONE, "ログアウト");
        menu.add(Menu.NONE, 1, Menu.NONE, "アカウント情報の変更");
        menu.add(Menu.NONE, 2, Menu.NONE, "アカウントの削除");
        menu.add(Menu.NONE, 3, Menu.NONE, "注文のキャンセル");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret = true;
        switch (item.getItemId()) {
            default:
                ret = super.onOptionsItemSelected(item);
                break;
            case 0:
                Log.e("Options:", "selectLogout");
                break;
            case 1:
                Intent intent = new Intent(this, ChangeAccountInfo.class);
                startActivity(intent);
                break;
            case 2:

                break;
            case 3:
                break;
        }
        return ret;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

        ProductItem item = (ProductItem) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, EditProduct.class);

        intent.putExtra("mode", "edit");

        intent.putExtra("_id", item._id);
        intent.putExtra("id", item.id);
        intent.putExtra("name", item.name);
        intent.putExtra("price", item.price);
        intent.putExtra("stock", item.stock);
        intent.putExtra("prefecture", item.prefecture);


        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, EditProduct.class);

        intent.putExtra("mode", "add");

        startActivity(intent);

    }

    private class ProductItem {
        int _id;
        String id;
        String name;
        int price;
        int stock;
        String prefecture;
        boolean flag;

        void setCheckFlag(boolean checkflg) {
            this.flag = checkflg;
        }

        boolean getCheckFlag() {
            return flag;
        }
    }

    private List<ProductItem> itemList;
    private ItemAdapter adapter;

    private void setProductData() {

/*
        ProductItem item = new ProductItem();
        item._id = 1;
        item.id = "A01";
        item.name = "赤鉛筆";
        item.price = 50;
        item.stock = 100;
        itemList.add(item);

        item = new ProductItem();
        item._id = 2;
        item.id = "A02";
        item.name = "青鉛筆";
        item.price = 50;
        item.stock = 50;
        itemList.add(item);
*/

        selectProductList();

        //Log.d("setProductData", "size = " + itemList.size());

        //adapter.notifyDataSetChanged();

    }

    private void selectProductList() {

        // 1. SQLiteDatabaseオブジェクトを取得
        SQLiteDatabase db = myHelper.getReadableDatabase();

        // 2. query()を呼び、検索を行う
        Cursor cursor =
                db.query(MyHelper.TABLE_NAME, null, null, null, null, null,
                        MyHelper.Columns._ID + " ASC");


        // 3. 読込位置を先頭にする。falseの場合は結果0件
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return;
        }

        // 4. 列のindex(位置)を取得する
        int _idIndex = cursor.getColumnIndex(MyHelper.Columns._ID);
        int idIndex = cursor.getColumnIndex(MyHelper.Columns.id);
        int nameIndex = cursor.getColumnIndex(MyHelper.Columns.productName);
        int priceIndex = cursor.getColumnIndex(MyHelper.Columns.price);
        int stockIndex = cursor.getColumnIndex(MyHelper.Columns.stock);
        int prefectureIndex = cursor.getColumnIndex(MyHelper.Columns.prefecture);

        // 5. 行を読み込む。
        itemList.removeAll(itemList);
        do {
            ProductItem item = new ProductItem();
            item._id = cursor.getInt(_idIndex);
            item.id = cursor.getString(idIndex);
            item.name = cursor.getString(nameIndex);
            item.price = cursor.getInt(priceIndex);
            item.stock = cursor.getInt(stockIndex);
            item.prefecture = cursor.getString(prefectureIndex);


            /*Log.d("selectProductList",
                    "_id = " + item._id + "\n" +
                    "id = " + item.id + "\n" +
                    "name = " + item.name + "\n" +
                    "price = " + item.price + "\n" +
                    "stock = " + item.stock + "\n" +
                    "prefecture = " + item.prefecture);*/

            itemList.add(item);

            // 読込位置を次の行に移動させる
            // 次の行が無い時はfalseを返すのでループを抜ける
        } while (cursor.moveToNext());

        Cursor c = db.query(MyHelper.ACCOUNT_TABLE_NAME, new String[]{
                MyHelper.AccountColumns.firstName,
                MyHelper.AccountColumns.lastName,
                MyHelper.AccountColumns.prefectureId,
                MyHelper.AccountColumns.address,
                MyHelper.AccountColumns.mailAddress,
                MyHelper.AccountColumns.password
        }, null, null, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return;
        }

        c.moveToFirst();

        Log.e("test:", String.format("%s | %s | %d | %s | %s | %s",
                c.getString(0), c.getString(1), c.getInt(2), c.getString(3), c.getString(4),
                c.getString(5)));


        // 6. Cursorを閉じる
        cursor.close();
        c.close();

        // 7. データベースを閉じる
        db.close();

        //return itemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Log.d("ProductList", "onCreate");

        // MyHelperオブジェクトを作り、フィールドにセット
        myHelper = MyHelper.getInstance(this);

        //ハンドラを生成
        mHandler = new Handler();
        accountInfo = AccountInfo.getInstance();

        //initTable();
        itemList = new ArrayList<>();
        adapter =
                new ItemAdapter(getApplicationContext(), 0, itemList);

        testAccountInsert();
        loginIfNeeded();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("Log :", "restart!");

        adapter.setNotifyOnChange(true);
        ListView listView =
                (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Table取得したデータをListViewにセットするためのスレッド
        (new Thread(new Runnable() {
            @Override
            public void run() {
                setProductData();

                //メインスレッドのメッセージキューにメッセージを登録します。
                mHandler.post(new Runnable() {
                    //run()の中の処理はメインスレッドで動作されます。
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        })).start();
    }

    public void loginIfNeeded() {
        Log.e("accountInfo :", accountInfo.getMailAddress() + " ");
        if (null == accountInfo.getMailAddress()) {
            //ログインダイアログ生成
            EditTextDialog dialogFragment = new EditTextDialog();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getFragmentManager(), "dialog_fragment");
        } else {
            //製品を取得
            adapter.setNotifyOnChange(true);
            ListView listView =
                    (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            // Table取得したデータをListViewにセットするためのスレッド
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    initTable();
                    setProductData();

                    //メインスレッドのメッセージキューにメッセージを登録します。
                    mHandler.post(new Runnable() {
                        //run()の中の処理はメインスレッドで動作されます。
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            })).start();
        }
    }

    public boolean onDialogLoginClick(String mailAddress, String password) {
        Log.e("dialogOk :", "click");
        if (null == queryAccount(mailAddress, password)) {
            Log.e("Log :", "何もしない");
            Toast.makeText(ProductList.this, "ねーよ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //製品取得
            adapter.setNotifyOnChange(true);
            ListView listView =
                    (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            // Table取得したデータをListViewにセットするためのスレッド
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    initTable();
                    setProductData();

                    //メインスレッドのメッセージキューにメッセージを登録します。
                    mHandler.post(new Runnable() {
                        //run()の中の処理はメインスレッドで動作されます。
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            })).start();
        }
        return true;
    }

    public String queryAccount(String mailAddres, String password) {
        //AccountInfoクラスで都道府県IDとメールアドレスを保持する。
        Cursor cursor = db.query(MyHelper.ACCOUNT_TABLE_NAME,
                new String[]{
                        MyHelper.AccountColumns.prefectureId,
                        MyHelper.AccountColumns.mailAddress
                },
                String.format("%s = %s AND %s = %s",
                        MyHelper.AccountColumns.mailAddress, "\"" + mailAddres + "\"",
                        MyHelper.AccountColumns.password, "\"" + password + "\""),
                null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            return null;
        } else {

            Log.e("cursorSize :", String.valueOf(cursor.getCount()));

            Log.e("accountInfo :", cursor.getString(cursor.getColumnIndex(MyHelper.AccountColumns.prefectureId)));
            Log.e("accountInfo :", cursor.getString(cursor.getColumnIndex(MyHelper.AccountColumns.mailAddress)));

            accountInfo.setPrefectureId(cursor.getInt(cursor.getColumnIndex(MyHelper.AccountColumns.prefectureId)));
            accountInfo.setMailAddress(cursor.getString(cursor.getColumnIndex(MyHelper.AccountColumns.mailAddress)));

            Log.e("accountInfo :", String.valueOf(accountInfo.getPrefectureId()));

            cursor.close();
        }

        return accountInfo.getMailAddress();
    }

    private class ItemAdapter extends ArrayAdapter<ProductItem> {
        private LayoutInflater inflater;

        ItemAdapter(Context context, int resouce,
                    List<ProductItem> objects) {
            super(context, resouce, objects);
            inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

            View view = inflater.inflate(R.layout.product_row, null, false);

            final ProductItem item = getItem(position);

            if (item != null) {
                TextView nameView = (TextView) view.findViewById(R.id.name);
                TextView priceView = (TextView) view.findViewById(R.id.price);
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

                selectProduct = new ArrayList<>();
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {
                            item.setCheckFlag(true);
                            selectProduct.add(itemList.get(position));
                            Log.e("select:", selectProduct.get(selectProduct.size() - 1).name);
                        } else {
                            item.setCheckFlag(false);
                            selectProduct.remove(itemList.get(position));
                        }
                    }
                });

                nameView.setText(item.name);
                priceView.setText(getString(R.string.money_mark) + item.price);
                checkBox.setChecked(item.getCheckFlag());

                Button btnOK = (Button) findViewById(R.id.btn_ok);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (ProductItem productItem : selectProduct) {
                            Log.e("ProductName :", productItem.id);
                            Log.e("ProductPrice :", String.valueOf(productItem.price));

                        }
                    }
                });
            }
            return view;
        }
    }

    /**
     * テーブルを初期化するための処理
     */

    private class ProductDbItem {
        String id;
        String name;
        int price;
        int stock;
        int prefecture;
    }

    /*private class AccountDb {
        String firstName;
        String lastName;
        int prefectureId;
        String address;
        String mailAddress;
        String password;
    }*/

    private List<ProductDbItem> itemDbList;

    private void setProductDbData() {

        itemDbList = new ArrayList<>();

        ProductDbItem item = new ProductDbItem();
        item.id = "A01";
        item.name = "赤鉛筆";
        item.price = 50;
        item.stock = 100;
        item.prefecture = 1;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A02";
        item.name = "青鉛筆";
        item.price = 50;
        item.stock = 50;
        item.prefecture = 13;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A03";
        item.name = "消しゴム";
        item.price = 75;
        item.stock = 1000;
        item.prefecture = 19;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A04";
        item.name = "三角定規";
        item.price = 120;
        item.stock = 10;
        item.prefecture = 37;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A05";
        item.name = "ボールペン黒";
        item.price = 80;
        item.stock = 25;
        item.prefecture = 38;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A06";
        item.name = "ボールペン赤";
        item.price = 90;
        item.stock = 24;
        item.prefecture = 23;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A07";
        item.name = "３色ボールペン";
        item.price = 120;
        item.stock = 30;
        item.prefecture = 10;
        itemDbList.add(item);

        //test
        item = new ProductDbItem();
        item.id = "A01";
        item.name = "赤鉛筆";
        item.price = 50;
        item.stock = 100;
        item.prefecture = 1;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A02";
        item.name = "青鉛筆";
        item.price = 50;
        item.stock = 50;
        item.prefecture = 13;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A03";
        item.name = "消しゴム";
        item.price = 75;
        item.stock = 1000;
        item.prefecture = 19;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A04";
        item.name = "三角定規";
        item.price = 120;
        item.stock = 10;
        item.prefecture = 37;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A05";
        item.name = "ボールペン黒";
        item.price = 80;
        item.stock = 25;
        item.prefecture = 38;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A06";
        item.name = "ボールペン赤";
        item.price = 90;
        item.stock = 24;
        item.prefecture = 23;
        itemDbList.add(item);

        item = new ProductDbItem();
        item.id = "A07";
        item.name = "３色ボールペン";
        item.price = 120;
        item.stock = 30;
        item.prefecture = 10;
        itemDbList.add(item);

    }


    private void initTable() {
        Log.d("ProductList", "initTable");
        db = myHelper.getWritableDatabase();

        // 一旦削除
        int count = db.delete(MyHelper.TABLE_NAME, null, null);
        Log.d("initTable", "count =" + count);

        setProductDbData();

        for (int i = 0; i < itemDbList.size(); i++) {
            ProductDbItem item = itemDbList.get(i);

            // 列に対応する値をセットする
            ContentValues values = new ContentValues();
            values.put(MyHelper.Columns.id, item.id);
            values.put(MyHelper.Columns.productName, item.name);
            values.put(MyHelper.Columns.price, item.price);
            values.put(MyHelper.Columns.stock, item.stock);
            values.put(MyHelper.Columns.prefecture, item.prefecture);

            // データベースに行を追加する
            long id = db.insert(MyHelper.TABLE_NAME, null, values);
            if (id == -1) {
                Log.d("Database", "行の追加に失敗したよ");
            }
        }

//        testAccountInsert();
    }

    private void testAccountInsert() {
        db = myHelper.getWritableDatabase();

        // 一旦削除
        int count = db.delete(MyHelper.ACCOUNT_TABLE_NAME, null, null);
        Log.d("initTable", "count =" + count);

        ContentValues values = new ContentValues();
        values.put(MyHelper.AccountColumns.firstName, "隆史");
        values.put(MyHelper.AccountColumns.lastName, "山田");
        values.put(MyHelper.AccountColumns.prefectureId, 47);
        values.put(MyHelper.AccountColumns.address, "高知市鏡竹奈路");
        values.put(MyHelper.AccountColumns.mailAddress, "test@gmail.com");
        values.put(MyHelper.AccountColumns.password, "test");

        db.insert(MyHelper.ACCOUNT_TABLE_NAME, null, values);

//        queryAccount();
    }


}
