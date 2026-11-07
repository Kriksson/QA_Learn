pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/jdk-27'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    parameters {
        choice(name: 'TEST_SET', choices: ['API', 'UI', 'UI+API', 'ALL'], description: 'Какой набор тестов запустить?')
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
            steps {
                sh './mvnw -B "-Dtest=API/**/*Test" clean test'
            }
        }

        stage("Запустить UI-Автотесты") {
            steps {
                sh './mvnw -B "-Dtest=UI/**/*Test" -Dselenide.remote=http://selenoid:4444/wd/hub -Dselenide.browserVersion=128.0 test'
            }
        }

        stage("Запустить UI-API-Автотесты") {
            steps {
                sh './mvnw -B "-Dtest=API_UI/**/*Test" -Dselenide.remote=http://selenoid:4444/wd/hub -Dselenide.browserVersion=128.0 test'
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