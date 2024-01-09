package softeer.tenten.network.response

data class BaseResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T
)
