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

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfProducts extends AppCompatActivity {
    ListView lvSensediaProducts;

    ArrayAdapter<String> adapter;
    List<String> sensediaProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);

        lvSensediaProducts = (ListView) findViewById(R.id.lvSensediaProducts);

        fillSensediaProductsListView();
    }

    private void fillSensediaProductsListView() {
        String url ="https://apiplatform.sensedia.com/dev/sensedia-products/1.0/sensedia-products";

        sensediaProducts = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray products;
                    try {
                        products = response.getJSONArray("sensedia's products");
                        for (int i=0; i<products.length(); i++) {
                            JSONObject product = products.getJSONObject(i);
                            sensediaProducts.add(product.getString("name")); // PROBLEM HERE
                        }
                        adapter = new ArrayAdapter<>(ListOfProducts.this, android.R.layout.simple_list_item_1, sensediaProducts);
                        lvSensediaProducts.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ProductsActivity","onErrorResponse: " + error.getMessage());
                    Toast.makeText(ListOfProducts.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    headers.put("client-id", "99982fb2-156a-3bc1-bd7c-0105613cebdf");
                    return headers;
                }
        };

        queue.add(request);

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