package com.example.stomone.recyclerContracts

import java.io.Serializable

data class ContractItem(
    var contractTitle : String,
    var contractData : String,
    var contractCheckBox : Boolean,
    var contractDoctor : String
): Serializable
