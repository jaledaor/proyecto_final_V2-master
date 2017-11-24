package todo_jaledaor.pruebatodolist.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import todo_jaledaor.pruebatodolist.R;

public class AnswerActivity_read extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText addTaskBox;
    String uid = "";
    String uid_resp = "";
    public String pregunta = "";
    public String respuesta = "";
    public String fecha = "";
    public String categoria = "";
    public Boolean respondida = false;


    String nombre_usuario_pregunta="";
    String nombre_usuario_responde="";
    String pregunta_r = "";
    String categoria_r ="";
    String fecha_r ="";
    String uid_preg_r ="";
    String uid_resp_r ="";
    String respuesta_r ="";
    boolean respondida_r=false;

    String respuesta_insert="";

    private FirebaseAuth mAuth_control;
    private FirebaseDatabase database_control;
    private DatabaseReference reference_control;
    private DatabaseReference reference_control_2;
    private List<Task> allTask;


    TextView pregunta_screen;
    TextView catergoria_screen;
    TextView fecha_screen;
    TextView respondida_screen;
    TextView uid_preg_screen;
    TextView uid_resp_screen;
    TextView respuesta_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_read);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        pregunta_screen = findViewById(R.id.pregunta_activity_r);
        catergoria_screen = findViewById(R.id.categoria_activity_r);
        fecha_screen = findViewById(R.id.fecha_activity_r);
        respondida_screen = findViewById(R.id.respondida_activity_r);
        uid_preg_screen = findViewById(R.id.uid_preg_activity_r);
        uid_resp_screen = findViewById(R.id.uid_resp_activity_r);
        respuesta_screen = findViewById(R.id.respuesta_activity_r);


        setSupportActionBar(toolbar);
        mAuth_control = FirebaseAuth.getInstance();
        uid = "";
        uid = mAuth_control.getCurrentUser().getUid().toString();
        database_control = FirebaseDatabase.getInstance();
        reference_control_2 = database_control.getReference("usuarios");

        Button btn_responder = findViewById(R.id.btn_responder_r);
        Button btn_volver = findViewById(R.id.btn_volver_r);
        Intent intent = getIntent();
        btn_responder.setEnabled(false);

        pregunta_r = intent.getStringExtra("pregunta_review");
        categoria_r = intent.getStringExtra("categoria_review");
        fecha_r = intent.getStringExtra("fecha_review");
        uid_preg_r = intent.getStringExtra("uid_preg_review");
        uid_resp_r = intent.getStringExtra("uid_resp_review");
        respuesta_r = intent.getStringExtra("respuesta_review");
        respondida_r = intent.getBooleanExtra("respondida_review", false);

        reference_control_2.child(uid_preg_r).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombre_usuario_pregunta=dataSnapshot.child("nombres").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference_control_2.child(uid_resp_r).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombre_usuario_responde=dataSnapshot.child("nombres").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        pregunta_screen.setText("Pregunta: "+pregunta_r);
        catergoria_screen.setText("Categoria: "+categoria_r);
        fecha_screen.setText("Fecha: "+fecha_r);
        respondida_screen.setText("¿Ya Fue Respondída? " + respondida_r);
        uid_preg_screen.setText("Uid Usuario Que Creó la Pregunta: "+uid_preg_r);
        uid_resp_screen.setText("Uid Usuario Que Respondió la Pregunta: "+uid_resp_r);
        respuesta_screen.setText("Respuesta: "+respuesta_r);

        btn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                respuesta_insert = respuesta_screen.getText().toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tareas");
                Query applesQuery = ref.orderByChild("pregunta").equalTo(pregunta_r);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().child("uid_resp").setValue(uid);
                            appleSnapshot.getRef().child("respondida").setValue(true);
                            appleSnapshot.getRef().child("respuesta").setValue(respuesta_insert);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salida) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
