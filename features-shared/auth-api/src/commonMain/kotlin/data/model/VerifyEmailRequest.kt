package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailRequest(
    @SerialName("email") val email: String,
    @SerialName("code") val code: String,
)