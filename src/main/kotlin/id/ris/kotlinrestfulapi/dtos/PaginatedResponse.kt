package id.ris.kotlinrestfulapi.dtos

data class PaginatedResponse(
    val data: List<Any>,
    val total: Int,
    val page: Int,
    val lastPage: Int
)