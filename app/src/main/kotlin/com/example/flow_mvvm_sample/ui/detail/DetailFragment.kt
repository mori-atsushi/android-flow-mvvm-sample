package com.example.flow_mvvm_sample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.flow_mvvm_sample.databinding.FragmentDetailBinding
import com.example.flow_mvvm_sample.util.ext.bind
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : BottomSheetDialogFragment() {
    companion object {
        private const val USER_NAME_KEY = "user_name"
        private const val REPO_NAME_KEY = "repo_name"

        fun newInstance(userName: String, repoName: String): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundleOf(
                USER_NAME_KEY to userName,
                REPO_NAME_KEY to repoName
            )
            return fragment
        }
    }

    private val userName: String by lazy {
        requireArguments().getString(USER_NAME_KEY)!!
    }
    private val repoName: String by lazy {
        requireArguments().getString(REPO_NAME_KEY)!!
    }
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(userName, repoName)
    }
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind(viewModel.isLoading) {
            binding.isLoading = it
        }
        bind(viewModel.data) {
            binding.repo = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}