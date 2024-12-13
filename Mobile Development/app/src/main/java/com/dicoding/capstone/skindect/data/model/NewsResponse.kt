package com.dicoding.capstone.skindect.data.model

import com.google.gson.annotations.SerializedName
data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<OGS8pWLl3rnbNgRzzItem>,

	@field:SerializedName("status")
	val status: String? = null
)

data class OGS8pWLl3rnbNgRzzItem(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
