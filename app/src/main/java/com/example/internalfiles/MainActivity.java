package com.example.internalfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    Button btnSave,btnReset,btnExit;
    TextView tV;
    EditText eT;
    final String FILE_NAME ="test.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String FILE_NAME ="test.txt";

        tV=findViewById(R.id.tV);
        eT=findViewById(R.id.eT);
        load();
    }

    public void load(){

        FileInputStream fis=null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            StringBuilder sb= new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            tV.setText(sb.toString());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public void save(View view) {

        String text = eT.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(text + "\n");
            eT.getText().clear();
            Toast.makeText(this, "Text saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            bw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load();
    }

    public void reset(View view) {
        final String FILE_NAME ="test.txt";
        tV.setText("");
        String text="";
        FileOutputStream fos=null;
        try {
            fos=openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            eT.getText().clear();
            Toast.makeText(this, "reset complete", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void exit(View view) {
        save(view);
        this.finishAffinity();
    }
}
