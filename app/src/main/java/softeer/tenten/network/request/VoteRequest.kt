package softeer.tenten.network.request

data class VoteRequest(
    val userId: String,
    val optionId: Long
)
