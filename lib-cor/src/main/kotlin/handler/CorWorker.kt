package ru.shvets.todolist.lib.cor.handler

import ru.shvets.todolist.lib.cor.CorDslMarker
import ru.shvets.todolist.lib.cor.ICorExec
import ru.shvets.todolist.lib.cor.ICorWorkerDsl

class CorWorker<T>(
    title: String,
    description: String = "",

    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},

    private val blockHandle: suspend T.() -> Unit = {},
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {

    override suspend fun handle(context: T) = context.blockHandle()
}

@CorDslMarker
class CorWorkerDsl<T> : CorExecDsl<T>(), ICorWorkerDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorWorker(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )
}
