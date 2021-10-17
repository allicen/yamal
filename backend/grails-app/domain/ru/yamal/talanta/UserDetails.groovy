package ru.yamal.talanta

class UserDetails {

    String city

    String birthday

    String email

    // Привязка к пользователю
    static belongsTo = [user: User]

    static constraints = {
    }
}
