package com.somecompose.composepokeapp.data.responses

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)