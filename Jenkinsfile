pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        EC2_HOST = '44.212.35.131'
        EC2_USER = 'ec2-user'
        EC2_PASSWORD = 'Khushi@2312'
        JAR_NAME = 'airline-management-system-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/khushi-paldiwal/honors-6th-contAssmt1.git'
            }
        }

        stage('Build Project') {
            steps {
                sh 'mvn clean install -DskipTests=false'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy to EC2') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'ec2-pass-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh """
                    sshpass -p "Khushi@2312" scp target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/ec2-user/
                    sshpass -p "Khushi@2312" ssh ${EC2_USER}@${EC2_HOST} 'pkill -f ${JAR_NAME} || true && nohup java -jar ${JAR_NAME} > app.log 2>&1 &'
                    """
                }
            }
        }
    }
}
