package com.media9.diary;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	
	CollectionPagerAdapter mCollectionPagerAdapter;
	boolean avviato = false;

	

	ViewPager mViewPager;
	static Button invia;
	static String testo;
	static String testoDomani;
	static EditText etOggi;
	static TextView dataOggi;
	static TextView tvOggi;
	static EditText etDomani;
	static TextView tvDomani;
	static Button inviaDomani;
	static Button lista;
	static Button listaDomani;
	static DiarioDataSource dds;
	static Button confermaOggi;
	static ImageView fotoView;
	static Intent i;
	static Bitmap picture;
	static String ciaociao;
	static String dataDiDomani;
	static TextView tvDataDomani;
	static Typeface font;
	static Uri uriOggi;
	static String path = "NOFOTO";
	static String pathDomani ="NOFOTO";
	static SharedPreferences preferenze_oggi;
	static SharedPreferences preferenze_domani;
	static boolean modificato = false;
	static String testoDiario;
	static String testoDiarioDomani;
	static ImageView fotoViewDomani;
	static String nomeFotoDomani;
	static String dataSalvataggioDomani;
	int pagina;

	static Calendar ci = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"),
			Locale.ITALY);
	static int tomorrow = Calendar.DATE;
	static Date provadata = ci.getTime();
	static Format formatter = new SimpleDateFormat("EEEE" + " " + "dd" + " "
			+ "MMMM");
	static Format formatterCompleto = new SimpleDateFormat(
			"EEEE-dd-MMMM-yyyy HH:mm:ss.SSSZ");
	static Format formatterSalvataggio = new SimpleDateFormat(
			"EEEE dd MMMM yyyy HH:mm");
	static String dataDiOggi = formatter.format(provadata);
	static String dataSalvataggio = formatterSalvataggio.format(provadata);
	static String nomeFotoOggi = formatterCompleto.format(provadata);
	static Date dataDomani;

	int pagSelezionata = 0;

	static public Context ctx;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ctx = this;
		
		
		if (getIntent().hasExtra("foto")) {
			pagina = getIntent().getIntExtra("pagina", 5);
			if (pagina == 0) {
				path = getIntent().getStringExtra("foto");
			}
			if (pagina == 1) {
				Log.e("varpagina", "qui entra");
				pathDomani = getIntent().getStringExtra("foto");
			}

		} else {
			path = "NOFOTO";
			pathDomani = "NOFOTO";
		}
		preferenze_oggi = PreferenceManager.getDefaultSharedPreferences(this);
		preferenze_domani = PreferenceManager.getDefaultSharedPreferences(this);

		mCollectionPagerAdapter = new CollectionPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mCollectionPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
		if(getIntent().hasExtra("pagina")){
			pagina = getIntent().getIntExtra("pagina", 5);
			mViewPager.setCurrentItem(pagina);
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						
						switch (position) {
						case 0:
							Log.e("Pagina Selezionata", "0");
							pagSelezionata = 0;
							break;
						case 1:
							Log.e("Pagina Selezionata", "1");
							pagSelezionata = 1;
							break;
						}
					}
				});
		

	}

	public static void savePreferences() {
		preferenze_oggi = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = preferenze_oggi.edit();
		editor.putString("testo", tvOggi.getText().toString()
				+ etOggi.getText().toString());
		editor.putString("foto", path);
		editor.commit();

		preferenze_domani = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editorDomani = preferenze_domani.edit();
		editorDomani.putString("testoTomorrow", tvDomani.getText().toString()
				+ etDomani.getText().toString());
		editorDomani.putString("fotoDomani", path);
		editorDomani.commit();

	}



	public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

		final int NUM_ITEMS = 2; // number of tabs

		public CollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new TabFragment();
			Bundle args = new Bundle();
			args.putInt(TabFragment.ARG_OBJECT, i);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			String tabLabel = null;
			switch (position) {
			case 0:
				tabLabel = getString(R.string.label1);
				break;
			case 1:
				tabLabel = getString(R.string.label2);
				break;
			case 2:
			}
			return tabLabel;
		}
	}


	public static class TabFragment extends Fragment {
		public static final String ARG_OBJECT = "object";
		int nView = 0;

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			font = Typeface.createFromAsset(getActivity().getAssets(),
					"florin.otf");

			if (nView == 0) {
				fotoView = (ImageView) getActivity()
						.findViewById(R.id.fotoView);
				fotoView.setImageResource(R.drawable.avatar);
				etOggi = (EditText) getActivity().findViewById(R.id.etOggi);
				tvOggi = (TextView) getActivity().findViewById(R.id.tvOggi);
				testo = preferenze_oggi.getString("testo", "");
				tvOggi.setText(testo);
				testo = "";

				confermaOggi = (Button) getActivity().findViewById(
						R.id.confermaOggi);

				dataOggi = (TextView) getActivity().findViewById(R.id.dataOggi);
				String[] dataStringa = dataDiOggi.split(" ");

				dataOggi.setText(dataStringa[0].substring(0, 1).toUpperCase()
						+ dataStringa[0].substring(1) + " " + dataStringa[1]
						+ " " + dataStringa[2].substring(0, 1).toUpperCase()
						+ dataStringa[2].substring(1));
				dataOggi.setTypeface(font);
				etOggi.setTypeface(font);
				tvOggi.setTypeface(font);
				
				etOggi.setOnKeyListener(new View.OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if ((event.getAction() == KeyEvent.ACTION_DOWN)
								&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
							if(tvOggi.getText().toString().length()<1){
								tvOggi.setText(etOggi.getText().toString());
								etOggi.setText("");

							}
							else{
							tvOggi.setText(tvOggi.getText().toString() + " "
									+ etOggi.getText().toString());
							etOggi.setText("");
							}
							return true;
						}
						return false;
					}
				});
				lista = (Button) getActivity().findViewById(R.id.lista);

				//
				if (!path.equals("NOFOTO")) {

					salvaFoto(path, fotoView);

				}
				lista.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickLista();

					}
				});

				tvOggi.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						etOggi.setText(tvOggi.getText().toString());
						tvOggi.setText("");
						return false;
					}
				});

				confermaOggi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						conferma(etOggi, tvOggi, fotoView, dataSalvataggio, testoDiario, path);

					}
				});

				fotoView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickFoto(0, nomeFotoOggi);
					}

				});

			} else if (nView == 1) {

				tvDataDomani = (TextView) getActivity().findViewById(
						R.id.dataDomani);
				tvDataDomani.setText(dataDiDomani);

				etDomani = (EditText) getActivity().findViewById(R.id.etDomani);
				tvDomani = (TextView) getActivity().findViewById(R.id.tvDomani);
				inviaDomani = (Button) getActivity().findViewById(
						R.id.confermaDomani);
				tvDataDomani.setTypeface(font);
				etDomani.setTypeface(font);
				tvDomani.setTypeface(font);
				testoDomani = preferenze_domani.getString("testoTomorrow", "");
				tvDomani.setText(testoDomani);
				testoDomani = "";

				listaDomani = (Button) getActivity().findViewById(R.id.listaDomani);
				fotoViewDomani = (ImageView) getActivity().findViewById(
						R.id.fotoViewDomani);
				etDomani.setOnKeyListener(new View.OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if ((event.getAction() == KeyEvent.ACTION_DOWN)
								&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
							tvDomani.setText(tvDomani.getText().toString() + " "
									+ etDomani.getText().toString());
							etDomani.setText("");
							return true;
						}
						return false;
					}
				});
				savePreferences();

				if (modificato == false) {
					ci.add(tomorrow, 1);
					dataDomani = ci.getTime();
					dataDiDomani = formatter.format(dataDomani);
					dataSalvataggioDomani=  formatterSalvataggio.format(dataDomani);
					String[] dataStringa = dataDiDomani.split(" ");
					Log.e("provacalendario", dataDiDomani);

					tvDataDomani.setText(dataStringa[0].substring(0,1).toUpperCase()
							+ dataStringa[0].substring(1)
							+ " "
							+ dataStringa[1]
							+ " "
							+ dataStringa[2].substring(0,1).toUpperCase()
							+ dataStringa[2].substring(1));
					modificato = true;
					nomeFotoDomani = formatterCompleto.format(dataDomani);
				}

				if (!pathDomani.equals("NOFOTO")) {
					Log.e("impostafoto", "qui entra?");
					salvaFoto(pathDomani, fotoViewDomani);
				}

				listaDomani.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.e("click", "qui entra?");
						clickLista();
					}
				});

				inviaDomani.setOnClickListener(new OnClickListener()

				{

					@Override
					public void onClick(View v) {
						
						conferma(etDomani, tvDomani, fotoViewDomani,dataSalvataggioDomani,
								testoDomani, pathDomani);

					}
				});

				fotoViewDomani.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickFoto(1, nomeFotoDomani);
					}

				});
				
				tvDomani.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						etDomani.setText(tvDomani.getText().toString());
						tvDomani.setText("");
						return false;
					}
				});

			}
		}

		// metodi per entrambi i giorni

		
		//questo metodo gestisce cosa succede quando si clicca sull'avatar nell'angolo della pagina
		public void clickFoto(int varpagina, String nomeFoto) {

			savePreferences();
			Intent i = new Intent(getActivity(), Foto.class);
			i.putExtra("varpagina", varpagina);
			i.putExtra("datadelgiorno", nomeFoto);
			startActivity(i);

		}

		public void salvaFoto(String pathDellaFoto, ImageView foto) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(pathDellaFoto, options);
			int width = options.outWidth / 320 + 1;
			int height = options.outHeight / 480 + 1;
			int sampleSize = Math.max(width, height);
			options.inSampleSize = sampleSize;
			options.inJustDecodeBounds = false;
			Bitmap immagine = BitmapFactory.decodeFile(pathDellaFoto, options);
			foto.setImageBitmap(immagine);
			savePreferences();
			
			
		}

		public void conferma(EditText edittext, TextView textview,
				ImageView immagine,String dataNota, String testoNota, String pathFoto) {

			if (textview.getText().toString().length() == 0
					&& edittext.getText().toString().length() == 0) {
				Toast.makeText(getActivity().getApplicationContext(),
						"La nota è vuota!", Toast.LENGTH_LONG).show();
			} else {
				if (textview.getText().toString().length() < 2) {
					testoNota = edittext.getText().toString();
					textview.setText("");
					edittext.setText("");
					savePreferences();
				} else {
					testoNota = textview.getText().toString();
				}
				dds = new DiarioDataSource(getActivity().getBaseContext());
				dds.createDay(new Day(dataNota, testoNota, pathFoto));
				Toast.makeText(getActivity().getApplicationContext(),
						"Nota salvata!", Toast.LENGTH_SHORT).show();
				textview.setText("");
				edittext.setText("");
				pathFoto = "";
				immagine.setImageResource(R.drawable.avatar);

				savePreferences();
			}

		}

		public void clickLista() {

			Intent intent;
			intent = new Intent(getActivity(), Giornate.class);
			startActivity(intent);
		}

		// /fine metodi per entrambi i giorni

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			int position;
			Bundle args = getArguments();
			position = args.getInt(ARG_OBJECT);
			// }

			int tabLayout = 0;
			switch (position) {
			case 0:
				tabLayout = R.layout.pagina1;
				nView = 0;

				break;
			case 1:
				tabLayout = R.layout.pagina2;
				nView = 1;
				break;
			}

			View rootView = inflater.inflate(tabLayout, container, false);

			return rootView;
		}

	}

}