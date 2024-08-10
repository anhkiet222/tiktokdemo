package com.nhatvm.toptop.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.video.VideoDetail
import com.nhatvm.toptop.ui.video.VideoDetailViewModel

@UnstableApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListVideoForYou(
    modifier: Modifier =Modifier
){
    val videoDetailViewModel: VideoDetailViewModel = hiltViewModel()
    VerticalPager(pageCount = 10) { videoId ->
        VideoDetail(videoId = videoId, videoDetailViewModel = videoDetailViewModel)
    }
}