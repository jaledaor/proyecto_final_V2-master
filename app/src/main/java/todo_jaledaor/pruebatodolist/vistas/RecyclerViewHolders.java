package todo_jaledaor.pruebatodolist.vistas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import todo_jaledaor.pruebatodolist.R;


public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public Button task_btn;
    public TextView task_title;
    public TextView task_date;
    public TextView category;
    public TextView task_answer;
    public String uid="";
    String answer_dialog="";
    String question="";
    String category_dialog="";
    String fecha_dialog="";
    String uid_preg_dialog="";
    String uid_resp_dialog="";
    String respuesta_dialog="";
    boolean respondida_dialog=false;
    TextView txt_question;
    TextView txt_category;
    EditText answer_input;

    private FirebaseDatabase database_control;
    private DatabaseReference reference_control;
    private FirebaseAuth mAuth_control;
    private List<Task> taskObject;

    public RecyclerViewHolders(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        task_title = itemView.findViewById(R.id.task_title);
        task_answer = itemView.findViewById(R.id.task_answer);
        task_btn = itemView.findViewById(R.id.task_btn);
        task_date = itemView.findViewById(R.id.task_date);
        category = itemView.findViewById(R.id.task_category);
        database_control = FirebaseDatabase.getInstance();
        reference_control = database_control.getReference("Tareas");
        mAuth_control = FirebaseAuth.getInstance();

        uid = "";
        uid = mAuth_control.getCurrentUser().getUid().toString();
        task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!taskObject.get(getAdapterPosition()).getUid_preg().equals(uid)) {
                    if (taskObject.get(getAdapterPosition()).isRespondida() == false) {
                        Intent intent = new Intent(v.getContext(), AnswerActivity.class);
                        question = taskObject.get(getAdapterPosition()).getPregunta();
                        category_dialog = taskObject.get(getAdapterPosition()).getCategoria();
                        fecha_dialog = taskObject.get(getAdapterPosition()).getFecha();
                        uid_preg_dialog = taskObject.get(getAdapterPosition()).getUid_preg();
                        uid_resp_dialog = taskObject.get(getAdapterPosition()).getUid_resp();
                        respondida_dialog = taskObject.get(getAdapterPosition()).isRespondida();
                        respuesta_dialog = taskObject.get(getAdapterPosition()).getRespuesta();


                        intent.putExtra("pregunta_review", question);
                        intent.putExtra("categoria_review", category_dialog);
                        intent.putExtra("fecha_review", fecha_dialog);
                        intent.putExtra("uid_preg_review", uid_preg_dialog);
                        intent.putExtra("uid_resp_review", uid_resp_dialog);
                        intent.putExtra("respondida_review", respondida_dialog);
                        intent.putExtra("respuesta_review", respuesta_dialog);

                        v.getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(v.getContext(), AnswerActivity_read.class);
                        question = taskObject.get(getAdapterPosition()).getPregunta();
                        category_dialog = taskObject.get(getAdapterPosition()).getCategoria();
                        fecha_dialog = taskObject.get(getAdapterPosition()).getFecha();
                        uid_preg_dialog = taskObject.get(getAdapterPosition()).getUid_preg();
                        uid_resp_dialog = taskObject.get(getAdapterPosition()).getUid_resp();
                        respondida_dialog = taskObject.get(getAdapterPosition()).isRespondida();
                        respuesta_dialog = taskObject.get(getAdapterPosition()).getRespuesta();

                        intent.putExtra("pregunta_review", question);
                        intent.putExtra("categoria_review", category_dialog);
                        intent.putExtra("fecha_review", fecha_dialog);
                        intent.putExtra("uid_preg_review", uid_preg_dialog);
                        intent.putExtra("uid_resp_review", uid_resp_dialog);
                        intent.putExtra("respondida_review", respondida_dialog);
                        intent.putExtra("respuesta_review", respuesta_dialog);
                        v.getContext().startActivity(intent);
                    }
                }
                else{
                    Intent intent = new Intent(v.getContext(), AnswerActivity_read.class);
                    question = taskObject.get(getAdapterPosition()).getPregunta();
                    category_dialog = taskObject.get(getAdapterPosition()).getCategoria();
                    fecha_dialog = taskObject.get(getAdapterPosition()).getFecha();
                    uid_preg_dialog = taskObject.get(getAdapterPosition()).getUid_preg();
                    uid_resp_dialog = taskObject.get(getAdapterPosition()).getUid_resp();
                    respondida_dialog = taskObject.get(getAdapterPosition()).isRespondida();
                    respuesta_dialog = taskObject.get(getAdapterPosition()).getRespuesta();

                    intent.putExtra("pregunta_review", question);
                    intent.putExtra("categoria_review", category_dialog);
                    intent.putExtra("fecha_review", fecha_dialog);
                    intent.putExtra("uid_preg_review", uid_preg_dialog);
                    intent.putExtra("uid_resp_review", uid_resp_dialog);
                    intent.putExtra("respondida_review", respondida_dialog);
                    intent.putExtra("respuesta_review", respuesta_dialog);
                    v.getContext().startActivity(intent);
                }
            }

            ;

        });
    }
}