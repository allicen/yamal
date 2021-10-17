#include <stdio.h>
#include <syslog.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/param.h>
#include <sys/resource.h>
#include <locale.h>

main(int argc, char **argv) {
    int fd; 
    struct rlimit flim;
    char file_name_backend[] = "backend.pid";
    FILE * file;

    setlocale(LC_ALL, "Rus");

    if (getpid() != 1) {
        // игнориррование сигналов ввода/вывода на терминал фонового процесса
        signal(SIGTTOU, SIG_IGN);
        signal(SIGTTIN, SIG_IGN);
        signal(SIGTSTP, SIG_IGN);

        if (fork() != 0) {
            exit(0); // родитель заканчивает работу 
        }

        setsid(); 
    }
    
    // закрыть открытые файлы
    getrlimit(RLIMIT_NOFILE, &flim);
    for (fd = 0; fd < flim.rlim_max; fd++) {
        close(fd);
    }

    openlog("Deploy daemon backend", LOG_PID | LOG_CONS, LOG_DAEMON);
    syslog(LOG_INFO, "Start daemon backend....");

    // проверка существования процесса
    file = fopen(file_name_backend, "rb+");

    if (!file) {
        file = fopen(file_name_backend, "w+");
        if (fprintf(file, "%d\n", getpid())) {
            syslog(LOG_INFO, "Создан файл backend.pid");
        } else {
            syslog(LOG_ERR, "Ошибка! Файл backend.pid не был создан");
            exit(1);
        }

        fclose(file);
    }

    closelog();

    // смена каталога для запуска бекенда
     chdir("/var/www/yamal/backend/");

    // запуск бекенда
//    system("grails run-app");
     system ("java -Dgrails.env=prod -jar /var/www/yamal/backend/build/libs/ru.yamal.talanta-0.1.war");

    // смена каталога на корневой
    chdir("/");

    while (1) pause(); 
}