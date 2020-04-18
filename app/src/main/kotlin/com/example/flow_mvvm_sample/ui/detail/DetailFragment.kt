package com.example.flow_mvvm_sample.ui.detail

import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

}