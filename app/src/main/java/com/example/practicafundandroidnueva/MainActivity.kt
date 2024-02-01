package com.example.practicafundandroidnueva

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.practicafundandroidnueva.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG_EMAIL = "MyEmail"
    private val TAG_PASSWROD = "MyPassword"

    private lateinit var binding : ActivityMainBinding

    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserInterface()

        lifecycleScope.launch{
            viewModel.uiState.collect{
                when (it){
                    is LoginViewModel.UiState.Started ->  Log.w("TAG", "Started")
                    is LoginViewModel.UiState.Ended -> Log.w("TAG", "Ended")
                    is LoginViewModel.UiState.LoginOK -> CoreActivity.launch(binding.saveButton.context, viewModel.token)
                    is LoginViewModel.UiState.Error -> Log.w("TAG", "Error en UiState")

                }
            }
        }

    }

    private fun setUserInterface(){
        loadDataFromPreferences()
        setActionMethod()
    }


    private fun setActionMethod(){

        binding.saveButton.setOnClickListener {
            if (!binding.saveButton.isChecked){
                saveDataInPreferences("", "")
                Toast.makeText(this,getString(R.string.datos_borrados), Toast.LENGTH_LONG).show()
                loadDataFromPreferences()
            }
        }

        binding.buttonLogin.setOnClickListener {

            if (!binding.tfEmail.text.isEmpty() && !binding.tfPassword.text.isEmpty()){
                if (binding.saveButton.isChecked ){
                    saveDataInPreferences(binding.tfEmail.text.toString(), binding.tfPassword.text.toString())
                    Toast.makeText(this,getString(R.string.datos_guardados), Toast.LENGTH_LONG).show()
                }else{
                    Log.w("Tag","Login data will not be saved")
                }
                viewModel.tryLogin(binding.tfEmail.text.toString(), binding.tfPassword.text.toString())
                Log.w("Login", "Inside the login call method")
            }
        }
    }


    private fun saveDataInPreferences(mail: String, pass: String){
        getPreferences(MODE_PRIVATE).edit().apply {
            putString(TAG_EMAIL, mail).apply()
            putString(TAG_PASSWROD, pass).apply()

        }
    }

    private fun loadDataFromPreferences(){
        getPreferences(MODE_PRIVATE).apply {
            binding.tfEmail.setText(getString(TAG_EMAIL,""))
            binding.tfPassword.setText(getString(TAG_PASSWROD,""))
            if (binding.tfEmail.text.toString() != "" && binding.tfPassword.text.toString() != ""){
                binding.saveButton.isChecked = true
            }
        }
    }
}