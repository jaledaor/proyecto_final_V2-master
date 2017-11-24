package todo_jaledaor.pruebatodolist.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import todo_jaledaor.pruebatodolist.R;
import todo_jaledaor.pruebatodolist.fragmentos.misPreguntas;
import todo_jaledaor.pruebatodolist.fragmentos.otrasPreguntas;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText addTaskBox;
    String uid = "";
    String uid_resp = "";
    String answer="";
    public String pregunta = "";
    public String respuesta = "";
    public String fecha = "";
    public String categoria = "";
    public Boolean respondida = false;

    private FirebaseAuth mAuth_control;
    private FirebaseDatabase database_control;
    private DatabaseReference reference_control;
    private List<Task> allTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        allTask = new ArrayList<Task>();
        mAuth_control = FirebaseAuth.getInstance();
        uid = "";
        uid = mAuth_control.getCurrentUser().getUid().toString();
        database_control = FirebaseDatabase.getInstance();
        reference_control = database_control.getReference("Tareas");

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        addTaskBox = (EditText) findViewById(R.id.add_task_box);
        recyclerView = (RecyclerView) findViewById(R.id.task_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FloatingActionButton addTaskButton = findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_question, null);
                final EditText question_input = mView.findViewById(R.id.question_input);
                final EditText category_input = mView.findViewById(R.id.category_input);
                final TextView fecha_elegida = mView.findViewById(R.id.fecha_elegida);

                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date myDate = new Date();
                String filename = timeStampFormat.format(myDate);
                fecha_elegida.setText(filename);

                builder.setView(mView)
                        .setTitle("Adicionar Pregunta")
                        .setPositiveButton("OK", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                pregunta = question_input.getText().toString();
                                if (TextUtils.isEmpty(pregunta)) {
                                    Toast.makeText(MainActivity.this, "No debe estar vacia la pregunta", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                categoria = category_input.getText().toString();
                                if (TextUtils.isEmpty(categoria)) {
                                    Toast.makeText(MainActivity.this, "No debe estar vacia la categoria", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                Task taskObject = new Task();
                                taskObject.setPregunta(pregunta);
                                taskObject.setCategoria(categoria);
                                taskObject.setRespuesta("");
                                taskObject.setFecha(fecha_elegida.getText().toString());
                                taskObject.setUid_preg(uid);
                                taskObject.setUid_resp("");
                                taskObject.setRespondida(false);

                                reference_control.push().setValue(taskObject);
                                dialog.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        reference_control.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                taskDeletion(dataSnapshot);
                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllTask(DataSnapshot dataSnapshot) {

        reference_control = database_control.getReference("Tareas");

        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            /*Task pregunta = singleSnapshot.getValue(Task.class);*/
            pregunta = dataSnapshot.child("pregunta").getValue(String.class);
            respuesta = dataSnapshot.child("respuesta").getValue(String.class);
            categoria = dataSnapshot.child("categoria").getValue(String.class);
            fecha = dataSnapshot.child("fecha").getValue(String.class);
            respondida = dataSnapshot.child("respondida").getValue(Boolean.class);
        }
        allTask.add(new Task(pregunta, categoria, respuesta, fecha, respondida, uid, uid_resp));
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, allTask);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void taskDeletion(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            answer = dataSnapshot.child("pregunta").getValue().toString();
            for (int i = 0; i < allTask.size(); i++) {
                if (allTask.get(i).getPregunta().equals(answer)) {
                    allTask.remove(i);
                }
            }
            Log.d(TAG, "Task title " + answer);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, allTask);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
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

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Preguntas De Otros Usuarios", "Mis Preguntas"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new otrasPreguntas();
                case 1:
                    return new misPreguntas();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }

    }
}
