package sg.edu.rp.c346.id20024466.l09ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ModifySong extends AppCompatActivity {

    EditText etTitle, etSinger, etYear, etId;
    RadioGroup rgStars;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnUpdate, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        etId = findViewById(R.id.etID);
        rgStars = findViewById(R.id.rgStars);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("song");

        etId.setText(currentSong.getId() + "");
        etTitle.setText(currentSong.getTitle());
        etSinger.setText(currentSong.getSingers());
        etYear.setText(currentSong.getYear());
        switch (currentSong.getStars()) {
            case 5:
                rb5.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
            case 3:
                rb3.setChecked(true);
            case 2:
                rb2.setChecked(true);
            case 1:
                rb1.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                currentSong.setTitle(etTitle.getText().toString().trim());
                currentSong.setSingers(etSinger.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(ModifySong.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentSong.setYear(year);

                int selectedRB = rgStars.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedRB);
                currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                int result = dbh.updateSong(currentSong);
                if (result>0) {
                    Toast.makeText(ModifySong.this, "Song updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ModifySong.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                int result = dbh.deleteSong(currentSong.getId());
                if (result > 0) {
                    Toast.makeText(ModifySong.this, "Song deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ModifySong.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}