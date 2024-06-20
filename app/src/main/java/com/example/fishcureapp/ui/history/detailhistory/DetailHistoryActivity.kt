package com.example.fishcureapp.ui.history.detailhistory

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.data.network.response.SavedDataHistory
import com.example.fishcureapp.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding

    private val solutionViewModel: HistoryDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailData()


    }

    private fun setDetailData() {

        val history = intent.getParcelableExtra<SavedDataHistory>(EXTRA_HISTORY) as SavedDataHistory

        // showLoading(false)
        binding.apply {
            history.let {

                val formattedDiseaseName = history.disease_name.toLowerCase().replace(" ", "_")
                val textSolution = formattedDiseaseName

                Glide.with(this@DetailHistoryActivity)
                    .load(history.image)
                    .into(binding.imgResultHitory)


                tvDetectionHistory.text = history.disease_name
                tvDescAccuracyHistory.text = "${history.akurasi}%"
                tvDateHistory.text = history.dateTime
                //   tvGejalaResultHistory.text = story.symptom
                //   tvDescPenangananHistory.text = story.penanganan
                //   tvDescPerawatanHistory.text = story.perawatan
                //   tvDescObatHistory.text = story.obat

                solutionViewModel.getDiseaseSolution(textSolution)

                solutionViewModel.solutionResult.observe(this@DetailHistoryActivity) { result ->
                    result.fold(
                        onSuccess = { response ->
                            binding.tvGejalaResultHistory.text = response.gejala.values.joinToString("\n") { "• $it" }
                            binding.tvDescPenangananHistory.text = response.langkahPenanganan.values.joinToString("\n") { "• $it" }
                            binding.tvDescObatHistory.text = response.obat.values.joinToString("\n") { "• $it" }

                        },
                        onFailure = { error ->
                            binding.tvDetectionHistory.text = "Solution retrieval failed: ${error.message}"
                        }
                    )
                }

            }
        }

        Log.e(ContentValues.TAG, "showSelectedArticle: ${history.disease_name}")
    }

    companion object {
        const val EXTRA_HISTORY = "extra_story"
    }
}