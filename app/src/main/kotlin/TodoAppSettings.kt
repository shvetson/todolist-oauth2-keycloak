package ru.shvets.todolist.app

data class TodoAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: TodoCorSettings,
    val processor: TodoProcessor = TodoProcessor(settings = corSettings),
)
