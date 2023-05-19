package models

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.03.2023 13:12
 */

@Serializable
@JvmInline
value class ToDoId(private val id: String){
    fun asString() = id

    companion object {
        val NONE = ToDoId("")
    }
}