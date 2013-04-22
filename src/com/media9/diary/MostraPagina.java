package com.media9.diary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MostraPagina extends Activity {
	Bundle extra;
	ImageView foto;
	TextView data;
	TextView testo;
	String dataGiornata;
	String testoGiornata;
	String fotoGiornata;
	Typeface font;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_pagina);
		extra = getIntent().getExtras();
		foto = (ImageView) findViewById(R.id.fotoGiornata);
		testo = (TextView) findViewById(R.id.testoGiornata);
		data = (TextView) findViewById(R.id.dataGiornata);
		font = Typeface.createFromAsset(getAssets(),
				"florin.otf");

		dataGiornata = extra.getString("data");
		fotoGiornata = extra.getString("foto");
		testoGiornata = extra.getString("testo");
		testo.setText(dataGiornata);
		data.setText(testoGiornata);
		testo.setTypeface(font);
		data.setTypeface(font);
		
		
		if (!fotoGiornata.equals("NOFOTO")){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fotoGiornata, options);
		int width = options.outWidth / 320 + 1;
		int height = options.outHeight / 480 + 1;
		int sampleSize = Math.max(width, height);
		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;
		Bitmap immagine = BitmapFactory.decodeFile(fotoGiornata, options);
		foto.setImageBitmap(immagine);
		}
		else
			foto.setImageResource(R.drawable.avatar);
		
	}

	
}