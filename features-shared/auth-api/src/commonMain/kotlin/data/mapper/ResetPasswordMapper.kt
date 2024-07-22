package data.mapper

import data.model.ResetPasswordRequest
import domain.model.ResetPassword

internal fun ResetPasswordRequest.toDomain() = ResetPassword(
    email = this.email,
    password = this.password,
)

internal fun ResetPassword.toData() = ResetPasswordRequest(
    email = this.email,
    password = this.password,
)