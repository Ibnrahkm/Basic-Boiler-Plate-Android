package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutPodcastPlayerBinding
import com.ibt.ava.service.model.podcasts.PodcastArrayData
import com.ibt.ava.util.Helper
import java.util.Formatter
import java.util.Locale
import java.util.Timer
import java.util.TimerTask


class PodcastPlayerFragment : BaseFragment() {

    lateinit var binding: LayoutPodcastPlayerBinding
    var gson = Gson()
    private lateinit var player: ExoPlayer
    var timer = Timer()

    companion object {
        var bookmarked = MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutPodcastPlayerBinding>(
            inflater, R.layout.layout_podcast_player, null, false
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

        var data = gson.fromJson(requireArguments().getString("data"), PodcastArrayData::class.java)
        var pos = requireArguments().getString("pos")
        var mediaItems = ArrayList<MediaItem>()
        for (i in data.podcast) {
            mediaItems.add(MediaItem.fromUri(i.audioUrl))
        }

        // Prepare the player with the media item
        player.setMediaItems(mediaItems)
      //  player.seekTo(pos!!.toInt(), 0)
        player.prepare()
        player.playWhenReady = true // Start playing when ready
        binding.btnDismiss.setOnClickListener {
            dismiss()
        }

        binding.btnNext.setOnClickListener {

            player.seekToNext()
        }
        binding.btnPrev.setOnClickListener {
            player.seekToPrevious()
        }
        binding.btnPlayPause.setOnClickListener {
            if (binding.btnPlayPause.tag == null || !(binding.btnPlayPause.tag as Boolean)) {
                player.pause()
                binding.btnPlayPause.tag = true
                binding.btnPlayPause.setImageResource(R.drawable.play)
            } else {
                player.play()
                binding.btnPlayPause.tag = false
                binding.btnPlayPause.setImageResource(R.drawable.pause)
            }
        }

        binding.tvTitle.text = data.podcast[0].title

        binding.tvCategory.text = data.podcast.size.toString() + " پۆدکاست"
        binding.tvFirstAuthorName.text = data.channel.name
        Helper.setImagePromatcally(binding.ivAuthor, data.channel.featuredImageUrl)

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                player.seekTo(binding.seekbar.progress.toLong())
            }
        })

        player.addListener(object : Player.Listener {
            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
                var p = player.currentMediaItemIndex
                binding.tvTitle.text = data.podcast[p].title
                binding.tvCategory.text = data.podcast.size.toString() + " پۆدکاست"
                binding.tvFirstAuthorName.text = data.channel.name
                binding.tvStartTime.text = "00:00:00"
                binding.tvEndTime.text = "00:00:00"
                Helper.setImagePromatcally(binding.ivAuthor, data.channel.featuredImageUrl)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == ExoPlayer.STATE_READY) {
                    val realDurationMillis: Long = player.getDuration()
                    binding.tvEndTime.text = stringForTime(realDurationMillis)
                    binding.seekbar.progress = 0
                    binding.seekbar.max = realDurationMillis.toInt()
                    seekCounter()
                }
            }

            override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
                super.onSeekBackIncrementChanged(seekBackIncrementMs)
             /*   binding.tvStartTime.text = stringForTime(player.currentPosition)
                binding.seekbar.progress = player.currentPosition.toInt()*/
            }

            override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
                super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
               /* binding.tvStartTime.text = stringForTime(player.currentPosition)
                binding.seekbar.progress = player.currentPosition.toInt()*/
            }
        })
    }

    private fun stringForTime(timeMs: Long): String {
        val mFormatBuilder = StringBuilder()
        val mFormatter: Formatter = Formatter(mFormatBuilder, Locale.getDefault())
        val totalSeconds = timeMs / 1000
        //  videoDurationInSeconds = totalSeconds % 60;
        val seconds = totalSeconds % 60
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 3600

        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    fun seekCounter() {
        try {
            timer.cancel()
        } catch (ex: Exception) {

        }
        try {
            timer=Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    try {
                        requireActivity().runOnUiThread {
                            try {
                                var p = player.currentPosition
                                binding.seekbar.progress = p.toInt()
                                binding.tvStartTime.text = stringForTime(p)
                            } catch (ex: Exception) {

                            }
                        }
                    }catch (exc:Exception){

                    }

                }

            }, 0, 1000)
        } catch (ex: Exception) {

        }
    }

    override fun onStop() {
        try {
            timer.cancel()
        } catch (ex: Exception) {

        }
        super.onStop()
        player.release()

    }
}