package com.example.harajtask.posts.parser

import android.content.Context
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.posts.di.InternalApi
import com.example.harajtask.posts.parser.model.JsonPost
import com.example.harajtask.posts.repository.dependencies.RemoteDataSource
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

//A parser isn't a remote datasource,
// but in real scenarios a real remote datasource is used here
@Reusable
internal class ParserImpl @Inject constructor(
    private val appCtx: Context,
    @InternalApi private val inputStream: InputStreamFactory
) : RemoteDataSource {

    private val format: Json = Json {  }

    override fun findPosts(): Flow<List<Post>> {
        return flowOf(parseJson(readJsonFromStream()).map(JsonPost::toPost))
            .flowOn(Dispatchers.IO)
    }

    private fun parseJson(json: String): List<JsonPost> {
        return format.decodeFromString(ListSerializer(JsonPost.serializer()), json)
    }

    private fun readJsonFromStream(): String {
        return inputStream.invoke(appCtx)
            .bufferedReader()
            .lineSequence()
            .joinToString("\n")
    }
}
