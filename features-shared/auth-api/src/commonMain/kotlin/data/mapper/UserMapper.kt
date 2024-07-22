package data.mapper

import data.model.UserRequest
import domain.model.User

internal fun UserRequest.toDomain() = User(
    nickname = this.nickname,
    email = this.email,
    id = this.id,
    password = this.password,
    sex = this.sex,
    name = this.name,
    lastname = this.lastname,
    patronymic = this.patronymic,
    dateOfBirth = this.dateOfBirth,
    image = this.image,
)

internal fun User.toData() = UserRequest(
    nickname = this.nickname,
    email = this.email,
    id = this.id,
    password = this.password,
    sex = this.sex,
    name = this.name,
    lastname = this.lastname,
    patronymic = this.patronymic,
    dateOfBirth = this.dateOfBirth,
    image = this.image,
)