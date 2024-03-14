package com.example.mushtool.Forum

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.mushtool.users.usuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel : ViewModel() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("grupo_chat")
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val messages: MutableLiveData<List<ChatMessage>> by lazy {
        MutableLiveData<List<ChatMessage>>().apply {
            loadMessages()
        }
    }

    private val _currentUser: MutableStateFlow<usuarios?> = MutableStateFlow(null)
    val currentUser: StateFlow<usuarios?> get() = _currentUser
    fun messagesAsFlow(): Flow<List<ChatMessage>> = messages.asFlow()

    private val selectedMessage: MutableLiveData<ChatMessage?> = MutableLiveData(null)
    private val isReplying: MutableLiveData<Boolean> = MutableLiveData(false)

    fun clearSelectedMessage() {
        selectedMessage.value = null
    }
    fun selectMessage(message: ChatMessage?) {
        selectedMessage.value = message
        isReplying.value = message != null
    }

    fun getSelectedMessage(): MutableLiveData<ChatMessage?> {
        return selectedMessage
    }

    fun getIsReplying(): MutableLiveData<Boolean> {
        return isReplying
    }

    fun getRepliesForSelectedMessage(): List<ChatMessage> {
        val selectedMessageId = selectedMessage.value?.id
        return messages.value?.filter { it.id == selectedMessageId }
            ?.flatMap { it.replies.orEmpty() }
            ?: emptyList()
    }

    fun getSelectedMessageReplies(): List<ChatMessage> {
        val selectedMessageId = selectedMessage.value?.id
        return messages.value?.find { it.id == selectedMessageId }?.replies.orEmpty()
    }

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Puedes cargar más información del usuario desde tu base de datos u otras fuentes
            val usuario = usuarios(username = currentUser.displayName)

            // Asignar el usuario actual al StateFlow
            _currentUser.value = usuario
        }
    }

    var load: Boolean = false

    fun sendMessage(
        messageText: String,
        mushroomLocation: SerializableLocation? = null,
        selectedMushroomId: String? = null
    ) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val timestamp = System.currentTimeMillis()
            val senderId = currentUser.uid
            val senderName = currentUser.displayName.orEmpty()

            val chatMessage = ChatMessage(
                id = "",
                text = messageText,
                senderId = senderId,
                senderName = senderName,
                timestamp = timestamp,
                mushroomId = selectedMushroomId?.toLongOrNull(),
                location = mushroomLocation
            )

            if (isReplying.value == true && selectedMessage.value != null) {
                val recipientId = selectedMessage.value?.senderId.orEmpty()
                val recipientName = selectedMessage.value?.senderName.orEmpty()

                val replyMessage = ChatMessage(
                    id = "",
                    text = messageText,
                    senderId = senderId,
                    senderName = senderName,
                    recipientId = recipientId,
                    mushroomId = selectedMushroomId?.toLongOrNull(),
                    location = mushroomLocation,
                    recipientName = recipientName,
                    timestamp = timestamp
                )

                selectedMessage.value?.replies?.add(replyMessage)
                databaseReference.child(selectedMessage.value?.id.orEmpty()).setValue(selectedMessage.value)
            } else {
                val messageId = databaseReference.push().key.orEmpty()
                chatMessage.id = messageId
                databaseReference.child(messageId).setValue(chatMessage)
            }

            selectMessage(null)
        } else {
            Log.e("ChatViewModel", "No se ha seleccionado una foto o una ubicación")
        }
    }


    init {
        loadMessages()
    }
    private fun loadMessages() {

        databaseReference
            //.orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {


                    // para listar los sms
                    val messageLists = mutableListOf<ChatMessage>()
                    for (messageSnapshot in snapshot.children) {
                        val chatMessage = messageSnapshot.getValue(ChatMessage::class.java)
                        chatMessage?.let { messageLists.add(it) }
                    }
                    messages.value = messageLists

                    // para recoger los mensajes
                    val messageList = snapshot.children.mapNotNull { messageSnapshot ->
                        messageSnapshot.getValue(ChatMessage::class.java)
                    }
                    messages.value = messageList
                    load = true
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatViewModel", "Error loading messages: ${error.message}")
                }
            })
    }
}
