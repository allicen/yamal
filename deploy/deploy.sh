#!/bin/bash

pidFileBackend="/var/www/yamal/deploy/backend.pid"
pidFileFrontend="/var/www/yamal/deploy/frontend.pid"

daemonBackendFileC="/var/www/yamal/deploy/daemonBackend.c"
daemonBackendFile="/var/www/yamal/deploy/daemonBackend"

daemonFrontendFileC="/var/www/yamal/deploy/daemonFrontend.c"
daemonFrontendFile="/var/www/yamal/deploy/daemonFrontend"

warFile="/var/www/yamal/backend/build/libs/ru.yamal.talanta-0.1.war"


killProcess() {

    local procPid
    local re='^[0-9]+$'

    # проверяем существование файла перед запуском
    if [ -f "$pidFileBackend" ]; then
        procPid=$(cat "$pidFileBackend")

        # Убить процесс и всех потомков
        kill -- -$(ps -o pgid= $procPid | grep -o [0-9]*)

        rm "$pidFileBackend"
    fi

    # проверяем существование файла перед запуском
    if [ -f "$pidFileFrontend" ]; then
        procPid=$(cat "$pidFileFrontend")

        # Убить процесс и всех потомков
        kill -- -$(ps -o pgid= $procPid | grep -o [0-9]*)

        rm "$pidFileFrontend"
    fi
}


case "$1" in
    start)
        echo "Starting the process..."
        echo "Project build started...."

        # удалить файл сборки
        if [ -f "$warFile" ]; then 
            rm $warFile
            echo "Built file deleted successfully"
        fi


        #########################################################
        #                                                       #
        #    Работает на новых версиях Grails (например, 4.0.2) #
        #                                                       #
        #########################################################

        cd "../backend"

        grails package
        if [ -f "$warFile" ]; then 
            echo "Project was successfully built"
        else 
            echo "Project is not built. Exit."
            exit 1
        fi


        #########################################################
        #                                                       #
        #                    Собираем React                     #
        #                                                       #
        #########################################################

        cd "../talanta"

        npm install
        npm run build
#        npm install -g serve


        #########################################################
        #                                                       #
        #                    Запускаем проект                   #
        #                                                       #
        #########################################################

        cd "../deploy"

        killProcess

        # создать файл для бекенда
        if [ -f $daemonBackendFileC ]; then
            cc -o daemonBackend $daemonBackendFileC

            if [ -f $daemonBackendFile ]; then
                echo "Try running backend..."
                "./daemonBackend"
            else
                echo "File for backend creation error ..."
            fi 
        fi

        # создать файл для фронтенда
        if [ -f $daemonFrontendFileC ]; then
            cc -o daemonFrontend $daemonFrontendFileC

            if [ -f $daemonFrontendFile ]; then
                echo "Try running frontend..."
                "./daemonFrontend"
            else
                echo "File for frontend creation error ..."
            fi
        fi

        echo "The process is running."

        ;;
    stop)
        echo "Stopping the process..."

        killProcess

        echo "The process is stopped."

        ;;
    restart) 
        echo "Restarting the process..."
        $0 stop
        $0 start

        echo "The process is restarted."
        ;;
    *)
        echo "Invalidate command! Use [start|stop|restart]" >&2
        ;;
esac 

