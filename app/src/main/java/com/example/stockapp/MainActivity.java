package com.example.stockapp;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

    public class MainActivity extends AppCompatActivity {
        Button stockPriceButton;
        TextView textView;
        EditText userText;
        static String stockName;
        TextView resultTextView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //textView = (textView) findViewById(R.id.StockNameInput);
            userText = (EditText) findViewById(R.id.StockNameText);
            stockPriceButton = (Button) findViewById(R.id.getPriceButton);
            resultTextView = (TextView) findViewById(R.id.responsTextView);


            stockPriceButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View v) {

                    stockName = (String) userText.getText().toString();
                    Log.i("Main Activity Got Stock Name", stockName);
                /*
                log.i("Main Activity Got Stock Name", stockName);
                resultTextView.setText(stockName);
                */
                    fetchStockPrice(v);
                }
            });

        }



        public void fetchStockPrice(final View view) {
            final StockPriceFetcher fetcher = new StockPriceFetcher(view.getContext());

            //Loading Box
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Fetching Stock...");
            progressDialog.show();

            Log.i("Main - fetcher ", stockName);
            fetcher.dispatchRequest(new StockPriceFetcher.StockPriceResponseListener() {
                @Override
                public void onResponse(StockPriceFetcher.PriceResponse response) {
                    progressDialog.hide();

                    if (response.isError) {
                        Log.i("main", "in response.is_err");
                        Toast.makeText(view.getContext(), "Error while fetching Stock", Toast.LENGTH_LONG);
                        return;
                    }
                    resultTextView.setText(response.price);

                }
            });
        }
    }