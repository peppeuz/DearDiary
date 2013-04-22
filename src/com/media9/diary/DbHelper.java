package com.media9.diary;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper { // classe che ci aiuta
	// nella creazione del
	// db


	//Costruttore
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static final String DB_NAME = "Diario";// nome del db
	private static final int DB_VERSION = 1; // numero di versione del nostro db
	static final String DIARIO = "diario"; //nome tabella
	static final String ID = "_id";
	static final String DATA = "data";
	static final String TESTO_GIORNO = "testo";
	static final String NOME_FOTO = "nomefoto";

//Query per creare il database
	  private static final String DIARIO_CREATE = 
				 "create table " + DIARIO + "(" 
					  + 	ID 						+	" INTEGER PRIMARY KEY AUTOINCREMENT, "
					  + 	DATA				+	" TEXT,"
					  + 	TESTO_GIORNO				+	" TEXT,"
					  + 	NOME_FOTO				+	" TEXT"
					  + " );";
	
	//Costruttore
	  public DbHelper(Context context) {
		    super(context, DB_NAME, null, DB_VERSION);
		  }

	@Override
	public void onCreate(SQLiteDatabase _db) { 
		// solo quando il db viene
		// creato, creiamo la
		// tabella
		_db.execSQL(DIARIO_CREATE);
	}

	//metodo per aggiornare il Diario
	 @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.e(DbHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + DIARIO);

	    onCreate(db);
	  }
}