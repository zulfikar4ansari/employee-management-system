pipeline {

    agent any

    tools {
        maven 'Maven 3.9.0'
        jdk 'java 21.0.8 2025-07-15 LTS'
    }

    environment {

        DOCKER_REGISTRY = "zulfikar4ansari"
        VERSION = "1.0.${BUILD_NUMBER}"

        API_GATEWAY = "api-gateway"
        AUTH_SERVICE = "auth-service"
        EMPLOYEE_SERVICE = "employee-service"
        ATTENDANCE_SERVICE = "attendance-service"
        PAYROLL_SERVICE = "payroll-service"
        NOTIFICATION_SERVICE = "notification-service"

    }

    stages {

        stage('Checkout Code') {

            steps {

                git branch: 'main',
                url: 'https://github.com/zulfikar4ansari/employee-management-system.git'

            }

        }

        stage('Build Parent Project') {

            steps {

                bat 'mvn clean install -DskipTests'

            }

        }

        stage('Build Docker Images') {

            steps {

                bat "docker build -t %DOCKER_REGISTRY%/%API_GATEWAY%:%VERSION% ./api-gateway"
                bat "docker build -t %DOCKER_REGISTRY%/%AUTH_SERVICE%:%VERSION% ./auth-service"
                bat "docker build -t %DOCKER_REGISTRY%/%EMPLOYEE_SERVICE%:%VERSION% ./employee-service"
                bat "docker build -t %DOCKER_REGISTRY%/%ATTENDANCE_SERVICE%:%VERSION% ./attendance-service"
                bat "docker build -t %DOCKER_REGISTRY%/%NOTIFICATION_SERVICE%:%VERSION% ./notification-service"
                bat "docker build -t %DOCKER_REGISTRY%/%PAYROLL_SERVICE%:%VERSION% ./payroll-service"

            }

        }

        stage('Push Docker Images') {

            steps {

                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"

                    bat "docker push %DOCKER_REGISTRY%/%API_GATEWAY%:%VERSION%"
                    bat "docker push %DOCKER_REGISTRY%/%AUTH_SERVICE%:%VERSION%"
                    bat "docker push %DOCKER_REGISTRY%/%EMPLOYEE_SERVICE%:%VERSION%"
                    bat "docker push %DOCKER_REGISTRY%/%ATTENDANCE_SERVICE%:%VERSION%"
                    bat "docker push %DOCKER_REGISTRY%/%NOTIFICATION_SERVICE%:%VERSION%"
                    bat "docker push %DOCKER_REGISTRY%/%PAYROLL_SERVICE%:%VERSION%"

                }

            }

        }

        stage('Deploy Containers') {

            steps {

                bat "docker run -d -p 8080:8080 %DOCKER_REGISTRY%/%API_GATEWAY%:%VERSION%"
                bat "docker run -d -p 8081:8081 %DOCKER_REGISTRY%/%AUTH_SERVICE%:%VERSION%"
                bat "docker run -d -p 8082:8082 %DOCKER_REGISTRY%/%EMPLOYEE_SERVICE%:%VERSION%"
                bat "docker run -d -p 8083:8083 %DOCKER_REGISTRY%/%ATTENDANCE_SERVICE%:%VERSION%"
                bat "docker run -d -p 8084:8084 %DOCKER_REGISTRY%/%PAYROLL_SERVICE%:%VERSION%"
                bat "docker run -d -p 8085:8085 %DOCKER_REGISTRY%/%NOTIFICATION_SERVICE%:%VERSION%"

            }

        }

    }

    post {

        success {

            echo "Microservices deployed successfully"

        }

        failure {

            echo "Pipeline failed"

        }

    }

}