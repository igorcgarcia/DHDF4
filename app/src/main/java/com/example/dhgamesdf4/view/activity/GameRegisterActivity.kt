package com.example.dhgamesdf4.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dhgamesdf4.R
import com.example.dhgamesdf4.util.Constants.Permissions.PERMISSION_CODE
import com.example.dhgamesdf4.viewModel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameRegisterActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel

    private val ivAvatar : ImageView by lazy {
        findViewById(R.id.ivAvatar)
    }

    private val fabAvatar : FloatingActionButton by lazy {
        findViewById(R.id.fabAvatar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_register)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupObservables()

    }

    private fun setupObservables(){
        fabAvatar.setOnClickListener{
            Log.i("Teste", "Alterar imagem capa")
            // pega imagem da galeria
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { img ->
                ivAvatar.setImageURI(img)
                ivAvatar.tag = img
            }
        }
    }


}