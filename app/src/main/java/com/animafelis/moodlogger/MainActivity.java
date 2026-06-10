package com.animafelis.moodlogger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.util.TypedValue;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String selectedMood = "";
    LinearLayout entryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtSelectedMood = findViewById(R.id.txtSelectedMood);
        EditText editNote = findViewById(R.id.editNote);
        Button btnSave = findViewById(R.id.btnSave);
        entryContainer = findViewById(R.id.entryContainer);

        int[] moodBtnIds = {
                R.id.btnHappy, R.id.btnSad,
                R.id.btnTired, R.id.btnAnxious, R.id.btnCalm
        };
        String[] moodLabels = {
                "😊 Happy", "😢 Sad",
                "😴 Tired", "😰 Anxious", "😌 Calm"
        };

        for (int i = 0; i < moodBtnIds.length; i++) {
            final String mood = moodLabels[i];
            findViewById(moodBtnIds[i]).setOnClickListener(v -> {
                selectedMood = mood;
                txtSelectedMood.setText("Selected: " + mood);
            });
        }

        btnSave.setOnClickListener(v -> {
            String note = editNote.getText().toString().trim();

            if (selectedMood.isEmpty()) {
                Toast.makeText(this, "Please select a mood first!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (note.isEmpty()) {
                Toast.makeText(this, "Please write something about your day!", Toast.LENGTH_SHORT).show();
                return;
            }

            String timestamp = new SimpleDateFormat(
                    "dd MMM yyyy, hh:mm a", Locale.getDefault()
            ).format(new Date());

            addEntryCard(selectedMood, timestamp, note);

            editNote.setText("");
            selectedMood = "";
            txtSelectedMood.setText("No mood selected");
            Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show();
        });
    }

    private void addEntryCard(String mood, String timestamp, String note) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.parseColor("#2a2a4a"));
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 16);
        card.setLayoutParams(cardParams);
        card.setPadding(32, 24, 32, 24);

        TextView tvMood = new TextView(this);
        tvMood.setText(mood + "  ·  " + timestamp);
        tvMood.setTextColor(Color.parseColor("#e8b4f8"));
        tvMood.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        TextView tvNote = new TextView(this);
        tvNote.setText(note);
        tvNote.setTextColor(Color.WHITE);
        tvNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        noteParams.setMargins(0, 8, 0, 0);
        tvNote.setLayoutParams(noteParams);

        card.addView(tvMood);
        card.addView(tvNote);
        entryContainer.addView(card, 0);
    }
}