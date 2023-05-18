package com.jagiellonianleaf.leafyourthoughts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import org.tensorflow.lite.task.gms.vision.TfLiteVision

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MLClassifierViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val options = TfLiteInitializationOptions.builder()
//            .setEnableGpuDelegateSupport(true)
            .build()

        TfLiteVision.initialize(applicationContext, options).addOnSuccessListener {
            initializeMLClassifier()
        }.addOnFailureListener {
            // Called if the GPU Delegate is not supported on the device
            TfLiteVision.initialize(applicationContext).addOnSuccessListener {
                initializeMLClassifier()
            }.addOnFailureListener{
                Log.e("ERROR INITIALIZING TfLiteVision", it.message.toString())
                finish()
                // TODO: make an alert for the user?
            }
        }
    }

    private fun initializeMLClassifier() {
        viewModel = MLClassifierViewModel(application)
    }
}