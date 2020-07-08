package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

import java.util.ArrayList;

import model.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    Context context;
    ArrayList<Todo> myTodo;

    public TodoAdapter(Context context1, ArrayList<Todo> todo1){
        context = context1;
        myTodo = todo1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.todoTitle.setText(myTodo.get(position).getTodoTitle());
        myViewHolder.todoDescription.setText(myTodo.get(position).getTodoDescription());
        myViewHolder.todoDate.setText(myTodo.get(position).getTodoDate());
    }

    @Override
    public int getItemCount() {
        return myTodo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView todoTitle, todoDescription, todoDate;

        public MyViewHolder( @NonNull View itemView) {
            super(itemView);
            todoTitle = (TextView) itemView.findViewById(R.id.todo_title);
            todoDescription = (TextView) itemView.findViewById(R.id.todo_desc);
            todoDate = (TextView) itemView.findViewById(R.id.todo_date);
        }
    }
}
