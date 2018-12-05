package com.example.learningmangament;

import java.util.HashMap;

import org.json.JSONObject;

import com.example.learningmangament.utils.Constants;
import com.example.learningmangament.utils.Helper;
import com.example.learningmangament.utils.UrlUtilAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends Activity {

	public static final String PREFS_NAME="LoginPrefs";
    SharedPreferences setting;
    SharedPreferences.Editor editor;

	LinearLayout linear1,linear2,linear3,linear4,linear5;
	TextView txt_username,txtt_password,txt_pwd,txt_forgot,txt_help;
	EditText edit_username,edit_password;
	Button btn_login,btn_cancel;
	int screen_width,screen_height;
	
	  public String Username,mUsername;
	    public String Password;
	    private String mId;
	    private String mName;

	    boolean cancle=false;
	    View focusView=null;

	    String TAG="test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		 	setting=getSharedPreferences(PREFS_NAME, 0);
	        editor=setting.edit();

	        findAllViews();
//	        
//	        
	        Display display=getWindowManager().getDefaultDisplay();
	        Point size=new Point();
	        display.getSize(size);
	        screen_width=size.x;
	        screen_height=size.y;
//	        
//	        
//	        
//	        //////height and width of txt_username/////////
//	        //////size of txt_username/////////
	        txt_username.setTextSize(28);
//	        
	        int txt_username_w=screen_width*200/480;
	        int txt_username_h=screen_height*100/752;
	        txt_username.getLayoutParams().width=txt_username_w;
	        txt_username.getLayoutParams().height=txt_username_h;
	        txt_username.requestLayout(); 
//	        
//	        //// h & w edit_username////
	        edit_username.setTextSize(26);
	        int edit_username_w=screen_width*200/480;
	        int edit_username_h=screen_height*70/752;
	        edit_username.getLayoutParams().width=edit_username_w;
	        edit_username.getLayoutParams().height=edit_username_h;
	        edit_username.requestLayout();
//	        
//	        //// h & w txt_pwd  ///
	        txt_pwd.setTextSize(28);
//
	        int txt_pwd_w=screen_width*200/480;
	        int txt_pwd_h=screen_height*100/752;
	        txt_pwd.getLayoutParams().width=txt_pwd_w;
	        txt_pwd.getLayoutParams().height=txt_pwd_h;
	        txt_pwd.requestLayout();
//	        
	        //h & W edit_password///
	        edit_password.setTextSize(26);

	        int edit_password_w=screen_width*200/480;
	        int edit_password_h=screen_height*70/752;
	        edit_password.getLayoutParams().width=edit_password_w;
	        edit_password.getLayoutParams().height=edit_password_h;
	        edit_password.requestLayout();
	        
	        ///h & w btn_login ///
	        int btn_login_w=screen_width*180/480;
	        int btn_login_h=screen_height*82/752;
	        btn_login.getLayoutParams().width=btn_login_w;
	        btn_login.getLayoutParams().height=btn_login_h;
	        btn_login.requestLayout();
	        
	        /// h & w btn_cancel //
	        int btn_cancel_w=screen_width*180/480;
	        int btn_cancel_h=screen_height*84/752;
	        btn_cancel.getLayoutParams().width=btn_cancel_w;
	        btn_cancel.getLayoutParams().height=btn_cancel_h;
	        btn_cancel.requestLayout();

	        txt_help.setTextSize(20);
	        
	       btn_login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				      Username = edit_username.getText().toString();
			          Password = edit_password.getText().toString();

			            if (!isConnected()) {
			                ShowToast("Please check internet Connection");
			            }
			            else{
			                validate();
			                if (cancle) {
			                    return;
			                }
			                LoginMethod();
				               }

				}
			});
	        
	 btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					btn_cancel.setBackgroundResource(R.drawable.btn_cancel_rollover);
					finish();
				}
			});
	 
	 txt_help.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 Intent intent = new Intent(getApplicationContext(), ResistrationActivity.class);
                 startActivity(intent);
                //finish();
			}
		});
	        
	    }
	    public void findAllViews()
	    {
	    	linear1=(LinearLayout)findViewById(R.id.linear1);
	    	linear2=(LinearLayout)findViewById(R.id.linear2);
	    	linear3=(LinearLayout)findViewById(R.id.linear3);
	    	linear4=(LinearLayout)findViewById(R.id.linear4);
	    	linear5=(LinearLayout)findViewById(R.id.linear5);
	    	txt_username=(TextView)findViewById(R.id.txt_username);
	    	txt_pwd=(TextView)findViewById(R.id.txt_pwd);
	    	txt_help=(TextView)findViewById(R.id.txt_help);
	    	edit_username=(EditText)findViewById(R.id.edit_username);
	    	edit_password=(EditText)findViewById(R.id.edit_password);
	    	btn_login=(Button)findViewById(R.id.btn_login);
	    	btn_cancel=(Button)findViewById(R.id.btn_cancel);
	
	    	
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	
		  public void validate() {
		        cancle= false;

		        if(edit_username.getText().toString().trim().equals("")){
		        	edit_username.setError("enter data");
		            cancle=true;
		        }else {
		        	edit_username.setError(null);
		        }

		        if(edit_password.getText().toString().trim().equals("")){
		        	edit_password.setError("enter data");
		            cancle=true;
		        }else {
		        	edit_password.setError(null);
		        }
		    }
		  
		  public void ShowToast(String msg) {
		        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		    }
		  
		  public boolean isConnected() {
		        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		        if(networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected()){
		            return true;
		        }else {
		            return false;
		        }
		    }
		
		
		public void LoginMethod(){

	        HashMap<String, String> val = new HashMap<String, String>();
	        val.put("reqid", "login");
	        val.put("username",Username);
	        val.put("password",Password);
	        val.put("packageName", getApplicationContext().getPackageName());
	        
	        Log.v(TAG, ":" + Username + ":" + Password + ":" );


	        final ProgressDialog pdDialog = new ProgressDialog(LogActivity.this);
	        pdDialog.setCancelable(true);
	        pdDialog.setMessage("Please wait...");
	        pdDialog.show();

	        UrlUtilAsyncTask sendData = new UrlUtilAsyncTask(val, Constants.POST) {

	            @Override
	            public void onPostExecute(String result) {
	                try {
	                    Log.v("testRSE","res="+result);
	                    if (pdDialog != null && pdDialog.isShowing()) {
	                        pdDialog.dismiss();
	                    }
	                 
	                    if (!result.equals("null")){
	                        JSONObject json=new JSONObject(result);
	                        Log.v(TAG,"result="+result);
	                        if(json.getString("response").equalsIgnoreCase("success"))
	                        {
	                            
	                            Log.v(TAG, "In If");
	                            Log.v(TAG, ":" + json.getString("name") + ":" + json.getString("empid") + ":" + json.getString("role"));

	                                //editor.putString("logged", "logged");
	                                editor.putString("name", json.getString("name"));
	                                editor.putString("email", json.getString("email"));
	                                editor.putString("empid", json.getString("empid"));
	                                editor.putString("role", json.getString("role"));
	                                editor.commit();

	                                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
	                                startActivity(intent);
	                                finish();
	                      }else{
	                            //Log.v(TAG, "in else");
	                            Helper.showAlert1("You are not valid user to use this application...!", LogActivity.this);
	                        }
	                    }
	                }catch(Exception e)
	                {
	                    e.printStackTrace();
	                }
	                super.onPostExecute(result);
	            }
	        };sendData.execute(Constants.HOST_NAME);
	    }
	}
