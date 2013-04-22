package com.media9.diary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModificaPagina extends Activity implements OnClickListener {
	
	TextView data;
	EditText testo;
	ImageView foto;
	String dataDaModificare;
	String testoDaModificare;
	String pathDaModificare;
	long id;
	Day giorno;
	Bundle extras;
	Bitmap fotoVecchia;
	DiarioDataSource dds;
	Context ctx=this;
	Button conferma;
	String nuovoTesto;
	String nuovaData;
	String nuovoPath;
	Typeface font;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifica_pagina);
		//Button conferma = (Button) findViewById(R.id.confermaModifica);
		font = Typeface.createFromAsset(getAssets(),
				"florin.otf");
		data = (TextView) findViewById(R.id.dataModifica);
		testo = (EditText) findViewById(R.id.etModifica);
		foto = (ImageView) findViewById(R.id.immagineModifica);
		conferma = (Button) findViewById(R.id.confermaModifica);
		extras = getIntent().getExtras();
		dataDaModificare = extras.getString("data");
		testoDaModificare = extras.getString("testo");
		pathDaModificare = extras.getString("foto");
		id = extras.getLong("id");
		dds = new DiarioDataSource(ctx);
		data.setTypeface(font);
		
		data.setText(dataDaModificare);
		if (!pathDaModificare.equals("NOFOTO")){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathDaModificare, options);
		int width = options.outWidth / 320 + 1;
		int height = options.outHeight / 480 + 1;
		int sampleSize = Math.max(width, height);
		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;
		fotoVecchia = BitmapFactory.decodeFile(pathDaModificare, options);
		foto.setImageBitmap(fotoVecchia);}
		else
		{
			foto.setImageResource(R.drawable.avatar);
		}
		testo.setTypeface(font);
		testo.setText(testoDaModificare);
		Toast.makeText(	this, ""+id, Toast.LENGTH_LONG).show();
		conferma.setOnClickListener(this);
		
		
		//dds.editDay(day)
		

		// extras
		// data.setText(giorno.getData());
		// testo.setText(giorno.getTesto());
		//
	}

	@Override
	public void onClick(View v) {
		if (v==conferma){
			nuovoTesto = testo.getText().toString();
			nuovaData = data.getText().toString();
			nuovoPath=pathDaModificare; //temporaneamente
			giorno = new Day(nuovaData, nuovoTesto, nuovoPath);
			giorno.setId(id);
			dds.editDay(giorno);
			Toast.makeText(
					ctx,"Modifica completata con successo",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, Giornate.class);
			startActivity(i);
		}
		// TODO Auto-generated method stub
		
	}

}