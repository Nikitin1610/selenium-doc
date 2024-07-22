pipeline{

    agent any

    stages{

        stage('Buld Jar'){
            steps{
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Build Image'){
            steps{
                sh "docker build -t=hanoi1610/selenium ."
            }
        }

        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                sh "docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}""
                sh "docker push hanoi1610/selenium"
            }
        }
    }

    post {
        always {
            sh "docker logout"
        }
    }
}