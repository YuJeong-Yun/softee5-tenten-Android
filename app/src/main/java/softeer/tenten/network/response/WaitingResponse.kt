package softeer.tenten.network.response

data class WaitingResponse(
    val id: Long,
    val waitingNumber: Int,
    val orderNumber: Int
)
