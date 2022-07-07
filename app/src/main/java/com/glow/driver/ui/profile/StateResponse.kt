package com.glow.driver.ui.profile

import com.google.gson.annotations.SerializedName

data class StateResponse(

	@field:SerializedName("result")
	val result: ArrayList<ResultItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)


