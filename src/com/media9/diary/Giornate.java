package com.media9.diary;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class Giornate extends Activity {
	DiarioDataSource dds;
	ListView listGiornate;
	Context ctx = this;
	List<Day> days;
	Typeface font;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giornate);
		font = Typeface.createFromAsset(getAssets(), "florin.otf");

		dds = new DiarioDataSource(this); // apriamo il db

		DiarioDataSource dds = new DiarioDataSource(this);
		days = dds.getAllDay();

		listGiornate = (ListView) findViewById(R.id.list_giornate);
		listGiornate.setAdapter(new MioAdapter(this, R.layout.list_row, days,
				font));
	

		listGiornate.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {	
				Log.e("proviamo", "se entra nell'onlongclick");
				DialogOpzioni dialog = new DialogOpzioni(ctx, days.get(position));
				dialog.setTitle("Cosa vuoi fare?");
				dialog.show();
				return false;
			}
		});

		// Cursor c=db.fetchProducts(); // query startManagingCursor(c);
		/*
		 * 
		 * 
		 * 
		 * @SuppressWarnings("deprecation") SimpleCursorAdapter adapter=new
		 * SimpleCursorAdapter( //semplice adapter per i cursor this,
		 * R.layout.data, //il layout di ogni riga/prodotto c, new
		 * String[]{Database
		 * .ProductsMetaData.DATA,Database.ProductsMetaData.TESTO_GIORNO
		 * },//questi colonne new int[]{R.id.dataDiario,R.id.testoDiario});//in
		 * queste views
		 * 
		 * 
		 * 
		 * 
		 * 
		 * productsLv.setAdapter(adapter); //la listview ha questo adapter
		 * 
		 * 
		 * 
		 * //qui vediamo invece come reperire i dati e usarli, in questo caso li
		 * stampiamo in una textview
		 * 
		 * int nameCol=c.getColumnIndex(Database.ProductsMetaData.DATA);
		 * //indici delle colonne int
		 * priceCol=c.getColumnIndex(Database.ProductsMetaData.TESTO_GIORNO);
		 * if(c.moveToFirst()){ //se va alla prima entry, il cursore non è vuoto
		 * do {
		 * 
		 * productsTv.append("Product Name:"+c.getString(nameCol)+", Price:"+c.
		 * getString(priceCol).substring(0,5)+"..."); //estrazione dei dati
		 * dalla entry del cursor
		 * 
		 * } while (c.moveToNext());//iteriamo al prossimo elemento }
		 */
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("onresume", "onresume");
		listGiornate.setAdapter(new MioAdapter(this, R.layout.list_row, days,
				font));

	}

	@Override
	public void onRestart() {
		super.onResume();
		Log.e("onRestart", "onRestart");
		listGiornate.setAdapter(new MioAdapter(this, R.layout.list_row, days,
				font));

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onStop() {
		super.onStop();

	}



}