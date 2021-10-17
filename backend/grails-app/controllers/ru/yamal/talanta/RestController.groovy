package ru.yamal.talanta

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class RestController {

    def springSecurityService
    UserRoleService userRoleService
    UserService userService
    RoleService roleService

    def index() {
    }

    /**
     * Регистрация пользователя
     * */
    @Secured(['ROLE_ANONYMOUS'])
    def signOn() {

        def out = [result: '', message: '']

        Integer age
        Study studyPlace

        if (params.age) {
            age = Integer.parseInt(params.age)
        }

        if (params.studyPlace) {
            studyPlace = Study.get(params.studyPlace)
        }

        def user = new User(username: params.username, password: params.password)

        if (User.findByUsername(params.username)) {
            out.result = 'error'
            out.message = 'Пользователь с таким логином уже зарегистрирован'
            render(out as JSON)
            return
        }

        def userDetails = new UserDetails(firstName: params.firstName, lastName: params.lastName,
                age: params.age, studyPlace: params.studyPlace, user: user)

        try {
            userService.save(user)
            userDetails.save()
            userRoleService.save(user, roleService.findByAuthority('ROLE_USER'))
            out.result = 'success'
            out.message = 'Регистрация прошла успешно'
        } catch (e) {
            out.result = 'error'
            out.message = 'При регистрации возникла ошибка'
            log.error(out.message, e)
        }

        render(out as JSON)
    }
}
