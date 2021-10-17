package ru.yamal.talanta

/**
 * Категория мероприятия, например, Киберспорт
 * */

class EventCategory {

    String name
    String description

    static constraints = {
        description nullable: true
    }
}
