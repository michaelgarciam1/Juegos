package com.example.juegos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class Setting extends AppCompatActivity {
    private ActivityResultLauncher<Intent> galleryLauncher;

    MediaPlayer mediaPlayer;
    private Database db;
    Button btChange;
    private String userName;
    private Button finishSession;
    private ImageView imgPerfil;
    private Button btBack;
    private Button btChangePassword;
    private Button data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db = new Database(this);
        userName = Util.getUserName(this);
        initializeView();
        setListeners();
        setPhoto();
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            if (checkImageSize(selectedImageUri)) {
                                Bitmap imageBitmap = getBitmapFromUri(selectedImageUri);
                                db.setPhoto(userName, imageBitmap);
                                Util.alerta("Imagen cambiada", this);
                                setPhoto();
                            } else {
                                Util.alerta("La imagen es muy grande, Tamaño maximo 1MB", this);
                            }
                        }
                    }
                });

        mediaPlayer=MediaPlayer.create(this,R.raw.fondo_menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void setListeners() {
        btChange.setOnClickListener(v -> captureImage());
        btBack.setOnClickListener(v -> {
            Intent intent = new Intent(Setting.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btChangePassword.setOnClickListener(v -> changePassword());
        finishSession.setOnClickListener(v -> cerrarSesion());
        data.setOnClickListener(v -> createData());
    }

    private void initializeView() {
        btChange = findViewById(R.id.bt_changePhoto);
        imgPerfil = findViewById(R.id.imgPerfilSetting);
        finishSession = findViewById(R.id.btFinish);
        btBack = findViewById(R.id.bt_backMenu);
        btChangePassword = findViewById(R.id.btCambioContraseña);
        data = findViewById(R.id.btData);
    }

    private void cerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea cerrar sesión?");
        builder.setPositiveButton("Si", (dialog, which) -> {
            Intent intent = new Intent(Setting.this, GameCenter.class);
            startActivity(intent);
            mediaPlayer.stop();
            finish();
        });
        builder.setNegativeButton("No", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean checkImageSize(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            int fileSize = inputStream.available(); // Tamaño en bytes
            inputStream.close();

            // Verificar si el tamaño es menor de 1 MB (1024 * 1024 bytes)
            return fileSize <= (1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Manejo de errores, la verificación falla si hay algún problema al obtener el tamaño
        }
    }

    private void changePassword() {
        EditText etPassword = findViewById(R.id.editContraseñaActual);
        EditText etNewPassword = findViewById(R.id.editNuevaContraseña);
        String password = etPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        if (password.equals("") || newPassword.equals("")) {
            etPassword.setError("Complete los campos");
            etNewPassword.setError("Complete los campos");
            Util.alerta("Complete los campos", this);
        } else {
            if (db.checkUser(userName, password)) {
                db.updatePassword(userName, newPassword);
                etPassword.setText("");
                etNewPassword.setText("");
                Util.alerta("Contraseña cambiada", this);

            } else {
                Util.alerta("Contraseña incorrecta", this);
            }
        }
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

    private void setPhoto() {
        try {
            Bitmap image = db.getPhoto(userName);
            if (image != null && image.getByteCount() > 0) {
                imgPerfil.setImageBitmap(image);
            } else {
                imgPerfil.setImageResource(R.drawable.perfil);
            }
        } catch (Exception e) {
            imgPerfil.setImageResource(R.drawable.perfil);
            e.printStackTrace();
        }
    }

    private void createData() {
        Util.createData(this, userName);
        Toast.makeText(this, "Datos Random creados", Toast.LENGTH_SHORT).show();
    }

}