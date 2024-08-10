package com.nhatvm.toptop.ui.following

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.R
import com.nhatvm.toptop.design.TikTokVideoPlayer
import com.nhatvm.toptop.ui.theme.ToptopTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun Following(){

    val pagerState = rememberPagerState()
    val cardWidth = (LocalConfiguration.current.screenWidthDp * 2 / 3) - 24
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(text = "Tác giả nổi bậc", style =  MaterialTheme.typography.h4.copy(color = Color.White))
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Follow tài khoản để xem video mới nhất của họ tại đây", style =  MaterialTheme.typography.body1.copy(color = Color.Gray))
        Spacer(modifier = Modifier.height(36.dp))
        
        HorizontalPager(modifier = Modifier
            .fillMaxSize()
            .aspectRatio(0.9f),
            state = pagerState,
            pageCount = 10,
            pageSpacing = 36.dp,
            contentPadding = PaddingValues(start = 24.dp),
            pageSize = PageSize.Fixed(cardWidth.dp),
            )
        {page ->
            Card(modifier = Modifier
                .width(cardWidth.dp)
                .aspectRatio(0.7f)
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction).absoluteValue
                    scaleY = lerp(
                        start = 0.7f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .clip(RoundedCornerShape(16.dp))
            ) {

            }
            CardFollowing(name = "Anh Kiệt $page", nickname = "kietloki121 $page", onFollow = {  }) {
                
            }
        }
    }
}
@UnstableApi
@Composable
fun CardFollowing(
    modifier: Modifier = Modifier,
    name: String,
    nickname: String,
    onFollow: () -> Unit,
    onClose: () -> Unit
){
    val videoPlayer = ExoPlayer.Builder(LocalContext.current).build()
    videoPlayer.repeatMode = REPEAT_MODE_ALL
    videoPlayer.playWhenReady = true
    videoPlayer.prepare()
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))) {
        val (videoIntro,btnClose,imgAvatar,tvName,tvNickName,btnFollow) = createRefs()
        TikTokVideoPlayer(player = videoPlayer, modifier = modifier.constrainAs(videoIntro) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })
        IconButton(onClick = onClose, modifier = Modifier
            .constrainAs(btnClose) {
                top.linkTo(parent.top, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
            }
            .size(16.dp)) {
            Icon(Icons.Sharp.Close, contentDescription = "Close icon", tint = Color.White)
        }
        Button(onClick = onFollow, modifier = Modifier
            .constrainAs(btnFollow) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }
            .padding(
                horizontal = 48.dp,
                vertical = 12.dp
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE94359),
                contentColor = Color.White
            )
        ) {
            Text(text = "Follow", style = MaterialTheme.typography.body1.copy(color = Color.White))
        }

        Text(text = nickname,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray),
            modifier = Modifier.constrainAs(tvNickName){
            bottom.linkTo(btnFollow.top, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Text(text = name,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier.constrainAs(tvName){
                bottom.linkTo(tvNickName.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        AvatarFollow(modifier = Modifier.constrainAs(imgAvatar){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(tvName.top, margin = 8.dp)
            top.linkTo(btnClose.bottom)
        })
    }

    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.one)
    val mediaItem = MediaItem.fromUri(uri)
    videoPlayer.setMediaItem(mediaItem)
    SideEffect {
        videoPlayer.play()
    }
}

@Composable
fun AvatarFollow(modifier: Modifier = Modifier){
    val sizeavatar =  LocalConfiguration.current.screenWidthDp * 0.15
    Image(
        painter = painterResource(id = R.drawable.ava2),
        contentDescription = "avatar",
        modifier = Modifier
            .size(sizeavatar.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(color = Color.White, shape = CircleShape, width = 2.dp)
            .clip(CircleShape)
    )

}
@UnstableApi
@Preview
@Composable
fun AvatarFollowPreview(){
    ToptopTheme {

    }
}