package todo_jaledaor.pruebatodolist.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import todo_jaledaor.pruebatodolist.R;
import todo_jaledaor.pruebatodolist.vistas.RecyclerViewAdapter;
import todo_jaledaor.pruebatodolist.vistas.Task;

public class misPreguntas extends Fragment {
    private RecyclerViewAdapter recyclerViewAdapter;
    private FirebaseAuth mAuth_control;
    private FirebaseDatabase database_control;
    private DatabaseReference reference_control;
    private List<Task> allTask;
    public String uid="";
    public String uid2="";
    public String uid_preg = "";
    public String uid_resp = "";
    public String pregunta = "";
    public String respuesta = "";
    public String fecha = "";
    public String categoria ="";
    public Boolean respondida = false;
    RecyclerView rv;
    public misPreguntas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        allTask = new ArrayList<Task>();
        mAuth_control = FirebaseAuth.getInstance();
        uid = "";
        uid = mAuth_control.getCurrentUser().getUid().toString();
        database_control = FirebaseDatabase.getInstance();
        reference_control = database_control.getReference("Tareas");

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

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        rv= rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), allTask);
        rv.setAdapter(recyclerViewAdapter);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }


    private void getAllTask(DataSnapshot dataSnapshot) {

        reference_control = database_control.getReference("Tareas");
        uid2 = dataSnapshot.child("uid_preg").getValue(String.class);
        if (uid.equals(uid2)) {
            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                uid_preg = dataSnapshot.child("uid_preg").getValue(String.class);
                uid_resp = dataSnapshot.child("uid_resp").getValue(String.class);
                pregunta = dataSnapshot.child("pregunta").getValue(String.class);
                respuesta = dataSnapshot.child("respuesta").getValue(String.class);
                categoria = dataSnapshot.child("categoria").getValue(String.class);
                fecha = dataSnapshot.child("fecha").getValue(String.class);
                respondida = dataSnapshot.child("respondida").getValue(Boolean.class);
            }
            allTask.add(new Task(pregunta, categoria, respuesta, fecha, respondida, uid_preg, uid_resp));

        }
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), allTask);
        rv.setAdapter(recyclerViewAdapter);
    }

}