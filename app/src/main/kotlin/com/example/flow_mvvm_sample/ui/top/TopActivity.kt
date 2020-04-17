package com.example.flow_mvvm_sample.ui.top

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.flow_mvvm_sample.R
import com.example.flow_mvvm_sample.databinding.ActivityTopBinding
import com.example.flow_mvvm_sample.util.ext.launchAllIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class TopActivity : AppCompatActivity() {
    private val viewModel: TopViewModel by viewModel()

    private lateinit var binding: ActivityTopBinding
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        bindViewModel()
    }

    private fun setupView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)
        adapter = RepoAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        listOf(
            viewModel.isLoading.onEach {
                binding.isLoading = it
            },
            viewModel.data.onEach {
                adapter.list = it.orEmpty()
                adapter.notifyDataSetChanged()
            }
        ).launchAllIn(lifecycleScope)
    }
}