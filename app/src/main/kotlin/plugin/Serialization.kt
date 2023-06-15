package ru.shvets.todolist.app.plugin

import io.ktor.serialization.gson.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import ru.shvets.todolist.api.apiMapper

fun Application.configureSerialization() {
    install(ContentNegotiation) {
//        json(Json { // JsonBuilder
//            ignoreUnknownKeys = true
//            isLenient = true
//            prettyPrint = true
//        })

        jackson {
            setConfig(apiMapper.serializationConfig)
            setConfig(apiMapper.deserializationConfig)
        }

        gson {
            setPrettyPrinting()
        }
    }
}