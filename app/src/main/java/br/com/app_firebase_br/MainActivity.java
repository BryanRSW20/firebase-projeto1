package br.com.app_firebase_br;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ImageView bomba;
    private EditText inpTexto;
    private Button BtnSave;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Texto texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inpTexto = findViewById(R.id.inpTexto);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Texto");

        texto = new Texto();

        BtnSave = findViewById(R.id.BtnSave);

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conteudo = inpTexto.getText().toString();

                if(TextUtils.isEmpty(conteudo)){
                    Toast.makeText(MainActivity.this, "Adicione algum texto",Toast.LENGTH_SHORT).show();
                }
                else{
                    addDatatoFirebase(conteudo);
                }
            }
        });
    }

    private void addDatatoFirebase(String conteudo){
        texto.setConteudo(conteudo);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(texto);

                Toast.makeText(getBaseContext(), "Texto adicionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), "O envio do texto falhou :(" + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clickBomba(){
        bomba = findViewById(R.id.bomba);

        bomba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Cabum!", Toast.LENGTH_LONG).show();
            }
        });
    }
}