package com.media9.diary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DialogOpzioni extends Dialog implements
		android.view.View.OnClickListener {
	Button cancella;
	Button modifica;
	Button mostra;
	long id;
	Day giorno;
	DiarioDataSource dds;
	String testo;
	String data;
	String foto;
	final Context ctx;
	MioAdapter adapter;

	public DialogOpzioni(Context context, Day day) {
		super(context);
		// TODO Auto-generated constructor stub

		giorno = day;
		ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_opzioni);
		cancella = (Button) findViewById(R.id.bottoneCancella);
		modifica = (Button) findViewById(R.id.bottoneModifica);
		mostra = (Button) findViewById(R.id.bottoneMostra);
		data = giorno.getData();
		testo = giorno.getTesto();
		foto = giorno.getNomefoto();
		id = giorno.getId();
		cancella.setOnClickListener(this);
		mostra.setOnClickListener(this);
		modifica.setOnClickListener(this);
		dds = new DiarioDataSource(ctx);
		

	}

	@Override
	public void onClick(View v) {

		
		if (v==cancella){
		
			id =giorno.getId();
			Toast.makeText(ctx,""+id,Toast.LENGTH_LONG).show();
			dds.deleteDay(giorno);
			Intent i = new Intent(ctx, Giornate.class);
			ctx.startActivity(i);
			

			
		}
		
		if (v==mostra){
		
		 Intent i;
		 i= new Intent(ctx, MostraPagina.class);
		 i.putExtra("data", testo);
		 i.putExtra("testo", data);
		 i.putExtra("foto", foto);
		 getContext().startActivity(i);
		}
		
		
		if (v==modifica){
			Intent modifica;
			modifica = new Intent(getContext(), ModificaPagina.class);
			 modifica.putExtra("data", data);
			 modifica.putExtra("testo", testo);
			 modifica.putExtra("foto", foto);
			 modifica.putExtra("id", id);
			getContext().startActivity(modifica);
			
			
			
		}
		
		// TODO Auto-generated method stub
		
	}

}