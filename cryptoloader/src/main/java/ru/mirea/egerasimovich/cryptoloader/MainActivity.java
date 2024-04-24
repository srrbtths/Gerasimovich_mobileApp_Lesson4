package ru.mirea.egerasimovich.cryptoloader;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.InvalidParameterException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import ru.mirea.egerasimovich.cryptoloader.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    ActivityMainBinding binding;
    public final String TAG = this.getClass().getSimpleName();
    private final int LoaderID = 5678;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onClick(View view) throws Exception {
        Log.d(TAG, "onClick: click");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String secretMessage = binding.editText.getText().toString();
        byte[] encryptedMessage = cipher.doFinal(secretMessage.getBytes());
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessage);

        Bundle bundle = new Bundle();
        bundle.putString(MyLoader.ARG_WORD, encodedMessage);
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        bundle.putString(MyLoader.ARG_KEY, encodedKey);
        LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this);
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader:" + i, Toast.LENGTH_SHORT).show();
            try {
                return new MyLoader(this, bundle);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new InvalidParameterException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if	(loader.getId() == LoaderID) {
            Log.d(TAG, "onLoadFinished: " + s);
            Toast.makeText(this, "onLoadFinished. Message: : " + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }
}