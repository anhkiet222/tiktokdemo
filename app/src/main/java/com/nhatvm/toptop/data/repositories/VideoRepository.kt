package com.nhatvm.toptop.data.repositories

import com.nhatvm.toptop.R
import javax.inject.Inject

class VideoRepository @Inject constructor() {
    private val video = listOf(
        R.raw.one,
        R.raw.two,
        R.raw.three,
        R.raw.four,
    )
    fun getVideo() = video.random();
}