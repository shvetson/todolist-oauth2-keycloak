package repositories.base

import kotlinx.serialization.Serializable

@Serializable
data class BaseError(
    var code: String = "",
    var group: String = "",
    var field: String = "",
    var description: String = "",
)