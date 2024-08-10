package com.nhatvm.toptop.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.contentValuesOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.design.TikTokVideoPlayer
import com.nhatvm.toptop.ui.video.compose.SideBarView
import com.nhatvm.toptop.ui.video.compose.VideoInfoArea
import com.nhatvm.toptop.ui.video.compose.VideoInfoItem

@UnstableApi
@Composable
fun VideoDetail(
    videoId: Int,
    videoDetailViewModel: VideoDetailViewModel
) {
    val uiState = videoDetailViewModel.uiState.collectAsState()
    if(uiState.value == VideoDetailUiState.Default){
        videoDetailViewModel.handleAction(VideoDetailAction.loadData(videoId))
    }
    VideoDetail(uiState = uiState.value, player = videoDetailViewModel.videoPlayer ){
        action ->
        videoDetailViewModel.handleAction(action = action)
    }
}
@Composable
@UnstableApi
fun VideoDetail(
    uiState : VideoDetailUiState,
    player: Player,
    handleAction: (VideoDetailAction) -> Unit,
){
    when(uiState){
        is VideoDetailUiState.Loading ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = "loading ...")
            }
        }
        is VideoDetailUiState.Success ->{
            VideoDetail(player = player, handleAction = handleAction)
        }
        else -> {

        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@UnstableApi
@Composable
fun VideoDetail(
    player: Player,
    handleAction: (VideoDetailAction) -> Unit,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .clickable(
            onClick = {
                handleAction(VideoDetailAction.TogVideo)
            }
        )) {
        val (videoPlayerView, sideBar, videoInfo) = createRefs()
        TikTokVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayerView){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.matchParent
        })
        SideBarView(
            onAvatarClick = { /*TODO*/ },
            onLikeClick = { /*TODO*/ },
            onCommentClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ },
            onShareClick = {},
            modifier = Modifier.constrainAs(sideBar){
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
        VideoInfoArea(
            accountName = "Nhạc Việt Nam",
            videoName = "Clone Tiktok",
            hashTags = listOf("TikTok", "DungHoangPham","Android"),
            songname = "Anh Thôi Nhân Nhượng",
            modifier = Modifier.constrainAs(videoInfo){
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(sideBar.bottom)
                end.linkTo(sideBar.start)
                width = Dimension.fillToConstraints
            }
        )
    }
}