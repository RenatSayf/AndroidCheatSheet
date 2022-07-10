package com.renatsayf.androidcheatsheet.ui.sections.exoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.renatsayf.androidcheatsheet.databinding.FragmentPlayerBinding
import com.renatsayf.androidcheatsheet.ui.sections.extentions.hideSystemUi

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding

    private val viewModel: PlayerViewModel by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }

    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()

        hideSystemUi(binding.videoView)
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializePlayer()
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                PlayerViewModel.State.Loading -> {

                }
                is PlayerViewModel.State.Current -> {
                    exoPlayer?.let {
                        it.playWhenReady = state.playerState.playWhenReady
                        it.seekTo(state.playerState.currentItem, state.playerState.playbackPosition)
                        it.prepare()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }


    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {

        exoPlayer = ExoPlayer.Builder(requireContext()).build().also { player ->
            binding.videoView.player = player

            val url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            viewModel.setState(PlayerViewModel.State.Current(
                PlayerViewModel.PlayerState(
                    playWhenReady = player.playWhenReady,
                    currentItem = player.currentMediaItemIndex,
                    playbackPosition = player.currentPosition
                )
            ))
            player.release()
        }
        exoPlayer = null
    }
}