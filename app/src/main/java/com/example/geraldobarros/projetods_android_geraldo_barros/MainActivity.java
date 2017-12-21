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

        final List<Loja> listaInfoLojas = new ArrayList<Loja>();

        try{

            // Receber informações do JSON
            JSONArray lojaArray = receberDadosJson(url);

            JSONObject lojaObj;
            JSONObject jsonObjectIntrn;

            final List<Map<String, String>> data = new ArrayList<>();

            // for para cada intem do array recebido via JSON
            for (int i = 0; i < lojaArray.length(); i++) {
                lojaObj = new JSONObject(lojaArray.getString(i));

                jsonObjectIntrn = lojaArray.getJSONObject(i);
                JSONObject jsonSubObjectIntrn = jsonObjectIntrn.getJSONObject("endereco");

                // Receber informações
                Loja receberInfoObjLona = new Loja();
                receberInfoObjLona.id = lojaObj.getString("id");
                receberInfoObjLona.nome = lojaObj.getString("nome");
                receberInfoObjLona.telefone = lojaObj.getString("telefone");
                receberInfoObjLona.endComplemento = jsonSubObjectIntrn.getString("complemento");
                receberInfoObjLona.endBairro = jsonSubObjectIntrn.getString("bairro");
                receberInfoObjLona.endNumero = jsonSubObjectIntrn.getString("numero");
                receberInfoObjLona.endLogradouro = jsonSubObjectIntrn.getString("logradouro");

                // Map para informações a serem inseridas no ListView
                Map<String, String> map = new HashMap<>(2);
                map.put("ID", lojaObj.getString("id"));
                map.put("Nome", lojaObj.getString("nome"));
                data.add(map);

                listaInfoLojas.add(receberInfoObjLona);
            }

            // Adaptar as informações ao ListView
            final SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                    new String[]{"ID","Nome"}, new int[]{android.R.id.text1, android.R.id.text2});

            ListView lista = (ListView) findViewById(R.id.listView);
            lista.setAdapter(adapter);

            // Adicionar evento de click ao ListView apresentando as descrições das lojas selecionadas
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // Informações enviadas para Activity Descricao
                    Intent intent = new Intent(MainActivity.this, Descricao.class);
                    intent.putExtra(idLoja, listaInfoLojas.get(position).getId());
                    intent.putExtra(nomeLoja, listaInfoLojas.get(position).getNome());
                    intent.putExtra(telefoneLoja, listaInfoLojas.get(position).getTelefone());
                    intent.putExtra(complementoDescrpt, listaInfoLojas.get(position).getEndComplemento());
                    intent.putExtra(bairroDescrpt, listaInfoLojas.get(position).getEndBairro());
                    intent.putExtra(numeroDescrpt, listaInfoLojas.get(position).getEndNumero());
                    intent.putExtra(logradouroDescrpt, listaInfoLojas.get(position).getEndLogradouro());
                    startActivity(intent);
                }
            });
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }


    }


    public JSONArray receberDadosJson(String url) throws IOException, JSONException {

        JSONArray jsonArray;
        String resultado;

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse resposta = httpClient.execute(new HttpGet(url));
        BufferedReader reader = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent(), "UTF-8"));

        resultado = reader.readLine();
        JSONObject jsonObject = new JSONObject(resultado);

        jsonArray = jsonObject.getJSONArray("lojas");

        return jsonArray;

    }

    public void msgToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}




