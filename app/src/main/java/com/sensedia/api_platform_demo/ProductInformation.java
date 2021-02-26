package com.sensedia.api_platform_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductInformation extends AppCompatActivity {
    TextView txtProduct, txtGoal, txtYearOfLaunch, txtFeatures;
    ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        txtProduct = findViewById(R.id.txtProduct);
        txtGoal = findViewById(R.id.txtGoal);
        txtYearOfLaunch = findViewById(R.id.txtYearOfLaunch);
        txtFeatures = findViewById(R.id.txtFeatures);
        imgProduct = findViewById(R.id.imgProduct);

        Intent intent = getIntent();
        String product = intent.getStringExtra("product");
        txtProduct.setText(product);

        setProductImage(product);

        String operation = getOperation(product);
        Toast.makeText(ProductInformation.this, operation, Toast.LENGTH_SHORT).show();
        setProductInformation(operation);
    }

    private void setProductInformation(String operation) {
        String url ="https://apiplatform.sensedia.com/dev/sensedia-products/1.0/sensedia-products/" + operation;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        txtGoal.setText(response.getString("goal"));
                        txtYearOfLaunch.setText(response.getString("year of launch"));
                        txtFeatures.setText(response.getString("features"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ProductsActivity","onErrorResponse: " + error.getMessage());
                    Toast.makeText(ProductInformation.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        );

        queue.add(request);
    }

    private String getOperation(String product) {
        product = product.toLowerCase();
        String operation = product;

        for(int i = 0; i < product.length(); i++){
            if(Character.isWhitespace(product.charAt(i))){
                operation = product.substring(0,i) + "-" + product.substring(i+1);
                break;
            }
        }
        return operation;
    }

    private void setProductImage(String product) {
        switch (product) { // switch is not best solution (not following S'O'LID) but necessary because setImageResource expects a Drawable
            case "API Platform":
                imgProduct.setImageResource(R.drawable.apip2);
                break;

            case "Adaptive Governance":
                imgProduct.setImageResource(R.drawable.ag);
                break;

            case "Events Hub":
                imgProduct.setImageResource(R.drawable.eh);
                break;

            case "Connectors":
                imgProduct.setImageResource(R.drawable.conn);
                break;
            case "Flexible Actions":
                imgProduct.setImageResource(R.drawable.fa);
                break;
            case "Service Mesh":
                imgProduct.setImageResource(R.drawable.mesh);
                break;
        }
    }
}