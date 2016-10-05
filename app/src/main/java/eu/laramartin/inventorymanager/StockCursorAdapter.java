package eu.laramartin.inventorymanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import eu.laramartin.inventorymanager.data.InventoryDbHelper;
import eu.laramartin.inventorymanager.data.StockContract;

/**
 * Created by Lara on 03/10/2016.
 */
public class StockCursorAdapter extends CursorAdapter {


    private InventoryDbHelper dbHelper;

    public StockCursorAdapter(Context context, Cursor c, InventoryDbHelper dbHelper) {
        super(context, c, 0);
        this.dbHelper = dbHelper;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        Button sale = (Button) view.findViewById(R.id.sale);

        String name = cursor.getString(cursor.getColumnIndex(StockContract.StockEntry.COLUMN_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndex(StockContract.StockEntry.COLUMN_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndex(StockContract.StockEntry.COLUMN_PRICE));

        nameTextView.setText(name);
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText(price);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("adapter", "on click view");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("itemId", cursor.getLong(cursor.getColumnIndex(StockContract.StockEntry._ID)));
                context.startActivity(intent);
            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("adapter", "on click button");
                dbHelper.sellOneItem();

            }
        });

    }
}
