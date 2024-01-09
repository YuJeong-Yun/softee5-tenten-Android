package softeer.tenten.network.response

data class ReviewListResponse(
    val nickname: String,
    val destination: String,
    val date: String,
    val content: String,
    val image: String
)
