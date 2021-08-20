package com.somecompose.composepokeapp.data.response

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)