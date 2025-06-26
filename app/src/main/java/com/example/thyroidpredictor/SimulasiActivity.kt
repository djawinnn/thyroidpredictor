package com.example.thyroidpredictor

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiActivity : AppCompatActivity() {

    private lateinit var tflite: Interpreter

    private val mean = floatArrayOf(
        40.8668407f, 0.18537859f, 0.127937337f, 0.07310705f,
        0.0182767624f, 1.95039164f, 2.5613577f, 2.92428198f,
        2.55091384f, 0.644908616f, 1.56657963f, 2.20626632f,
        0.54308094f, 0.046997389f, 0.242819843f, 1.57441253f
    )

    private val scale = floatArrayOf(
        15.11472307f, 0.38860439f, 0.33402002f, 0.26031214f, 0.13395045f,
        0.63009271f, 1.34834595f, 1.17057503f, 0.88909433f, 0.478541f,
        0.64239317f, 1.34290998f, 0.85661177f, 0.21163325f, 0.77226356f, 0.91638586f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulasi)

        tflite = Interpreter(loadModelFile())

        val editTextIds = listOf(
            R.id.editAge, R.id.editGender, R.id.editSmoking, R.id.editHxSmoking,
            R.id.editHxRadiotherapy, R.id.editThyroidFunction, R.id.editPhysicalExamination,
            R.id.editAdenopathy, R.id.editPathology, R.id.editFocality, R.id.editRisk,
            R.id.editT, R.id.editN, R.id.editM, R.id.editStage, R.id.editResponse
        )

        val btnPrediksi = findViewById<Button>(R.id.btnPrediksi)
        val txtHasil = findViewById<TextView>(R.id.txtHasil)

        btnPrediksi.setOnClickListener {
            try {
                val rawInput = editTextIds.map { id ->
                    val value = findViewById<EditText>(id).text.toString().lowercase().trim()
                    when {
                        value == "m" -> 1f
                        value == "f" -> 0f
                        value == "yes" -> 1f
                        value == "no" -> 0f
                        value.isEmpty() -> 0f
                        else -> value.toFloatOrNull() ?: 0f
                    }
                }

                val scaledInput = rawInput.mapIndexed { index, value ->
                    (value - mean[index]) / scale[index]
                }.toFloatArray()

                val input = arrayOf(scaledInput)
                val output = Array(1) { FloatArray(1) }

                tflite.run(input, output)

                val hasil = output[0][0]
                txtHasil.text = if (hasil > 0.5f)
                    "Prediksi: Penderita Tiroid (%.2f)".format(hasil)
                else
                    "Prediksi: Sehat (%.2f)".format(hasil)

            } catch (e: Exception) {
                txtHasil.text = "Terjadi kesalahan: ${e.message}"
                Log.e("PrediksiError", "Gagal prediksi", e)
            }
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = assets.openFd("ml/thyroid_model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }
}
