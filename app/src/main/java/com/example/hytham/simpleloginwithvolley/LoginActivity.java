package com.example.hytham.simpleloginwithvolley;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {
    EditText edit_username , edit_password;
    Button button_login;
    TextView text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password =(EditText) findViewById(R.id.edit_password);
        text_message = (TextView) findViewById(R.id.text_message);

        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJsonObjectRequest("http://10.0.3.2:80/dashboard/stimulate.php");
                text_message.setText("");

            }
        });
    }
    void postJsonObjectRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject parms = new JSONObject();

        try {
            parms.put("username" , edit_username.getText().toString());
            parms.put("password" , edit_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, parms, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String success = response.getString("success");
                    String message = response.getString("message");

                    if (success == "0"){
                        text_message.setText(message);
                    } else {
                        Toast.makeText(getApplicationContext() , message , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text_message.setText("Error:" + error.toString());

            }
        });
        queue.add(objectRequest);
    }
    void getJsonObjectRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                text_message.setText("Response: " + response.toString());

                try {
                    String success = response.getString("success");
                    String message = response.getString("message");

                    if (success == "0"){
                        text_message.setText(message);
                    } else {
                        Toast.makeText(getApplicationContext() , message , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text_message.setText("Error:" + error.toString());

            }
        });
        queue.add(objectRequest);
    }
    void postStringRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                text_message.setText("Response is: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text_message.setText("That did not work !");

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>() ;
                params.put("username" , edit_username.getText().toString());
                params.put("password" , edit_password.getText().toString());
                return params;

            }
        };
        //add the request to the requestqueue
        queue.add(stringRequest);

    }
    void getStringRequest(String url){
         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
          StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text_message.setText("Response is: " + response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        text_message.setText("That did not work !");

                    }
                });
        //add the request to the requestqueue
         queue.add(stringRequest);

    }
}
