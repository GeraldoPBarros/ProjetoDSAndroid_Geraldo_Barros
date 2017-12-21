package com.example.geraldobarros.projetods_android_geraldo_barros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String idLoja = "com.example.myfirstapp.MESSAGEID1";
    public static final String nomeLoja = "com.example.myfirstapp.MESSAGEID2";
    public static final String telefoneLoja = "com.example.myfirstapp.MESSAGEID3";
    public static final String complementoDescrpt = "com.example.myfirstapp.MESSAGE1";
    public static final String bairroDescrpt = "com.example.myfirstapp.MESSAGE2";
    public static final String numeroDescrpt = "com.example.myfirstapp.MESSAGE3";
    public static final String logradouroDescrpt = "com.example.myfirstapp.MESSAGE4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = "https://api.myjson.com/bins/hvcbf";

        final List<Trend> listaTrends = new ArrayList<Trend>();

        try{
            String result = "";

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(url));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            result = reader.readLine();
            JSONObject jsonObject = new JSONObject(result);
            String receiveObj = jsonObject.getString("lojas");

            JSONArray trendsArray = jsonObject.getJSONArray("lojas");

            JSONObject trend;
            JSONObject jsonObjectIntrn;
            final List<Map<String, String>> data = new ArrayList<>();

            for (int i = 0; i < trendsArray.length(); i++) {
                trend = new JSONObject(trendsArray.getString(i));

                jsonObjectIntrn = trendsArray.getJSONObject(i);
                JSONObject jsonSubObjectIntrn = jsonObjectIntrn.getJSONObject("endereco");

                Trend objetoTrend = new Trend();
                Map<String, String> map = new HashMap<>(2);

                objetoTrend.id = trend.getString("id");
                objetoTrend.nome = trend.getString("nome");
                objetoTrend.telefone = trend.getString("telefone");
                objetoTrend.endComplemento = jsonSubObjectIntrn.getString("complemento");
                objetoTrend.endBairro = jsonSubObjectIntrn.getString("bairro");
                objetoTrend.endNumero = jsonSubObjectIntrn.getString("numero");
                objetoTrend.endLogradouro = jsonSubObjectIntrn.getString("logradouro");

                map.put("ID", trend.getString("id"));
                map.put("Nome", trend.getString("nome"));
                data.add(map);

                listaTrends.add(objetoTrend);
            }


            final SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                    new String[]{"ID","Nome"}, new int[]{android.R.id.text1, android.R.id.text2});

            ListView lista = (ListView) findViewById(R.id.listView);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Intent intent = new Intent(MainActivity.this, Description.class);
                    intent.putExtra(idLoja, listaTrends.get(position).getId());
                    intent.putExtra(nomeLoja, listaTrends.get(position).getNome());
                    intent.putExtra(telefoneLoja, listaTrends.get(position).getTelefone());
                    intent.putExtra(complementoDescrpt, listaTrends.get(position).getEndComplemento());
                    intent.putExtra(bairroDescrpt, listaTrends.get(position).getEndBairro());
                    intent.putExtra(numeroDescrpt, listaTrends.get(position).getEndNumero());
                    intent.putExtra(logradouroDescrpt, listaTrends.get(position).getEndLogradouro());
                    startActivity(intent);

                }

            });



        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public void msgToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}




