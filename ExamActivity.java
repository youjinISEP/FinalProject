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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExamActivity extends Activity implements View.OnClickListener {
	
	public static final String PREFS_NAME="LoginPrefs";
    SharedPreferences setting;
    SharedPreferences.Editor editor;
	
	TextView txtQuestion;
	Button btnSubminNnext;
	RadioButton rbA,rbB,rbC,rbD;
	RadioGroup radioGroup;
	
	TextView txtTitleQuestion;
	
	String mCourseId,mChapterId="",mQuesId="",mAns,mSelectedAns,mChapterName,mStudId;
	int mQuesCount=1;
	int mCorrectCount=0;

    String TAG="test",mLang;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);
		
		setting=getSharedPreferences(PREFS_NAME, 0);
        editor=setting.edit();
        
//        mName=setting.getString("name", "");
//        mRole=setting.getString("role","");
        mStudId=setting.getString("empid","");
        

        mLang=setting.getString("lang", "");
        Log.v(TAG, "mLang=" + mLang);
		
		txtQuestion=(TextView)findViewById(R.id.txt_question);
		btnSubminNnext=(Button)findViewById(R.id.btn_subNnext);		
		rbA=(RadioButton)findViewById(R.id.rb_A);
		rbB=(RadioButton)findViewById(R.id.rb_B);
		rbC=(RadioButton)findViewById(R.id.rb_C);
		rbD=(RadioButton)findViewById(R.id.rb_D);
		
		radioGroup=(RadioGroup)findViewById(R.id.rg_first);
		
		txtTitleQuestion=(TextView)findViewById(R.id.txt_title_question);
		
		if(mLang.equalsIgnoreCase("1")){
			txtTitleQuestion.setText(R.string.txt_question_eng);
		}else if(mLang.equalsIgnoreCase("2")){
			txtTitleQuestion.setText(R.string.txt_question_mar);
		}
		
		
		mChapterName=getIntent().getStringExtra("mChapterName");
		mChapterId=getIntent().getStringExtra("mChapterId");
		mCourseId=getIntent().getStringExtra("mCourseId");
		Log.v(TAG,"mChapterId="+mChapterId);
		Log.v(TAG,"mCourseId="+mCourseId);
		
		//GetFirstQuesMethod();	
		
		CheckExamMethod();
		
		btnSubminNnext.setOnClickListener(this);
		rbA.setOnClickListener(this);
		rbB.setOnClickListener(this);
		rbC.setOnClickListener(this);
		rbD.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exam, menu);
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
	
	public void CheckExamMethod(){

        HashMap<String, String> val = new HashMap<String, String>();
        val.put("reqid", "check_exam");
        val.put("mStudId",mStudId);
        val.put("mChapterId",mChapterId);
        val.put("mCourseId",mCourseId);
        //val.put("mQuesId","demo");
        val.put("packageName", getApplicationContext().getPackageName());
        
        final ProgressDialog pdDialog = new ProgressDialog(ExamActivity.this);
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
                        	 //Helper.showAlert1("Test Avilable for you...!", ExamActivity.this);
                        	GetFirstQuesMethod();                       	
                        	
                        	
                      }else if(json.getString("response").equalsIgnoreCase("fail")){
                            //Helper.showAlert1("Test Not Avilable for you...!", ExamActivity.this);
//                            Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
//                            startActivity(intent);
//                            finish();
                    	  
                    	  GetFirstQuesMethod();	
                        }else if(json.getString("response").equalsIgnoreCase("less_marks")){                                            	  
                        	//ShowToast("Please clear test for next test...!");
                        	Helper.showAlertOpenTest("Please clear test for next test...!", ExamActivity.this);
//                        	Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
//                          startActivity(intent);
//                          finish();
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
	
	public void GetFirstQuesMethod(){

        HashMap<String, String> val = new HashMap<String, String>();
        val.put("reqid", "get_first_ques");
        val.put("mChapterId",mChapterId);
        val.put("mCourseId",mCourseId);
        val.put("mQuesId","demo");
        val.put("packageName", getApplicationContext().getPackageName());
        
        //Log.v(TAG, ":" + mName + ":" + mEmail + ":" + mPassword + ":" + mDOB + ":" + mMobile + ":"+ mAddress + ":" + mGender + ":" + mCourse + ":" + mUserType + ":" );
        final ProgressDialog pdDialog = new ProgressDialog(ExamActivity.this);
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
                        	txtQuestion.setText(json.getString("mQuestion"));
                        	rbA.setText(json.getString("mA"));
                        	rbB.setText(json.getString("mB"));
                        	rbC.setText(json.getString("mC"));
                        	rbD.setText(json.getString("mD"));
                        	
                        	mAns=json.getString("mANS");
                        	mQuesId=json.getString("mId");
                      }else{
                            Helper.showAlert1("Test Not Avilable...!", ExamActivity.this);
                            Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
                            //intent.putExtra("mQuesId", mQuesId);
                            startActivity(intent);
                            finish();
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
	
	public void GetNextQuesMethod(){

        HashMap<String, String> val = new HashMap<String, String>();
        val.put("reqid", "get_first_ques");
        val.put("mChapterId",mChapterId);
        val.put("mCourseId",mCourseId);
        val.put("mQuesId",mQuesId);
        val.put("packageName", getApplicationContext().getPackageName());
        
        //Log.v(TAG, ":" + mName + ":" + mEmail + ":" + mPassword + ":" + mDOB + ":" + mMobile + ":"+ mAddress + ":" + mGender + ":" + mCourse + ":" + mUserType + ":" );
        final ProgressDialog pdDialog = new ProgressDialog(ExamActivity.this);
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
                        	mQuesCount++;
                        	Log.v(TAG,"mQuesCount: "+mQuesCount);
                        	txtQuestion.setText(json.getString("mQuestion"));
                        	rbA.setText(json.getString("mA"));
                        	rbB.setText(json.getString("mB"));
                        	rbC.setText(json.getString("mC"));
                        	rbD.setText(json.getString("mD"));
                        	
                        	mAns=json.getString("mANS");
                        	mQuesId=json.getString("mId");
                      }else{
                            Helper.showAlert1("Exam Completed....!", ExamActivity.this);
                            //ShowToast("Exam Completed....");
                            Intent intent = new Intent(getApplicationContext(), TestFinishActivity.class);
                            intent.putExtra("mQuesId", mQuesCount); 
                            intent.putExtra("mCorrectCount", mCorrectCount); 
                            intent.putExtra("mCourseId", mCourseId); 
                            intent.putExtra("mChapterId", mChapterId); 
                        	intent.putExtra("mChapterName", mChapterName);
                            Log.v(TAG,"correct ans:="+mCorrectCount);
                            startActivity(intent);
                            finish();
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
	
	  public void ShowToast(String msg) {
	        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		
		case R.id.btn_subNnext:
			if(mSelectedAns.equalsIgnoreCase(mAns)){
				mCorrectCount++;
				Log.v(TAG,"count:="+mCorrectCount);
			}
			radioGroup.clearCheck();
			GetNextQuesMethod();
			break;

		case R.id.rb_A:
			mSelectedAns="A";
			break;
			
		case R.id.rb_B:
			mSelectedAns="B";
			break;
			
		case R.id.rb_C:
			mSelectedAns="C";
			break;
			
		case R.id.rb_D:
			mSelectedAns="D";
			break;

	}
		
	}
}
