package com.media9.diary;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Intro extends Activity implements OnClickListener {

	Typeface font;
	TextView text;
	TextView text2;
	ImageView diario;
	ImageButton facebook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		text = (TextView) findViewById(R.id.textView1);
		font = Typeface.createFromAsset(getAssets(),"VIJAYA.TTF");
		text.setTypeface(font);
		text2 = (TextView) findViewById(R.id.keep);
		text2.setTypeface(font);
		diario = (ImageView) findViewById(R.id.imageButton3);
		diario.setOnClickListener(this);
		facebook = (ImageButton) findViewById(R.id.facebook);
		facebook.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==diario){
			Intent main;
			main = new Intent (this, MainActivity.class);
			startActivity(main);
			
		}
		
	
		if(v==facebook){
			Log.e("ma il problema sta qui","???");
			loginFacebook();
		}
		
	
		
		
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

	}
	
	
	
	
	
	
	
	
	
	public boolean loginFacebook() {
		boolean login = true;

		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				Log.e("", "11111111111111111111111111");
				if (session.isOpened()) {
					Log.e("", "2222222222222222222222222222");
					// make request to the /me API
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response
								// with
								// user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {

									Log.e("", "3333333333333333333333333333");
									Toast.makeText(
											getApplication(),"Ciao"
													+ " "
													+ user.getName().toString()+"!\nAdesso puoi condividere le note con i tuoi amici!",
											Toast.LENGTH_LONG).show();
								}
							});
					Log.e("", "4444444444444444444444444");
				}
				Log.e("", "55555555555555555555555555");
			}

		});

		return login;
	}

	
	
	

	


}