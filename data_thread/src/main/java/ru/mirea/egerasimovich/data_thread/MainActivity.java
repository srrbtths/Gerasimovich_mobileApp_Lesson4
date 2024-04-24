package ru.mirea.egerasimovich.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import ru.mirea.egerasimovich.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setText("");

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.textView.append("1. runOnUiThread - метод класса Activity, позволяет выполнить определенный код на основном потоке пользовательского интерфейса (UI).\n");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.textView.append("2. postDelayed - метод класса Handler, выполняет задачу через указанный промежуток времени в основном потоке приложения.\n");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.textView.append("3. post - метод класса Handler, помещает задачу в очередь главного потока приложения для выполнения в порядке очереди, когда главный поток будет свободен.\n");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.textView.postDelayed(runn3, 2000);
                    binding.textView.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}