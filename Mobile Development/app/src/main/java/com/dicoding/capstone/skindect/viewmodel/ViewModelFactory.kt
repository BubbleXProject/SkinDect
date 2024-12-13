package com.dicoding.capstone.skindect.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.skindect.repository.ArticleRepository
import com.dicoding.capstone.skindect.repository.DetailHistoryRepository
import com.dicoding.capstone.skindect.repository.HistoryRepository
import com.dicoding.capstone.skindect.repository.ResultRepository
import com.dicoding.capstone.skindect.repository.ScanRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val context: Context? = null,

    private val articleRepository: ArticleRepository? = null,
    private val scanRepository: ScanRepository? = null,
    private val resultRepository: ResultRepository? = null,
    private val historyRepository: HistoryRepository? = null,
    private val detailHistoryRepository: DetailHistoryRepository? = null

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                articleRepository?.let { ArticleViewModel(it) }
                    ?: error("ArticleRepository not provided")
            }

            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                scanRepository?.let { ScanViewModel(it) } ?: context?.let { ScanViewModel(ScanRepository(it)) }
                ?: error("Context or ScanRepository not provided")
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                resultRepository?.let { ResultViewModel(it) }
                    ?: error("ResultRepository not provided")
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                historyRepository?.let { HistoryViewModel(it) }
                    ?: error("HistoryRepository not provided")
            }
            modelClass.isAssignableFrom(DetailHistoryViewModel::class.java) -> {
                detailHistoryRepository?.let { DetailHistoryViewModel(it) }
                    ?: error("DetailHistoryRepository not provided")
            }

            else -> error("Unknown ViewModel class")
        } as T
    }
}
