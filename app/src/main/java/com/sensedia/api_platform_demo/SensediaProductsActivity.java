package com.sensedia.api_platform_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SensediaProductsActivity extends AppCompatActivity {
    ListView lvSensediaProducts;

    ArrayAdapter<String> adapter;
    List<String> sensediaProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensedia_products);

        lvSensediaProducts = (ListView) findViewById(R.id.lvSensediaProducts);

        fillSensediaProductsListView();
    }

    private void fillSensediaProductsListView() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apiplatform.sensedia.com/dev/sensedia-products/1.0/sensedia-products";

        sensediaProducts = new ArrayList<String>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray products = null;
                    try {
                        products = response.getJSONArray("sensedia's products");
                        for (int i=0; i<products.length(); i++) {
                            JSONObject product = products.getJSONObject(i);
                            //sensediaProducts.add(product.getString("name")); // PROBLEM HERE
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(SensediaProductsActivity.this, sensediaProducts.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ProductsActivity","onErrorResponse: " + error.getMessage());
                    Toast.makeText(SensediaProductsActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                }
            }
        );

        queue.add(request);
        
        sensediaProducts.add("API Management");
        sensediaProducts.add("Adaptive Governance");
        sensediaProducts.add("Events Hub");
        sensediaProducts.add("Connectors");
        sensediaProducts.add("Flexible Actions");
        sensediaProducts.add("Service Mesh");

//        Toast.makeText(SensediaProductsActivity.this, sensediaProducts.toString(), Toast.LENGTH_SHORT).show();

        adapter = new ArrayAdapter<String>(SensediaProductsActivity.this, android.R.layout.simple_list_item_1, sensediaProducts);
        lvSensediaProducts.setAdapter(adapter);

        lvSensediaProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductInformation.class);
                intent.putExtra("product", sensediaProducts.get(position));
                startActivity(intent);
            }
        });
    }
}