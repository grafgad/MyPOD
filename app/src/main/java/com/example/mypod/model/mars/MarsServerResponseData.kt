package com.example.mypod.model.mars

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
//    @field:SerializedName("image") val image: String?,
//    @field:SerializedName("date") val date: String?,
//    @field:SerializedName("caption") val caption: String?,
//    @field:SerializedName("centroid_coordinates") val centroidCoordinates: String?,
//    @field:SerializedName("dscovr_j2000_position") val dscovrJ2000Position: String?,
//    @field:SerializedName("lunar_j2000_position") val lunarJ2000Position: String?,
//    @field:SerializedName("sun_j2000_position") val sunJ2000Position: String?,
//    @field:SerializedName("attitude_quaternion") val attitudeQuaternion: String?,



    @field:SerializedName("id") val id: String?,
    @field:SerializedName("sol") val sol: String?,
    @field:SerializedName("img_src") val img_src: String?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("rover_id") val rover_id: String?,
    @field:SerializedName("full_name") val full_name: String?,
    @field:SerializedName("earth_date") val earth_date: String?
)

