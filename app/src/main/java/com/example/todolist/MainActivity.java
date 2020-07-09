package com.example.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.TodoAdapter;
import model.Todo;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView todos;
    ArrayList<Todo> arrayList;
    TodoAdapter todoAdapter;
    FloatingActionButton fab;
    private ImageView editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(72, 112,255));

        fab = findViewById(R. id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);
            }
        });

        //working with data
        editButton = findViewById(R.id.editBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        todos = findViewById(R.id.todos);
        todos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        arrayList = new ArrayList<Todo>();

        //get data from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TodoList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //setting code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Todo todo = dataSnapshot.getValue(Todo.class);
                    arrayList.add(todo);
//                    arrayList.add(position, todo);
//                    todoAdapter.notifyItemInserted(position);
                }
                todoAdapter = new TodoAdapter(getApplicationContext(), arrayList);
                todos.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("TodoList");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeTodo.class);
        startActivity(intent);
    }
}
