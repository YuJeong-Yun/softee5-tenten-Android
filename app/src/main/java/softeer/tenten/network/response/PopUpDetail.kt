package softeer.tenten.network.response

data class PopUpDetail(
    val id: Long,
    val category: String,
    val brand: String,
    val title: String,
    val destination: List<String>,
    val duration: String,
    val introduction: String,
    val tags: List<String>,
    val status: Integer,
    val capacity: Integer,
    val carType: String,
    val contentImage: String,
    val otherImage: String,
    val time: String,
    val images: List<String>,
)

