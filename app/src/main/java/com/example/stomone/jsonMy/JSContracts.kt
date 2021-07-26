package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSContracts(
    @SerializedName("ContractNumber") val contractNumber : String,
    @SerializedName("DateOfContractCreation") val dateOfContractCreation : String,
    @SerializedName("ContractCompleted") val contractCompleted : Int,
    @SerializedName("Doctor") val doctor : String
)
