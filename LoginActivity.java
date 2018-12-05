package com.example.learningmangament;

import java.util.HashMap;

import org.json.JSONObject;

import com.example.learningmangament.utils.Helper;
import com.example.learningmangament.utils.UrlUtilAsyncTask;
import com.example.learningmangament.utils.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	public static final String PREFS_NAME="LoginPrefs";
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    private EditText etEmailId, etPassword;
    private Button btnLogin,btnResistration;

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
		setContentView(R.layout.activity_login);
		
		 	setting=getSharedPreferences(PREFS_NAME, 0);
	        editor=setting.edit();
		
		 	etEmailId = (EditText) findViewById(R.id.et_username);
	        etPassword = (EditText) findViewById(R.id.et_password);
	        btnLogin = (Button)findViewById(R.id.btn_login);
	        btnResistration= (Button)findViewById(R.id.btn_resisterL);

	        etEmailId.setText(mUsername);

	        btnLogin.setOnClickListener(this);
	        btnResistration.setOnClickListener(this);
	}
	
	  public void validate() {
	        cancle= false;

	        if(etEmailId.getText().toString().trim().equals("")){
	            etEmailId.setError("enter data");
	            cancle=true;
	        }else {
	            etEmailId.setError(null);
	        }

	        if(etPassword.getText().toString().trim().equals("")){
	            etPassword.setError("enter data");
	            cancle=true;
	        }else {
	            etPassword.setError(null);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

        case R.id.btn_login:

            Username = etEmailId.getText().toString();
            Password = etPassword.getText().toString();

            if (!isConnected()) {
                ShowToast("Please check internet Connection");
            }
            else{
                validate();
                if (cancle) {
                    return;
                }
                LoginMethod();

//                editor.putString("mUsername", Username);
//                editor.commit();

//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();

            }

            break;
            
        case R.id.btn_resisterL:
        	Intent intent = new Intent(getApplicationContext(), ResistrationActivity.class);
        	startActivity(intent);
        	finish();
        	
        	break;
    }
	}
	
	public void LoginMethod(){

        HashMap<String, String> val = new HashMap<String, String>();
        val.put("reqid", "login");
        val.put("username",Username);
        val.put("password",Password);
        val.put("packageName", getApplicationContext().getPackageName());
        
        Log.v(TAG, ":" + Username + ":" + Password + ":" );


        final ProgressDialog pdDialog = new ProgressDialog(LoginActivity.this);
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
                                editor.putString("empid", json.getString("empid"));
                                editor.putString("role", json.getString("role"));
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                      }else{
                            //Log.v(TAG, "in else");
                            Helper.showAlert1("You are not valid user to use this application...!", LoginActivity.this);
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
