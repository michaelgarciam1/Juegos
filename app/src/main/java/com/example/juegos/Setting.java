package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Database db;
    Button btChange;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db = new Database(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");
        btChange = findViewById(R.id.bt_changePhoto);
        btChange.setOnClickListener(v -> captureImage());
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            Bitmap imageBitmap = getBitmapFromUri(selectedImageUri);

                            db.setPhoto(userName, imageBitmap);
                        }
                    }
                }
        );
        Button btBack = findViewById(R.id.bt_backMenu);
        btBack.setOnClickListener(v -> {
            Intent intent = new Intent(Setting.this, MainActivity.class);
            startActivity(intent);
        });

        Button btChangePassword = findViewById(R.id.btCambioContraseña);
        btChangePassword.setOnClickListener(v -> {
            changePassword();
        });

    }

    private void changePassword(){
        EditText etPassword = findViewById(R.id.editContraseñaActual);
        EditText etNewPassword = findViewById(R.id.editNuevaContraseña);
        String password = etPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        if(password.equals("") || newPassword.equals("")) {
            etPassword.setError("Complete los campos");
            etNewPassword.setError("Complete los campos");
            alerta("Complete los campos");
        } else {
            if (db.checkUser(userName, password)) {
                db.updatePassword(userName, newPassword);
                etPassword.setText("");
                etNewPassword.setText("");
                alerta("Contraseña cambiada");

            } else {
                alerta("Contraseña incorrecta");
            }
        }
    }

    private void alerta(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Vale", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}