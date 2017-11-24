package todo_jaledaor.pruebatodolist.vistas;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import todo_jaledaor.pruebatodolist.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Task> task;
    protected Context context;

    public RecyclerViewAdapter(Context context, List<Task> task) {
        this.task = task;
        this.context = context;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, task);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.task_title.setText("pregunta: "+task.get(position).getPregunta());
        holder.task_answer.setText("respuesta: "+task.get(position).getRespuesta());
        holder.category.setText("categoria: "+task.get(position).getCategoria());
        holder.task_date.setText("fecha: "+task.get(position).getFecha());


    }
    @Override
    public int getItemCount() {
        return this.task.size();
    }


}