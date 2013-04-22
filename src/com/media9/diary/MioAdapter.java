package com.media9.diary;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

//import android.widget.ImageView;

public class MioAdapter extends BaseAdapter {
	Context activity;
	List<Day> data;
	LayoutInflater inflater;
	DiarioDataSource dds;
	Typeface font;
	ImageView facebook;
	Day day;
	String testoNota; 
	
	public MioAdapter(Activity a, int layout, List<Day> d, Typeface fonttesto) {
		font = fonttesto;
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);

		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView tvTesto = (TextView) vi.findViewById(R.id.subtitle);
		ImageView foto = (ImageView) vi.findViewById(R.id.list_image);
		facebook = (ImageView) vi.findViewById(R.id.facebook);
		day = data.get(position);
		
		if (day.getTesto().length()>19){
			testoNota= day.getTesto().substring(0,20)+"...";
		}
		else
		{
			testoNota= day.getTesto();

		}
		title.setText(testoNota);
		title.setTypeface(font);
		tvTesto.setText(day.getData());
		tvTesto.setTypeface(font);
		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("qui entra?", "onclick");
				share(day);
			}
		});

		if (day.getNomefoto().equals("NOFOTO")) {
			foto.setImageResource(R.drawable.avatar);
			Log.e("entra nel getnomefoto?", "boh");
		} else

		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(day.getNomefoto(), options);
			// resize the picture for memory.
			int width = options.outWidth / 240 + 1;
			int height = options.outHeight / 320 + 1;
			int sampleSize = Math.max(width, height);
			options.inSampleSize = sampleSize;
			options.inJustDecodeBounds = false;
			// convert to bitmap with declared size.
			Bitmap immagine = BitmapFactory.decodeFile(day.getNomefoto(),
					options);
			foto.setImageBitmap(immagine);
		}
		return vi;
	}

	public void share(Day giorno) {

		Bundle params = new Bundle();
		params.putString("name", "Ecco cosa mi è successo " + giorno.getData());
		params.putString("caption", giorno.getTesto());
		params.putString("description",
				"DearDiary è il tuo diario quotidiano su Android!");
		params.putString("link", "https://www.facebook.com/peppeuz");
		params.putString("picture", "http://imgbin.org/images/12252.png");
		if (Session.getActiveSession() == null) {
			alert();

		} else {
			WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(activity,
					Session.getActiveSession(), params)).setOnCompleteListener(
					new OnCompleteListener() {

						@Override
						public void onComplete(Bundle values,
								FacebookException error) {
							if (error == null) {
								
								final String postId = values
										.getString("post_id");
								if (postId != null) {
									Toast.makeText(activity,
											"Post effettuato correttamente!" 
									//+ postId
											
											,Toast.LENGTH_SHORT).show();
								} else {
									// User clicked the Cancel button
									Toast.makeText(
											activity.getApplicationContext(),
											"Post annullato",
											Toast.LENGTH_SHORT).show();
								}
							} else if (error instanceof FacebookOperationCanceledException) {
								// User clicked the "x" button
								Toast.makeText(
										activity.getApplicationContext(),
										"Post annullato", Toast.LENGTH_SHORT)
										.show();
							} else {
								// Generic, ex: network error
								Toast.makeText(
										activity.getApplicationContext(),
										"Si è presentato un errore durante la pubblicazione",
										Toast.LENGTH_SHORT).show();
							}
						}

					}).build();
			feedDialog.show();
		}
	}

	public void alert() {
		Log.e("il problema è nell'intent?", "entra qui?");
		Toast.makeText(activity,
				"Non sei ancora collegato su Facebbok: esegui il login per condividere la nota!",
				Toast.LENGTH_LONG).show();
		Intent i = new Intent (activity.getApplicationContext(), Intro.class);
		activity.startActivity(i);

//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				activity.getApplicationContext());
//
//		// set title
//		alertDialogBuilder
//				.setTitle("Non hai ancora eseguito l'accesso a Facebook.");
//
//		// set dialog message
//		alertDialogBuilder
//				.setMessage(
//						"Per collegarti, clicca sul logo. Vuoi loggare adesso?")
//				.setCancelable(false)
//				.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						Intent i = new Intent(activity, Intro.class);
//						activity.startActivity(i);
//					}
//				})
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						// if this button is clicked, just close
//						// the dialog box and do nothing
//						dialog.cancel();
//					}
				//});
	}

}