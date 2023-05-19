package helpers

import repositories.base.BaseError

//val errorEmptyId = BaseError(
//    code = "id-empty",
//    group = "cruds",
//    field = "id",
//    description = "id must not be null or blank"
//)

val errorEmptyId = error {
    code = "id-empty"
    group = "cruds"
    field = "id"
    description = "id must not be null or blank"
}

//val errorNotFound = BaseError(
//    code = "not-found",
//    group = "cruds",
//    field = "id",
//    description = "not found",
//)

val errorNotFound = error {
    code = "not-found"
    group = "cruds"
    field = "id"
    description = "not found"
}

//val errorSave = BaseError(
//    code = "not-save",
//    group = "cruds",
//    field = "row",
//    description = "not save a new item",
//)

val errorSave = error {
    code = "not-save"
    group = "cruds"
    field = "row"
    description = "not save a new item"
}

//fun err(block: (BaseError) -> Unit): BaseError {
//    val er = BaseError()
//    block(er)
//    return er
//}

fun error(block: BaseError.()-> Unit): BaseError {
    val error = BaseError()
    error.block()
    return error
}