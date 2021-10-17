package ru.yamal.talanta

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class RestController {

    def springSecurityService
    UserRoleService userRoleService
    UserService userService
    RoleService roleService
    UserDetailService userDetailService
    EventCategoryService eventCategoryService

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
        def userDetails = new UserDetails(city: params.city, birthday: params.birthDay, userEmail: params.email, user: user)

        if (User.findByUsername(params.email)) {
            out.result = 'error'
            out.message = 'Пользователь с таким логином уже зарегистрирован'
            render(out as JSON)
            return
        }

        try {

            userService.save(user)
            userDetailService.save(userDetails)
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


    /**
     * Получение категорий
     * */
    @Secured(['ROLE_ANONYMOUS'])
    def getEventCategory() {
        render (eventCategoryService.list(params).collect {[id: it.id, name: it.name]} as JSON)
    }


    /***
     * Получение вопросов для вывода пользователю
     * */
    @Secured(['ROLE_ANONYMOUS'])
    def getQuestions() {

        def result = [
                [ eventCategory: 'Программирование', questions: [
                        [name: 'Вы интересуетесь этой темой или серьезно занимаетесь?', type: 'checkbox', answers: [
                                'Серьезно занимаюсь',
                                'Больше развлекаюсь',
                                'Нечто среднее'
                        ]],
                        [name: 'Скидки и бонусы какого рода Вам были бы интересны', type: 'checkbox', answers: [
                                'Обучающие курсы',
                                'Скидки на оборудование'
                        ]],
                        [name: 'Что Вы думаете о развитии программирования в ЯНАО? Что можно сделать лучше? Как поддержать программистов?', type: 'textarea', answers: [
                                'Развит слабее, чем в крупных городах. Чаще организовывать соревнования, собирать команды и отправлять на большие соревнования. Нужны кружки в школе'
                        ]]
                ]],
                  [ eventCategory: 'Киберспорт', questions: [
                          [name: 'Вы интересуетесь этой темой или серьезно занимаетесь?', type: 'textarea', answers: [
                                  'Серьезно занимаюсь',
                                  'Больше развлекаюсь',
                                  'Нечто среднее'

                          ]],
                          [name: 'Скидки и бонусы какого рода Вам были бы интересны', type: 'checkbox', answers: [
                                  'Обучающие курсы',
                                  'Скидки на оборудование'
                          ]],
                          [name: 'Что Вы думаете о развитии киберспорта в ЯНАО? Что можно сделать лучше? Как поддержать киберспортсменов?', type: 'checkbox', answers: [
                                  'Мало развит, люди мало что о нем знают. Собрать кружки и чаще рассказывать об этом'
                          ]]
                  ]
                ]
        ]

        render(result as JSON)
    }

    /**
     * Получение статистики
     * */
    def getStat() {

    }
}
