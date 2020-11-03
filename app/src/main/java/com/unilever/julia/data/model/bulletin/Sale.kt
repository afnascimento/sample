package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class Sale(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("estimated_value")
    var estimatedValue: Double? = 0.0,
    @SerializedName("percentage")
    var percentage: Double? = 0.0,
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("update_informations")
    var updateInformations: UpdateInformations? = UpdateInformations(),
    @SerializedName("result_informations")
    var resultInformations: List<ResultInformation>? = listOf()
)