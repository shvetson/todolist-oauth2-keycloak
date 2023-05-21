package ru.shvets.todolist.app.authentication.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 09:59
 */

class SHA256HashingService: Hashing {
    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(
            hash = hash,
            salt = saltAsHex,
        )
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt + value) == saltedHash.hash
    }
}