package softeer.tenten.network.response

data class VoteInformationResponse(
    val voteResults: List<VoteResult>,
    val myVoteNumber: Long
)

data class VoteResult(
    val id: Long,
    val name: String,
    val result: Int
)
