package com.imtiaz.callbackflowapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.imtiaz.callbackflowapi.databinding.ActivityMainBinding
import com.imtiaz.callbackflowapi.util.checkConnection
import com.imtiaz.callbackflowapi.util.textChange
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    checkConnection().collect{
                    val check = when(it){
                           true -> "Connected with internet"
                           false -> "Not connected "
                    }
                        binding.checkNetwork.text = check
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.username.textChange().debounce(400L).collect{
                    Log.d("main", "onCreate: $it")
                }
            }
        }


    }
}