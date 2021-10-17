package ru.yamal.talanta

import java.time.ZonedDateTime

class Event {

    String name
    String city
    String address
    ZonedDateTime date
    EventCategory category
    EventType type

    static constraints = {
    }
}
