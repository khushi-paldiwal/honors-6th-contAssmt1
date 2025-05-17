// pipeline {
//     agent {
//         docker {
//             image 'maven:3.8.5-openjdk-17'
//             args '-v $HOME/.m2:/root/.m2'
//         }
//     }

//     environment {
//         EC2_HOST = '44.212.35.131'
//         EC2_USER = 'ec2-user'
//         EC2_PASSWORD = 'Khushi@2312'
//         JAR_NAME = 'airline-management-system-0.0.1-SNAPSHOT.jar'
//     }

//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git 'https://github.com/khushi-paldiwal/honors-6th-contAssmt1.git'
//             }
//         }

//         stage('Build Project') {
//             steps {
//                 script {
//                     docker.image('maven:3.8.5-openjdk-17').inside("-v ${pwd()}:/app -w /app") {
//                         sh 'mvn clean package'
//                     }
//                 }
//             }
//         }

//         stage('Run Tests') {
//             steps {
//                 sh 'mvn test'
//             }
//         }

//         stage('Deploy to EC2') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: 'ec2-pass-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
//                     sh """
//                     sshpass -p "Khushi@2312" scp target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/ec2-user/
//                     sshpass -p "Khushi@2312" ssh ${EC2_USER}@${EC2_HOST} 'pkill -f ${JAR_NAME} || true && nohup java -jar ${JAR_NAME} > app.log 2>&1 &'
//                     """
//                 }
//             }
//         }
//     }
// }
pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/khushi-paldiwal/honors-6th-contAssmt1.git'
        EC2_HOST = 'ec2-52-202-109-170.compute-1.amazonaws.com'
        EC2_USER = 'ubuntu'
        JAR_NAME = 'airline-management-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout Code') {
            steps {
                cleanWs()
                git branch: 'main', url: "${GIT_REPO}"
                echo "Checked out code from ${GIT_REPO}"
            }
        }

        stage('Build & Test using Docker') {
            steps {
                script {
                    docker.image('maven:3.9.4-eclipse-temurin-17').inside {
                        sh 'mvn clean install'
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'jenkins_aws_key', keyFileVariable: 'SSH_KEY')]) {
                        // Step 1: Copy the JAR file to EC2
                        sh """
                        scp -o StrictHostKeyChecking=no -i ${SSH_KEY} target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/
                        """

                        // Step 2: Start the JAR on EC2
                        sh """
                        ssh -o StrictHostKeyChecking=no -i ${SSH_KEY} ${EC2_USER}@${EC2_HOST} '
                            pkill -f ${JAR_NAME} || true
                            nohup java -jar ${JAR_NAME} > airline.log 2>&1 &
                        '
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up Docker containers locally..."
            sh 'docker container prune -f || true'
        }

        success {
            echo '✅ CI/CD pipeline completed successfully!'
        }

        failure {
            echo '❌ CI/CD pipeline failed.'
        }
    }
}
