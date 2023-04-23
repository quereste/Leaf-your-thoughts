package com.jagiellonianleaf.leafyourthoughts

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.gms.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.gms.vision.classifier.ImageClassifier.ImageClassifierOptions
import java.nio.ByteBuffer

class MLClassifierViewModel(application: Application) : AndroidViewModel(application) {

    private val _classificationResult = MutableLiveData<String>()
    val classificationResult: LiveData<String>
        get() = _classificationResult

    private val modelFile = "model_v3.tflite"
    private var model: ImageClassifier? = null

    init {
        val baseOptions = BaseOptions.builder()
            .useGpu()
            .build()
        val options = ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(1)
            .build()
        model = ImageClassifier.createFromFileAndOptions(getApplication(), modelFile, options)
    }

    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun processImage(imageProxy: ImageProxy): TensorImage {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))    // TODO: check out the other resize method
            .build()
        Log.w("imageProxy format: ", imageProxy.format.toString())
        var tfImage = TensorImage(DataType.UINT8)
        tfImage.load(imageProxyToBitmap(imageProxy))
        tfImage = imageProcessor.process(tfImage)
        return tfImage
    }

    fun predict(imageProxy: ImageProxy) {
        val res = model?.classify(processImage(imageProxy))
        imageProxy.close()
        val label = res?.get(0)?.categories?.get(0)?.label
        _classificationResult.postValue(label ?: "Failed to predict?(placeholder)")
        Log.i("Classification result: ", "${label}: ${res?.get(0)?.categories?.get(0)?.label}")
    }
}