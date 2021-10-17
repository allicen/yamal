package ru.yamal.talanta

class UserDetails {

    City city

    Date birthday

    // Учебное заведение
    Study studyPlace

    // Привязка к пользователю
    static belongsTo = [user: User]

    static constraints = {
    }
}
