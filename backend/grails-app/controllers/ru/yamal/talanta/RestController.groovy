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

        if (!params.email) {
            out.result = 'error'
            out.message = 'Email обязателен для заполнения'
            render(out as JSON)
            return
        }

        if (User.findByUsername(params.email)) {
            out.result = 'error'
            out.message = 'Пользователь с таким логином уже зарегистрирован'
            render(out as JSON)
            return
        }

        def user = new User(username: params.email, password: params.password, fullName: params.fullName)
        def userDetails = new UserDetails(city: params.city, birthday: params.birthday, userEmai: params.email, user: user)

        if (User.findByUsername(params.email)) {
            out.result = 'error'
            out.message = 'Пользователь с таким логином уже зарегистрирован'
            render(out as JSON)
            return
        }

        try {
            userService.save(user)
            userDetails.save(flush: true)
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
