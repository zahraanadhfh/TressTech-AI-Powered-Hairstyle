package com.example.tresstech.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
