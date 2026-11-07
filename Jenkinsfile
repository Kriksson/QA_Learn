pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/jdk-27'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        JUNIT_ARGS = "-Djunit.jupiter.execution.parallel.config.fixed.parallelism=${params.THREADS} -Djunit.jupiter.execution.parallel.config.fixed.max-pool-size=${params.THREADS}"
    }

    parameters {
        choice(name: 'TEST_SET', choices: ['API', 'UI', 'UI+API', 'ALL'], description: 'Какой набор тестов запустить?')
        choice(
            name: 'THREADS',
            choices: ['1', '2'],
            description: 'Количество потоков для выполнения тестов'
        )

    }

    stages {
        stage("Получить исходники") {
            steps {
                git branch: 'master',
                url: 'https://github.com/Kriksson/QA_Learn'
            }
        }

        stage("Проверить Java") {
            steps {
                sh 'java -version'
                sh './mvnw -version'
            }
        }

        stage("Запустить API Автотесты") {
            when {
                expression {
                    params.TEST_SET == 'API' || params.TEST_SET == 'ALL'
                }
            }
            steps {
                sh './mvnw -B $JUNIT_ARGS "-Dtest=API/**/*Test" test'
            }
        }

        stage("Запустить UI-Автотесты") {
            when {
                expression {
                    params.TEST_SET == 'UI' || params.TEST_SET == 'ALL'
                }
            }
            steps {
                sh './mvnw -B $JUNIT_ARGS "-Dtest=UI/**/*Test" -Dselenide.remote=http://selenoid:4444/wd/hub -Dselenide.browserVersion=128.0 test'
            }
        }

        stage("Запустить UI-API-Автотесты") {
            when {
                expression {
                    params.TEST_SET == 'UI+API' || params.TEST_SET == 'ALL'
                }
            }
            steps {
                sh './mvnw -B $JUNIT_ARGS "-Dtest=API_UI/**/*Test" -Dselenide.remote=http://selenoid:4444/wd/hub -Dselenide.browserVersion=128.0 test'
            }
        }

        stage('Очистить результаты пред.запуска') {
            steps {
                sh './mvnw -B clean'

                dir('allure-results') {
                    deleteDir()
                }
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/TEST-*.xml'

            archiveArtifacts(
                artifacts: 'allure-results/**'
            )

            allure(
                allureVersion: '3',
                includeProperties: true,
                results: [[path: 'allure-results']]
            )
        }
    }
}