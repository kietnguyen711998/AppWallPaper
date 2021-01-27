package com.kn.appwallpaper.api

import android.util.Log
import androidx.lifecycle.LiveData
import com.kn.appwallpaper.model.Category
import com.kn.appwallpaper.model.SearchedPhotos
import com.kn.appwallpaper.model.TrendingPhotos
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object PexelsRepository {
    var job: CompletableJob? = null
    private const val TAG = "PexelsRepo"

    //return a list of categories
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                "https://images.pexels.com/photos/545008/pexels-photo-545008.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Street Art"
            ),
            Category(
                "https://images.pexels.com/photos/704320/pexels-photo-704320.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Wild Life"
            ),
            Category(
                "https://images.pexels.com/photos/34950/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Nature"
            ),
            Category(
                "https://images.pexels.com/photos/466685/pexels-photo-466685.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "City"
            ),
            Category(
                "https://images.pexels.com/photos/1434819/pexels-photo-1434819.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                "Motivation"
            ),
            Category(
                "https://images.pexels.com/photos/2116475/pexels-photo-2116475.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Bikes"
            ),
            Category(
                "https://images.pexels.com/photos/1149137/pexels-photo-1149137.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Cars"
            )
        )
    }

    //get list of the trending
    fun getTrendingPhotos(
        pages_number: Int
    ): LiveData<TrendingPhotos> {
        Log.d(TAG, "getting trending photos ")
        job = Job()
        return object : LiveData<TrendingPhotos>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        try {
                            val trendingPhotos: TrendingPhotos =
                                RetrofitBuilder.pexelsApiService.getTrendingPhotos(pages_number)

                            withContext(Main) {
                                //set value on the main thread
                                value = trendingPhotos
                                Log.d(TAG, trendingPhotos.toString())
                                theJob.complete()
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "onActiveTrendingPhotos: ${e.message.toString()}")
                        }
                    }
                }
            }
        }
    }

    //search photo
    //get a list of the trending photos

    fun getSearchPhotos(
        query: String,
        pages_number: Int
    ): LiveData<SearchedPhotos>{
        Log.d(TAG, "getSearchPhotos: ")
        job = Job()
        return object : LiveData<SearchedPhotos>(){
            override fun onActive() {
                super.onActive()
                job?.let {theJob ->
                    CoroutineScope(IO + theJob).launch {
                        try {
                            val searchedPhotos: SearchedPhotos= RetrofitBuilder.pexelsApiService.searchPhotos(query,pages_number)
                            withContext(Main){
                                //set value on the main thread
                                value = searchedPhotos
                                Log.d(TAG, searchedPhotos.toString())
                                theJob.complete()
                            }
                        }catch (e:Exception){
                            Log.d(TAG, "onActiveSearchPhotos: ${e.message.toString()}")
                        }
                    }
                }
            }
        }

    }

}