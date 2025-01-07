package com.example.android_trivia_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_trivia_app.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameWonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.appToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.nextMatchButton.setOnClickListener {
            it.findNavController()
                .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        if (noCompatibleShareActivity())
            binding.appToolbar.menu.findItem(R.id.share).setVisible(false)

        binding.appToolbar.setOnMenuItemClickListener {
            shareSucess()
            true
        }

        return binding.root
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent
            .setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_sucess_text, args.numCorrect, args.numQuestions)
            )

        return shareIntent
    }

    private fun shareSucess() {
        startActivity(getShareIntent())
    }

    private fun noCompatibleShareActivity() =
        getShareIntent().resolveActivity(requireActivity().packageManager) == null
}