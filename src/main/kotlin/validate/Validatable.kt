package validate

import io.konform.validation.ValidationResult

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.05.2023 20:34
 */

interface Validatable<T> {
    fun validate(): ValidationResult<T>
}