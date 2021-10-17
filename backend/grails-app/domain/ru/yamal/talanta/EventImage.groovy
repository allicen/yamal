package ru.yamal.talanta

class EventImage {

    // байты
    byte[] image

    // тип изображения
    String contentType

    static belongsTo = [event: Event]

    static constraints = {
    }

    static mapping = {
        image sqlType: 'longblob'
    }
}
