package data.mapper

import data.model.CreateUserRequest
import domain.model.CreateUser

internal fun CreateUserRequest.toDomain() = CreateUser(
    nickname = this.nickname,
    email = this.email,
    password = this.password,
)

internal fun CreateUser.toData() = CreateUserRequest(
    nickname = this.nickname,
    email = this.email,
    password = this.password,
)

