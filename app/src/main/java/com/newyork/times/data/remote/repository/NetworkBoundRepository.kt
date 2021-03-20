package com.newyork.times.data.remote.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.newyork.times.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

/**
Created by Umer Khawaja on 19,March,2021
Dubai, UAE.
 */
abstract class NetworkBoundRepository<RESULT, REQUEST>() {

    fun asFlow() = flow<State<RESULT>> {

        // Emit Loading State
        emit(State.loading())

        try {
            // Emit Database content first
            //emit(State.success(fetchFromLocal().first()))

            // Fetch latest posts from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val remotePosts = apiResponse?.body()

            // Check for response validation
            if (apiResponse?.isSuccessful!! && remotePosts != null) {
                // Save posts into the persistence storage
                saveRemoteData(remotePosts)
            } else {
                // Something went wrong! Emit Error state.
                emit(State.error(apiResponse?.message()))
            }

        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(State.error("Network error! Can't get latest weather report."))
            e.printStackTrace()
        }

        // Retrieve posts from persistence storage and emit
        emitAll(fetchFromLocal().map {
            State.success<RESULT>(it)
        })
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST?>?
}