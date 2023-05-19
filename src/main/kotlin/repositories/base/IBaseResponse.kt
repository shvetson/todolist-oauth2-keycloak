package repositories.base

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 22:59
 */

interface IBaseResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<BaseError>
}