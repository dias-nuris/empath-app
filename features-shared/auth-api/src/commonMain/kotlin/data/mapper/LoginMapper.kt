package data.mapper

import data.model.LoginRequest
import domain.model.Login

internal fun Login.toData() = LoginRequest(
    username = this.email,
    password = this.password,
)

internal fun LoginRequest.toDomain() = Login(
    email = this.username,
    password = this.password,
)