package com.example.mushtool.Forum

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class ChatMessage(
    var id :  String = "",
    val text: String = "",
    val senderId: String = "",  // ID del remitente
    val senderName: String = "",  // Nombre del remitente
    val recipientId: String = "",  // ID del destinatario (en el caso de respuestas)
    val recipientName: String = "",
    val mushroomId: Long? = null, //ID de las fotos de las setas para poder mostrarlas
    var timestamp: Long = System.currentTimeMillis(),
    val location: SerializableLocation? = null, //Localización de las setas para poder compartir la ubiacación
    val replies: MutableList<ChatMessage> = mutableListOf(),
) {

    constructor() : this("", "")
    fun getFormattedTime(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}

data class SerializableLocation(
    val latitude: Double,
    val longitude: Double
) {
    // Constructor sin argumentos necesario para Firebase
    constructor() : this(0.0, 0.0)
}