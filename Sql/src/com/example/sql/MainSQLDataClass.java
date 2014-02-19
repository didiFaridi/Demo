package com.example.sql;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainSQLDataClass extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		insertData();
		readData();
	}

	private void readData() {
		TextView tvId = (TextView) findViewById(R.id.id);
		SQLHelper help = new SQLHelper(MainSQLDataClass.this);
		
		help.open();
		String data = help.getData(); 
		help.close();
		
	}

	private void insertData() {
		try{
			//tissue
			String tBarcode = "9556113002727";
			String tName = "Premier Tissue";
			String tPrice = "100000";
			//book
			String bBarcode = "9789812437488";
			String bName = "Principles of Marketing";
			String bPrice = "522222142";
			
			SQLHelper help = new SQLHelper(MainSQLDataClass.this);
			help.open();
			help.createEntry(tBarcode, tName, tPrice);
			help.createEntry(bBarcode, bName, bPrice);
			help.close();
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

	

}
