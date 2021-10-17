package ru.yamal.talanta

class BootStrap {

    RoleService roleService
    UserService userService
    UserRoleService userRoleService
    StudyService studyService
    InterestService interestService
    def springSecurityService

    def init = { servletContext ->
        List<String> authorities = ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_PARTNER']
        authorities.each { authority ->
            if ( !roleService.findByAuthority(authority) ) {
                roleService.save(authority)
            }
        }

        // Добавление учебных заведений
        Study study = new Study(name: 'Ямальский университет')
        studyService.save(study)

//        // Добаление интересов
//        Interest interest = new Interest(name: 'Игра в кальмара')
//
//        // добавление пользователей
//        User user = new User(username: 'admin', password: 'admin')
//
//        interestService.save(interest)
//
//
//        userService.save(user)
//        userRoleService.save(admin, roleService.findByAuthority('ROLE_ADMIN'))
    }

    def destroy = {
    }
}
