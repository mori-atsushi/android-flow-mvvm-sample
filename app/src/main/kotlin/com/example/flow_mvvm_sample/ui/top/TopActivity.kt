package com.example.flow_mvvm_sample.ui.top

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.flow_mvvm_sample.R
import com.example.flow_mvvm_sample.databinding.ActivityTopBinding
import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.ui.detail.DetailFragment
import com.example.flow_mvvm_sample.util.ext.bind
import com.example.flow_mvvm_sample.util.ext.requestQuery
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
        adapter = RepoAdapter(this::showDetail)
        binding.recyclerView.adapter = adapter
        binding.error.setOnClickRetryButton {
            viewModel.retry()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.submit()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setUserName(newText.orEmpty())
                return false
            }
        })
    }

    private fun bindViewModel() {
        bind(viewModel.userName) {
            binding.searchView.requestQuery(it)
        }
        bind(viewModel.isLoading) {
            binding.isLoading = it
        }
        bind(viewModel.isFail) {
            binding.isFail = it
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