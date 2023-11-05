package com.vynguyen.toeicvocabularywords.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.databinding.FragmentWriteBinding
import com.vynguyen.toeicvocabularywords.utils.SoundHelper
import com.vynguyen.toeicvocabularywords.viewmodel.WriteFragmentViewModel


class WriteFragment : Fragment() {

    private lateinit var viewBinding: FragmentWriteBinding
    private lateinit var viewModel: WriteFragmentViewModel
    private var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentWriteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WriteFragmentViewModel::class.java]
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this
        viewModel.showExam()
        setObserver()

        return viewBinding.root
    }

    private fun setObserver() {
        viewBinding.lifecycleOwner?.let { lifecycle ->
            viewModel.state.observe(lifecycle) {
                Log.d("ZZZ", "state change - value = $it")
                viewBinding.imgResult.setImageResource(viewModel.imgResultRes)
                viewBinding.imgSpeaker.setOnClickListener {
                    SoundHelper.playSound(requireContext(), viewModel.vocabulary.soundResource)
                }
                if (viewModel.isNeedShowToast) {
                    Toast.makeText(context, R.string.pls_enter_text, Toast.LENGTH_SHORT).show()
                } else if (viewModel.isCanPlaySoundResult()){
                    SoundHelper.playSound(requireContext(), viewModel.soundResult)
                }
            }
        }
    }
}