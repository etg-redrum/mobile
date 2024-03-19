package ru.mirea.shchukin.buttonclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStudent;
    private Button btnWhoAmI;
    private Button btnIsIsNotMe;
    private CheckBox acceptBtn;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        textViewStudent = findViewById(R.id.textViewStudent);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnIsIsNotMe = findViewById(R.id.itIsNotMe);
        acceptBtn = findViewById(R.id.acceptBtn);


        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStudent.setText("Мой номенр по списку 29й");
            }
        };
        btnWhoAmI.setOnClickListener(oclBtnWhoAmI);

        View.OnClickListener isCheckBoxState = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acceptBtn.isChecked()) {
                    textViewStudent.setText("Вы подтвердили!");
                    btnIsIsNotMe.setEnabled(false);
                    acceptBtn.setText("Cancel?");
                } else {
                    textViewStudent.setText("Who?");
                    btnIsIsNotMe.setEnabled(true);
                    acceptBtn.setText("Accept");
                }
            }
        };

        acceptBtn.setOnClickListener(isCheckBoxState);
    }

    public void oclItIsNotMe(View view) {
        textViewStudent.setText("Твой номер другой?");
    }
}
