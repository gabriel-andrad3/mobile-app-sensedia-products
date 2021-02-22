package com.sensedia.api_platform_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductInformation extends AppCompatActivity {
    TextView txtProduct, txtFocus, txtYear, txtFeatures;
    ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        txtProduct = findViewById(R.id.txtProduct);
        txtFocus = findViewById(R.id.txtFocus);
        txtYear = findViewById(R.id.txtLabelYear);
        txtFeatures = findViewById(R.id.txtFeatures);
        imgProduct = findViewById(R.id.imgProduct);

        Intent intent = getIntent();
        String product = intent.getStringExtra("product");
        txtProduct.setText(product);

        setProductImage(product);
    }

    private void setProductImage(String product) {
        switch (product) { // refactor this switch - not following SOLID: O
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