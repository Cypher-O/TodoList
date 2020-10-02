package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.DeleteOption;
import com.example.todolist.EditTask;
import com.example.todolist.R;
import java.util.ArrayList;
import model.TodoClass;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    Context context;
    ArrayList<TodoClass> myTodo;

    public TodoAdapter(Context context1, ArrayList<TodoClass> todo1){
        context = context1;
        myTodo = todo1;
//        setHasStableIds(true);
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
//        myViewHolder.setIsRecyclable(false);

        final String getTodoTitles = myTodo.get(position).getTodoTitle();
        final String getTodoDescriptions = myTodo.get(position).getTodoDescription();
        final String getTodoDates = myTodo.get(position).getTodoDate();
        final String getTodoKeys = myTodo.get(position).getTodoKey();

        myViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTask.class);
                intent.putExtra("todoTitle", getTodoTitles);
                intent.putExtra("todoDescription", getTodoDescriptions);
                intent.putExtra("todoDate", getTodoDates);
                intent.putExtra("todoKey", getTodoKeys);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        myViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteOption.class);
                intent.putExtra("todoTitle", getTodoTitles);
                intent.putExtra("todoDescription", getTodoDescriptions);
                intent.putExtra("todoDate", getTodoDates);
                intent.putExtra("todoKey", getTodoKeys);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTodo.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @Override
//    public void setHasStableIds(boolean hasStableIds) {
//        super.setHasStableIds(hasStableIds);
//    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView todoTitle, todoDescription, todoDate, todoKey;
        ImageView editButton, deleteButton;

        public MyViewHolder( @NonNull View itemView) {
            super(itemView);
            todoTitle = (TextView) itemView.findViewById(R.id.todo_title);
            todoDescription = (TextView) itemView.findViewById(R.id.todo_desc);
            todoDate = (TextView) itemView.findViewById(R.id.todo_date);
            editButton = (ImageView) itemView.findViewById(R.id.editBtn);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteBtn);
        }
    }
}
