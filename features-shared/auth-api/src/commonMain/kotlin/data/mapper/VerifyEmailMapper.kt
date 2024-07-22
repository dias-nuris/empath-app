package data.mapper

import data.model.VerifyEmailRequest
import domain.model.VerifyEmail

internal fun VerifyEmailRequest.toDomain() = VerifyEmail(
    email = this.email,
    code = this.code,
)

internal fun VerifyEmail.toData() = VerifyEmailRequest(
    email = this.email,
    code = this.code,
)