package com.example.flow_mvvm_sample.ui.top

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flow_mvvm_sample.R
import com.example.flow_mvvm_sample.databinding.ActivityTopBinding
import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.ui.detail.DetailFragment
import com.example.flow_mvvm_sample.util.ext.bind
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
        setSupportActionBar(binding.toolbar)
        adapter = RepoAdapter(this::showDetail)
        binding.recyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        bind(viewModel.isLoading) {
            binding.isLoading = it
        }
        bind(viewModel.data) {
            adapter.setList(it)
        }
    }

    private fun showDetail(repo: Repo) {
        val dialogFragment = DetailFragment.newInstance(repo.owner.login, repo.name)
        dialogFragment.show(supportFragmentManager, dialogFragment.tag)
    }
}