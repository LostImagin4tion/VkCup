package io.lostimagin4tion.vkcup.ui.components.animations

import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

/**
 * [AnimatedGif] - animation that is created from GIF file using Coil
 *
 * @author Egor Danilov
 */
@Composable
fun AnimatedGif(
    @DrawableRes gifRes: Int,
    context: Context,
    modifier: Modifier = Modifier
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val imageRequest = ImageRequest.Builder(context)
        .data(data = gifRes)
        .apply { size(Size.ORIGINAL) }
        .build()

    Image(
        painter = rememberAsyncImagePainter(
            model = imageRequest,
            imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier
    )
}