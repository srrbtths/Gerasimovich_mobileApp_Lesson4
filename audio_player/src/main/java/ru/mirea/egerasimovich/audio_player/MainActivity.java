package ru.mirea.egerasimovich.audio_player;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import ru.mirea.egerasimovich.audio_player.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private boolean isPause = true;

    private Handler mHandler = new Handler();

    protected static ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageButton = findViewById(R.id.playStatusButton);
        imageButton.setImageResource(R.drawable.pause);


    }
    public void OnClickMedia (View view){
        if (isPause) {
            imageButton.setImageResource(R.drawable.play);
        } else {
            imageButton.setImageResource(R.drawable.pause);
        }
        isPause = !isPause;
    }

}