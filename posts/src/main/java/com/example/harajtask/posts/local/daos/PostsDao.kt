package com.example.harajtask.posts.local.daos

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.*
import com.example.harajtask.posts.local.PostsContract
import com.example.harajtask.posts.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class PostsDao {

    @Query("""SELECT
            ${PostsContract.Post.COL_ID},
            ${PostsContract.Post.COL_TITLE},
            ${PostsContract.Post.COL_USERNAME},
            ${PostsContract.Post.COL_THUMB_URL},
            ${PostsContract.Post.COL_COMMENT_COUNT},
            ${PostsContract.Post.COL_CITY},
            ${PostsContract.Post.COL_DATE},
            ${PostsContract.Post.COL_BODY}
        FROM
            ${PostsContract.Post.TABLE_NAME}
    """)
    abstract fun findSimplePosts(): PagingSource<Int, PostEntity>

    @Query("""SELECT
            ${PostsContract.Post.COL_ID},
            ${PostsContract.Post.COL_TITLE},
            ${PostsContract.Post.COL_USERNAME},
            ${PostsContract.Post.COL_THUMB_URL},
            ${PostsContract.Post.COL_COMMENT_COUNT},
            ${PostsContract.Post.COL_CITY},
            ${PostsContract.Post.COL_DATE},
            ${PostsContract.Post.COL_BODY}
        FROM
            ${PostsContract.Post.TABLE_NAME}
    """)
    @VisibleForTesting
    internal abstract fun findAllPosts(): Flow<List<PostEntity>>

    @Query("""SELECT
            ${PostsContract.Post.COL_ID},
            ${PostsContract.Post.COL_TITLE},
            ${PostsContract.Post.COL_USERNAME},
            ${PostsContract.Post.COL_THUMB_URL},
            ${PostsContract.Post.COL_COMMENT_COUNT},
            ${PostsContract.Post.COL_CITY},
            ${PostsContract.Post.COL_DATE},
            ${PostsContract.Post.COL_BODY}
        FROM
            ${PostsContract.Post.TABLE_NAME}
        WHERE
            ${PostsContract.Post.COL_ID} = :id
    """)
    abstract suspend fun findPost(id: Long): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun bulkInsert(posts: List<PostEntity>)

    @Query("DELETE FROM ${PostsContract.Post.TABLE_NAME}")
    protected abstract suspend fun deleteAll()

    @Transaction
    open suspend fun replaceAllTransaction(
        posts: List<PostEntity>
    ) {
        deleteAll()
        bulkInsert(posts)
    }
}
