package ru.mirea.shchukin.data_thread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import ru.mirea.shchukin.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        String description = "runOnUiThread(Runnable): Этот метод применяется для исполнения кода в главном потоке пользовательского интерфейса (UI). " +
                "Если метод вызывается из фонового потока, указанный Runnable будет помещён в очередь сообщений главного потока и выполнен, когда он освободится.\n\n" +
                "post(Runnable): Метод post() служит для запуска Runnable на View, который исполняется " +
                "в потоке UI. Это достигается за счёт добавления сообщения в очередь. В отличие от runOnUiThread, " +
                "метод post() можно использовать из любой точки, и исполнение начнётся в следующем цикле обработки сообщений потока UI.\n\n" +
                "postDelayed(Runnable, long): Этот метод аналогичен post(), но позволяет запланировать исполнение " +
                "Runnable с задержкой. Runnable будет внесён в очередь и выполнен после заданного времени в миллисекундах.\n\n" +
                "Последовательность вызовов:\n" +
                "1. runn1 выполняется первым с использованием runOnUiThread() после задержки в 2 секунды, начиная выполнение сразу после активации потока.\n" +
                "2. runn2 запускается вторым без задержки через post(), следующим за runn1.\n" +
                "3. runn3 исполняется последним через postDelayed() с дополнительной задержкой в 2 секунды после runn2.";

        binding.tvInfo.setText(description);

    }
}