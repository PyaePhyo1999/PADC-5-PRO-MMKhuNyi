package com.example.acer.kuunyi.data.vos

import com.google.gson.annotations.SerializedName

/**
 * Created by Acer on 8/2/2018.
 */
class RequiredSkillVO  {
    @SerializedName("skillName")
    var skillName: String? = null

    @SerializedName("skillId")
    var skillId: Int? = null
}