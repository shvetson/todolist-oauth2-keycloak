package ru.shvets.todolist.api

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import ru.shvets.api.v1.models.IRequest
import ru.shvets.api.v1.models.IResponse

val apiMapper = JsonMapper.builder().run {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
//    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    build()
}

fun apiRequestSerialize(request: IRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiRequestDeserialize(json: String): T =
    apiMapper.readValue(json, IRequest::class.java) as T

fun apiResponseSerialize(response: IResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IResponse::class.java) as T