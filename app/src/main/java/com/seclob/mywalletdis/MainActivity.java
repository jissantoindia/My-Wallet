package com.seclob.mywalletdis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout sideNavOverLay;
    ScrollView sideNav;
    Animation pop;
    SharedPreferences sharedPreferences;
    TextView mCount,dCount,kCount;

    List<RecentMobileRechargeModel> mobileRechargeModels = new ArrayList<>(1000);
    private RecentMobileRechargeAdaptor recentMobileRechargeAdaptor;
    RecyclerView mobileRechargeRecyclerView;

    List<RecentDthRechargeModel> mobileDthModels = new ArrayList<>(1000);
    private RecentDthRechargeAdaptor recentDthRechargeAdaptor;
    RecyclerView mobileDthRecyclerView;

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            HomeApi(sharedPreferences.getString("username",""),sharedPreferences.getString("password",""));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("MYSCBCL", MODE_PRIVATE);
        sideNavOverLay = findViewById(R.id.sideNavOverLay);
        sideNav = findViewById(R.id.sideNav);
        registerReceiver(receiver,new IntentFilter("com.push.message.received"));
        pop = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);

        mCount = findViewById(R.id.mCount);
        dCount = findViewById(R.id.dCount);
        kCount = findViewById(R.id.kCount);

        mobileRechargeRecyclerView = findViewById(R.id.mobileRechargeRecyclerView);
        recentMobileRechargeAdaptor = new RecentMobileRechargeAdaptor(getApplication());
        mobileRechargeRecyclerView.setAdapter(recentMobileRechargeAdaptor);
        mobileRechargeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mobileRechargeRecyclerView.setNestedScrollingEnabled(false);

        mobileDthRecyclerView = findViewById(R.id.mobileDthRecyclerView);
        recentDthRechargeAdaptor = new RecentDthRechargeAdaptor(getApplication());
        mobileDthRecyclerView.setAdapter(recentDthRechargeAdaptor);
        mobileDthRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mobileDthRecyclerView.setNestedScrollingEnabled(false);

        HomeApi(sharedPreferences.getString("username",""),sharedPreferences.getString("password",""));


    }

    @Override
    protected void onResume() {
        HomeApi(sharedPreferences.getString("username",""),sharedPreferences.getString("password",""));
        super.onResume();
    }

    public void goToMobileRecharge(View view){
        Intent i= new Intent(MainActivity.this,MobilerechargesActivity.class);
        startActivity(i);
    }
    public void goToDthRecharge(View view){
        Intent i= new Intent(MainActivity.this,DthActivity.class);
        startActivity(i);
    }
    public void goToStatement(View view){
        Intent i= new Intent(MainActivity.this, StatementActivity.class);
        startActivity(i);
    }
    public void goToKSEBRecharge(View view){
        Intent i= new Intent(MainActivity.this,KsebActivity.class);
        startActivity(i);
    }
    public void goToWallet(View view){
        Intent i=new Intent(MainActivity.this,WalletActivity.class);
        startActivity(i);
    }
    public void goToViewClientEr(View view) {
        Intent i = new Intent(MainActivity.this, ViewActivity.class);
        startActivity(i);
        SideNavOpen(false);

    }
    public void goToAddClient(View view) {
        Intent i = new Intent(MainActivity.this, AddclientActivity.class);
        startActivity(i);
        SideNavOpen(false);

    }
    public void OpenSideBar(View view){
        SideNavOpen(true);
    }

    public void CloseSideBar(View view){
        SideNavOpen(false);
    }

    void SideNavOpen(boolean isOpen)
    {
        if(isOpen)
        {   sideNavOverLay.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ltor);
            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
            sideNavOverLay.startAnimation(animation2);
            sideNav.startAnimation(animation);
        }else{
            Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotl);
            Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
            sideNavOverLay.startAnimation(animation4);
            sideNav.startAnimation(animation3);
            sideNavOverLay.setVisibility(View.GONE);
        }
    }

    public void DthRecharge(View view)
    {

        Intent i= new Intent(MainActivity.this,DthActivity.class);
        startActivity(i);
    }

    public void goToCMobile(View view)
    {
        SideNavOpen(false);
        Intent i= new Intent(MainActivity.this,TrmActivity.class);
        startActivity(i);
    }

    public void goToCDth(View view)
    {
        SideNavOpen(false);
        Intent i= new Intent(MainActivity.this,TrdActivity.class);
        startActivity(i);
    }

    public void goToMR(View view)
    {
        SideNavOpen(false);
        Intent i= new Intent(MainActivity.this,MobilerechargesActivity.class);
        startActivity(i);
    }

    public void goToDR(View view)
    {
        SideNavOpen(false);
        Intent i= new Intent(MainActivity.this,DthActivity.class);
        startActivity(i);
    }

    public void MobileRecharge(View view)
    {
        Intent i= new Intent(MainActivity.this,MobilerechargesActivity.class);
        startActivity(i);
    }


    public void HomeApi(final String username, final String password)
    {
        Loader(true);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = getString(R.string.api_url)+"login";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        Log.i("VOLLEYES", response);

                        try {
                            Loader(false);
                            JSONObject Res=new JSONObject(response);
                            String sts    = Res.getString("sts");
                            String msg    = Res.getString("msg");

                            if(sts.equalsIgnoreCase("01"))
                            {
                                mCount.setText(Res.getString("mblcntpend"));
                                dCount.setText(Res.getString("dthcntpend"));
                                kCount.setText(Res.getString("elecntpend"));

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("balance", Res.getString("balance"));
                                editor.apply();

                                String data = Res.getString("mblpendlist");
                                JSONArray Results = new JSONArray(data);
                                mobileRechargeModels.clear();
                                int len = (Results.length()>5)?5:Results.length();
                                for (int i = 0; i < len; i++) {
                                    String Result = Results.getString(i);
                                    JSONObject rst = new JSONObject(Result);
                                    RecentMobileRechargeModel mobileRechargeModel = new RecentMobileRechargeModel();
                                    mobileRechargeModel.setMobile(rst.getString("mreach_number"));
                                    mobileRechargeModel.setProvider(rst.getString("mreach_operator")+" - "+rst.getString("mreach_circle"));
                                    mobileRechargeModel.setDis("("+rst.getString("mreach_type")+" Mobile Recharge) "+rst.getString("mreach_amount")+" on " + rst.getString("mreach_date"));
                                    mobileRechargeModel.setStatus(rst.getString("mreach_status"));
                                    mobileRechargeModel.setIdRecharge(rst.getString("mreach_id"));
                                    mobileRechargeModels.add(mobileRechargeModel);
                                }
                                recentMobileRechargeAdaptor.renewItems(mobileRechargeModels);

                                String data1 = Res.getString("dthpendlist");
                                JSONArray Results1 = new JSONArray(data1);
                                mobileDthModels.clear();
                                int len1 = (Results1.length()>5)?5:Results1.length();
                                for (int i = 0; i < len1; i++) {
                                    String Result = Results1.getString(i);
                                    JSONObject rst = new JSONObject(Result);
                                    RecentDthRechargeModel recentDthRechargeModel = new RecentDthRechargeModel();
                                    recentDthRechargeModel.setMobile(rst.getString("dth_customerid"));
                                    recentDthRechargeModel.setProvider(rst.getString("dth_operator"));
                                    recentDthRechargeModel.setDis(rst.getString("dth_paidamount")+" on " + rst.getString("dth_date"));
                                    recentDthRechargeModel.setStatus(rst.getString("dth_status"));
                                    recentDthRechargeModel.setIdRecharge(rst.getString("dth_id"));
                                    mobileDthModels.add(recentDthRechargeModel);
                                }
                                recentDthRechargeAdaptor.renewItems(mobileDthModels);

                            }
                            else
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            Log.e("catcherror",e+"d");

                            Toast.makeText(getApplicationContext(), "Catch Error :"+e, Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                            Loader(false);
                            Toast.makeText(getApplicationContext(), "Network Error :"+errorString, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);

                Log.i("loginp ", params.toString());

                return params;
            }

        };


        // Add the realibility on the connection.
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        // Start the request immediately
        queue.add(request);

    }

    void Loader(Boolean status)
    {
        LinearLayout loader = findViewById(R.id.loader_Main);
        if(status)
            loader.setVisibility(View.VISIBLE);
        else
            loader.setVisibility(View.GONE);
    }


}