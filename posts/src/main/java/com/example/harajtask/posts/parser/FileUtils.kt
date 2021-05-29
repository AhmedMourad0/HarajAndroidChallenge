package com.example.harajtask.posts.parser

import android.content.Context
import java.io.InputStream

internal typealias InputStreamFactory =
            (@JvmSuppressWildcards Context) -> @JvmSuppressWildcards InputStream

internal fun openFileFromAssets(appCtx: Context): InputStream {
    return appCtx.assets.open("data.json")
}
