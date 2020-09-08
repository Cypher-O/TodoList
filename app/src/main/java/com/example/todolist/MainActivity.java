package com.example.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Date;
import adapter.TodoAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Todo;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView todos;
    ArrayList <Todo> arrayList;
    TodoAdapter todoAdapter;
    FloatingActionButton fab;
    ImageView editButton, deleteButton;
    private LinearLayoutManager linearLayoutManager;
    private static final String TAG = "MainActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private CircleImageView googleSigninCircleImageView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(72, 112, 255));

        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                startActivity(intent);
            }
        });

        //working with data
        editButton = findViewById(R.id.editBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        todos = findViewById(R.id.todos);
        googleSigninCircleImageView = findViewById(R.id.profile_pic);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//        todos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        todos.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        arrayList = new ArrayList<Todo>();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();

        boolean firstRun = sharedPreferences.getBoolean("firstRun", true);
        Log.d("Tag1", "fistRun: " + Boolean.valueOf(firstRun).toString());

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        databaseReference = FirebaseDatabase.getInstance().getReference("TodoList");

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount != null){
//            Uri personPhoto = googleSignInAccount.getPhotoUrl();
//            googleSigninCircleImageView.setImageURI(googleSignInAccount.getPhotoUrl());
            Picasso.get().load(googleSignInAccount.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(googleSigninCircleImageView);
        }
        //get data from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TodoList");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //setting code to retrieve data and replace layout
                int i = 0;
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Todo todo = dataSnapshot.getValue(Todo.class);
                    arrayList.add(todo);
                    i++;
//                    todoAdapter.notifyItemInserted(position);
                }
                todoAdapter = new TodoAdapter(getApplicationContext(), arrayList);
                todos.setLayoutManager(linearLayoutManager);
                todos.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
                todoAdapter.notifyItemInserted(0);
                Log.i(TAG, arrayList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void ClearList(){
        if(arrayList != null){
            arrayList.clear();
        }else{
            arrayList = new ArrayList<Todo>();
        }
    }

    //    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(), HomeTodo.class);
//        startActivity(intent);
//    }
}
