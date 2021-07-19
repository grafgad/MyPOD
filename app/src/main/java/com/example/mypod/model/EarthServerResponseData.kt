package com.example.mypod.model

import com.google.gson.annotations.SerializedName

data class EarthServerResponseData(
    @field:SerializedName("image") val image: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("centroid_coordinates") val centroidCoordinates: String?,
    @field:SerializedName("dscovr_j2000_position") val dscovrJ2000Position: String?,
    @field:SerializedName("lunar_j2000_position") val lunarJ2000Position: String?,
    @field:SerializedName("sun_j2000_position") val sunJ2000Position: String?,
    @field:SerializedName("attitude_quaternion") val attitudeQuaternion: String?
)
