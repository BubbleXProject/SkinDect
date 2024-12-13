package com.dicoding.capstone.skindect.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstone.skindect.R
import com.dicoding.capstone.skindect.adapter.ArticleAdapter
import com.dicoding.capstone.skindect.data.SettingsPreferences
import com.dicoding.capstone.skindect.databinding.ActivityMainBinding
import com.dicoding.capstone.skindect.repository.ArticleRepository
import com.dicoding.capstone.skindect.settings.SettingsActivity
import com.dicoding.capstone.skindect.viewmodel.SettingsViewModel
import com.dicoding.capstone.skindect.utils.ImageHandler
import com.dicoding.capstone.skindect.viewmodel.ArticleViewModel
import com.dicoding.capstone.skindect.viewmodel.MainViewModel
import com.dicoding.capstone.skindect.viewmodel.ViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val articleViewModel: ArticleViewModel by viewModels {
        ViewModelFactory(articleRepository = ArticleRepository())
    }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.Factory(SettingsPreferences(this))
    }

    private lateinit var imageHandler: ImageHandler
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        enableEdgeToEdge()
        setSupportActionBar(binding.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageHandler = ImageHandler(this, mainViewModel)

        observeViewModels()
        setupRecyclerView()
        setupButtonListeners()
    }

    private fun observeViewModels() {


        articleViewModel.articles.observe(this) { articles ->
            articleAdapter.updateArticles(articles)
            binding.progressBar.visibility = View.GONE
        }

        articleViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        mainViewModel.imageUri.observe(this) { uri ->
            uri?.let {
                imageHandler.startCrop(it)
                mainViewModel.setImageUri(null)
            }
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(this, emptyList())
        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }
    }

    private fun setupButtonListeners() {
        binding.btnScan.setOnClickListener {
            imageHandler.showImageSourceDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                val intent = Intent(this, ScanActivity::class.java).apply {
                    putExtra(ScanActivity.EXTRA_IMAGE_URI, it.toString())
                }
                startActivity(intent)
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, R.string.crop_error, Toast.LENGTH_SHORT).show()
        }
    }
}
