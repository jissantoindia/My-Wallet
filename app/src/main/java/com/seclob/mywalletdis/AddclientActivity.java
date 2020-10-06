package com.seclob.mywalletdis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddclientActivity extends AppCompatActivity {

    TextInputEditText clientName,shopName,userName,mobileNumber,address,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add client");

        clientName=findViewById(R.id.clientName);
        shopName=findViewById(R.id.shopName);
        userName=findViewById(R.id.userName);
        mobileNumber=findViewById(R.id.mobileNumber);
        address=findViewById(R.id.address);
        Password=findViewById(R.id.Password);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
    public void AddClient (View view) {

        if(clientName.length()>2 && shopName.length()>2 && userName.length()>2 && mobileNumber.length()>2 && address .length()>2 && Password.length()>2)
        {ChangeStatus();}else
        {
            Toast.makeText(this, "Some required fields are empty!", Toast.LENGTH_LONG).show();
        }

    }

    void Loader(Boolean status)
    {
        LinearLayout loader = findViewById(R.id.loader_add);
        if(status)
            loader.setVisibility(View.VISIBLE);
        else
            loader.setVisibility(View.GONE);
    }

    public void ChangeStatus()
    {
        Loader(true);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = getString(R.string.shopadd_api)+"shopadd";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        Log.i("VOLLEYES", response);
                        Loader(false);
                        try {
                            JSONObject Res=new JSONObject(response);
                            String sts    = Res.getString("sts");
                            String msg    = Res.getString("msg");

                            if(sts.equalsIgnoreCase("01"))
                            {

                                Toast.makeText(getApplicationContext(), "Client Added Successfully!", Toast.LENGTH_LONG).show();
                                finish();

                            }else
                            {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }

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

                params.put("username",userName.getText().toString());
                params.put("password",Password.getText().toString());
                params.put("shopname",shopName.getText().toString());
                params.put("ownername",clientName.getText().toString());
                params.put("address",address.getText().toString());
                params.put("mobile1",mobileNumber.getText().toString());
                params.put("mobile2",mobileNumber.getText().toString());
                params.put("shopimage",userName.getText().toString());

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

}