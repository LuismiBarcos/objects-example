package org.luismi.objects.example.validator

/**
 * @author Luis Miguel Barcos
 */
typealias Validator<T> = (T) -> Result<T>

// A: type to validate
fun <A> validate(with: (A) -> Boolean): (A) -> A? = { it.takeIf(with) }

//(A) -> A? is the validate function that we want to extend
fun <A>((A) -> A?).orElseFail(with: Exception): Validator<A> = {
    a -> this(a)?.let { Result.success(a) } ?: Result.failure(with)
}