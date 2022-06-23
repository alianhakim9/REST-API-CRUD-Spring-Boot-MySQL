package id.ris.kotlinrestfulapi.dtos

data class BaseResponse<T>(
    val message: String,
    val data: T
)
