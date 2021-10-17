package ru.yamal.talanta

import grails.gorm.services.Service

@Service(User)
interface UserService {
    User save(User user)

    User get(Serializable id)

    List<User> list(Map args)

    User findName(String name)

    Long count()

    void delete(Serializable id)
}