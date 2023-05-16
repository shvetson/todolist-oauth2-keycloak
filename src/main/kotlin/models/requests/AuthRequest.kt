package ru.shvets.todolist.models.requests

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable
import ru.shvets.todolist.validate.Validatable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:39
 */

@Serializable
data class AuthRequest(
    val username: String,
    val password: String,
) : Validatable<AuthRequest> {

    override fun validate(): ValidationResult<AuthRequest> = Validation<AuthRequest> {
        AuthRequest::username required {
            pattern(".+@.+\\..+") hint "invalid email address"
        }
        AuthRequest::password required {
            minLength(8)
            maxLength(40)
        }
    }(this)
}