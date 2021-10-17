package ru.yamal.talanta

import java.time.ZonedDateTime

class UserDiscount {

    User user
    Discount discount
    ZonedDateTime dateExpired
    Boolean used = false
    ZonedDateTime dateUsed

    static constraints = {
    }
}
