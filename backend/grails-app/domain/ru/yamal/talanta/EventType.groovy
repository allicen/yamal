package ru.yamal.talanta

/**
 * Тип мероприятия, например, Соревнования
 * */

class EventType {

    String name
    String description

    static constraints = {
        description nullable: true
    }
}
