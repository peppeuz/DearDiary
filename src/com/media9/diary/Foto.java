package com.media9.diary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Foto extends Activity implements OnClickListener {

	private static final int CAMERA_REQUEST = 0;

	protected static int CAMERA_PICTURE_RESULT;
	protected int var = 0;
	ImageView test;
	Button foto;
	Button galleriaFoto;

	Button ok;
	Bitmap thumbnail = null;
	File file;
	static Uri capturedImageUri = null;
	Bundle extras;
	String datafoto;
	File outputFile;
	ByteArrayOutputStream bs;
	String unodueprova;
	String path;
	SharedPreferences preferenze_oggi;
	SharedPreferences preferenze_domani;
	String testoPagina1;
	String fotoPagina1;
	String fotoPagina2;
	int varpagina;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// uploadPrefs();
		preferenze_oggi = PreferenceManager.getDefaultSharedPreferences(this);
		testoPagina1 = preferenze_oggi.getString("testo", "null");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto);
		foto = (Button) findViewById(R.id.fotocamera);
		galleriaFoto = (Button) findViewById(R.id.galleriaFoto);
		test = (ImageView) findViewById(R.id.imageView1);
		ok = (Button) findViewById(R.id.buttonOk);
		galleriaFoto.setOnClickListener(this);
		foto.setOnClickListener(this);
		ok.setOnClickListener(this);
		extras = getIntent().getExtras();
		varpagina = extras.getInt("varpagina");

	}

	public void uploadPrefs() {
		preferenze_oggi = PreferenceManager.getDefaultSharedPreferences(this);
		preferenze_domani = PreferenceManager.getDefaultSharedPreferences(this);

		// testoPagina1 = preferenze_oggi.getString("testo", "null");
		fotoPagina1 = preferenze_oggi.getString("foto", "null");
		fotoPagina2 = preferenze_domani.getString("foto", "null");

	}

	public void savePreferences() {
		preferenze_oggi = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferenze_oggi.edit();
		// editor.putString("testo", testoPagina1);
		editor.putString("foto", path);
		editor.commit();

		preferenze_domani = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editorDomani = preferenze_domani.edit();
		editorDomani.putString("fotoDomani", path);
		editorDomani.commit();


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
					unodueprova = extras.getString("datadelgiorno");
					thumbnail = (Bitmap) imageReturnedIntent.getExtras().get(
							"data");
					test.setImageBitmap(thumbnail);

					file = new File(getString(R.string._sdcard_deardiary_));
					// have the object build the directory structure, if needed.
					file.mkdirs();
					// create a File object for the output file

					File outputFile = new File(file, unodueprova + ".png");
					
					if (var == 1)
						path = getString(R.string._sdcard_deardiary_)
								+ unodueprova + ".png";
					

					try {
						file.createNewFile();
						FileOutputStream ostream = new FileOutputStream(
								outputFile);
						thumbnail.compress(CompressFormat.PNG, 100, ostream);
						ostream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

			break;
		case 1:
			if (resultCode == RESULT_OK) {

				Log.e("<2iugbkjft",
						getRealPathFromURI(imageReturnedIntent.getData()));
				InputStream in;
				try {

					in = getContentResolver().openInputStream(
							imageReturnedIntent.getData());
					// get picture size.
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeStream(in, null, options);

					in.close();
					// resize the picture for memory.
					int width = options.outWidth / 320 + 1;
					int height = options.outHeight / 480 + 1;
					int sampleSize = Math.max(width, height);
					options.inSampleSize = sampleSize;
					options.inJustDecodeBounds = false;
					if (var == 2)
						path = getRealPathFromURI(imageReturnedIntent.getData())
								.toString();
					in = getContentResolver().openInputStream(
							imageReturnedIntent.getData());
					// convert to bitmap with declared size.
					thumbnail = BitmapFactory.decodeStream(in, null, options);
					in.close();
					test.setImageBitmap(thumbnail);
				

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		}
	}

	private String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(this, contentUri, proj, null,
				null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == foto) {
			var = 1;
			
			Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(takePicture, 0);

		}

		if (v == galleriaFoto) {
			var = 2;

			Intent pickPhoto = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(pickPhoto, 1);// one can be replced with any
													// action code
		}

		if (v == ok) {
			Intent main;
			main = new Intent(this, MainActivity.class);
			bs = new ByteArrayOutputStream();
			if (path != null) {
				
				main.putExtra("foto", path);
				main.putExtra("pagina", varpagina);
			}
		
			startActivity(main);
		}

	}

}