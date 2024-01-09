package softeer.tenten.network.response

data class PopUpList(
    val id: Long,
    val category: String,
    val brand: String,
    val title: String,
    val destinations: List<String>,
    val duration: String,
    val image: String,
    val status: Int,
)