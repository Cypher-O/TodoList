package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    private TextView todoDate;
    TextInputEditText todoTitle, todoDescription;
    ImageView backButton;
    Button discardButton, addNowButton, saveButton;
    DatabaseReference databaseReference;
    ImageView datePickerIconButton;

    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        todoDate = findViewById(R.id.displayDate);
        backButton = findViewById(R.id.backBtn);
        addNowButton = findViewById(R.id.addNow);
        saveButton = findViewById(R.id.saveBtn);
        todoTitle = findViewById(R.id.titleField);
        todoDescription = findViewById(R.id.descriptionField);
        discardButton = findViewById(R.id.discardBtn);
        datePickerIconButton = findViewById(R.id.datePicker_icon);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .58));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = -20;

        getWindow().setAttributes(layoutParams);

        // get value from previous activity
        todoTitle.setText(getIntent().getStringExtra("todoTitle"));
        todoDescription.setText(getIntent().getStringExtra("todoDescription"));
        todoDate.setText(getIntent().getStringExtra("todoDate"));

        final String todoKeys = getIntent().getStringExtra("todoKey");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TodoList").child("Todos" + todoKeys);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("todoTitle").setValue(todoTitle.getText().toString());
                        snapshot.getRef().child("todoDescription").setValue(todoDescription.getText().toString());
                        snapshot.getRef().child("todoDate").setValue(todoDate.getText().toString());
                        snapshot.getRef().child("todoKey").setValue(todoKeys);
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        datePickerIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm-dd-yyy: " + day + "-" + month + "-" + year);

                String date = day + "-" + month + "-" + year;
                todoDate.setText(date);
            }
        };
    }
}