package com.kn.appwallpaper.model

data class SearchedPhotos(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: ArrayList<Photo>,
    val total_results: Int
)