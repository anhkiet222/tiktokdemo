package com.nhatvm.toptop.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.following.Following
import com.nhatvm.toptop.ui.foryou.ListVideoForYou
import com.nhatvm.toptop.ui.theme.ToptopTheme
import com.nhatvm.toptop.ui.user.Profile
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun MainScreen(){
    val coroutineScope = rememberCoroutineScope()
    val paperState = rememberPagerState(initialPage = 1)
    val scrollPage: (Boolean) -> Unit ={ isForU ->
        val index = if(isForU) 1 else 0
        coroutineScope.launch {
            paperState.scrollToPage(index)
        }
    }
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (tabContentView, body) = createRefs()
        HorizontalPager(modifier = Modifier.constrainAs(body){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, pageCount = 3, state = paperState) {page ->
            when(page){
                0 -> Following()
                1 -> Profile()
                else -> ListVideoForYou()
            }
        }
        TabcontentView(isTabSeletedIndex = paperState.currentPage, onSelectedTab = {
            isForU ->
        }, modifier = Modifier.constrainAs(tabContentView){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
    }
}

@Composable
fun TabContentItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isForU: Boolean,
    onSelectedTab: (isForU: Boolean) -> Unit
){
    val alpha = if(isSelected) 1f else 0.6f
    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelectedTab(isForU) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = alpha)))
        Spacer(modifier = Modifier.height(8.dp))
        if(isSelected){
            Divider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier.width(24.dp))
        }
    }
}
@Composable
fun TabcontentView(
    modifier: Modifier = Modifier,
    isTabSeletedIndex: Int,
    onSelectedTab: (isForU: Boolean) -> Unit
){
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tabContent, imgSearch) = createRefs()
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            TabContentItem(
                title = "Following",
                isSelected = isTabSeletedIndex == 0,
                isForU = false,
                onSelectedTab = onSelectedTab)
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItem(
                title = "For You",
                isSelected = isTabSeletedIndex == 1,
                isForU = true,
                onSelectedTab = onSelectedTab)
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(imgSearch){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Icon search",
                Modifier.size(24.dp),
                tint = Color.White)
        }
    }
}

@Preview
@Composable
fun TabContenItemViewPreviewSelect(){
    ToptopTheme {
        TabContentItem(title = "For You", isSelected = true, isForU = true, onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContenItemViewPreviewUnselect(){
    ToptopTheme {
        TabContentItem(title = "Following", isSelected = false, isForU = false, onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentPreView(){
    ToptopTheme {
        TabcontentView(isTabSeletedIndex = 1){

        }
    }
}