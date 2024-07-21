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
            steps{
                sh "docker push hanoi1610/selenium"
            }
        }
    }


}