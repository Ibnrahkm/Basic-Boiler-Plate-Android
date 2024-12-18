package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutMediaPlayerBinding


class MediaPlayerFragment : BaseFragment() {

    lateinit var binding: LayoutMediaPlayerBinding
    var gson = Gson()
    private lateinit var player: ExoPlayer

    companion object {
        var bookmarked = MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutMediaPlayerBinding>(
            inflater, R.layout.layout_media_player, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        player = ExoPlayer.Builder(requireActivity()).build()
        binding.playerView.setPlayer(player)

        val uri = Uri.parse(requireArguments().getString("url"))
        val mediaItem: MediaItem = MediaItem.fromUri(uri)


        // Prepare the player with the media item
        player.setMediaItem(mediaItem)
        player.prepare()
        player.setPlayWhenReady(true) // Start playing when ready
        binding.btnDismiss.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}