package softeer.tenten.vote

data class VoteItemModel(
    val id: Long,
    val location: String,
    var checked: Boolean = false
)
