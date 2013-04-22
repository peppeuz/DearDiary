package com.media9.diary;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DiarioDataSource {

	// Database fields
	private SQLiteDatabase database; //dichiarazione Database
	private DbHelper helper; //dichiarazione Helper
	private String[] allColumns = { DbHelper.ID, DbHelper.DATA, //Array che contiene tutte le colonne del DB
			DbHelper.TESTO_GIORNO, DbHelper.NOME_FOTO };

	public DiarioDataSource(Context context) {
		helper = new DbHelper(context);
	}

	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public void createDay(Day day) {
		open();
		ContentValues values = new ContentValues();
		values.put(DbHelper.DATA, day.getData());
		values.put(DbHelper.TESTO_GIORNO, day.getTesto());
		values.put(DbHelper.NOME_FOTO, day.getNomefoto());
		database.insert(DbHelper.DIARIO, null, values);
		close();

	}

	//cancella riga
	public void deleteDay(Day position) {
		open();
		long id = position.getId();
		System.out.println("Trip deleted with id: " + id);
		database.delete(DbHelper.DIARIO, DbHelper.ID + " = " + id, null);
		close();
	}

	public void editDay(Day day) {
		open();
		ContentValues values = new ContentValues();
		values.put(DbHelper.DATA, day.getData());
		values.put(DbHelper.TESTO_GIORNO, day.getTesto());
		values.put(DbHelper.NOME_FOTO, day.getNomefoto());
		database.update(DbHelper.DIARIO, values,
				DbHelper.ID + " = " + day.getId(), null);
		close();
	}

	public void deleteDay(List<Day> position) {
		open();
		for (int i = 0; i < position.size(); i++) {
			long id = position.get(i).getId();
			System.out.println("Trip deleted with id: " + id);
			database.delete(DbHelper.DIARIO, DbHelper.ID + " = " + id, null);
		}
		close();
	}

	public List<Day> getAllDay() {
		open();
		List<Day> days = new ArrayList<Day>();

		Cursor cursor = database.query(DbHelper.DIARIO, allColumns, null, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Day day = cursorToDay(cursor);
			days.add(day);
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return days;
	}

	private Day cursorToDay(Cursor cursor) {
		Day day = new Day(cursor.getString(1), cursor.getString(2),
				cursor.getString(3));
		day.setId(cursor.getLong(0));

		return day;
	}

}