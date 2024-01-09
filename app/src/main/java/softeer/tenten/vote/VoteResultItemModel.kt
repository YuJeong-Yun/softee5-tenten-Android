package softeer.tenten.vote

data class VoteResultItemModel(
    val location: String,
    val checked: Boolean,
    var rate: Int
)
