
package com.example.inmob;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmob.ui.login.LoginActivity;

public class MainActivityViewModel extends AndroidViewModel {

    private final Context context;

    private final MutableLiveData<Void> setupNavigationEvent = new MutableLiveData<>();

    private final MutableLiveData<Intent> redirectToLoginEvent = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }


    public LiveData<Void> getSetupNavigationEvent() {
        return setupNavigationEvent;
    }

    public LiveData<Intent> getRedirectToLoginEvent() {
        return redirectToLoginEvent;
    }


    public void iniciarVerificacion() {
        SharedPreferences sp = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = sp.getString("auth_token", null);

        if (token != null) {

            setupNavigationEvent.postValue(null);
        } else {

            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            redirectToLoginEvent.postValue(intent);
        }
    }

    public void logout() {
        SharedPreferences sp = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        sp.edit().remove("auth_token").apply();


        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        redirectToLoginEvent.postValue(intent);
    }
}
