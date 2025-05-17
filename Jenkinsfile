pipeline {
    agent {
        docker {
            image 'docker:20.10.16-cli'  
            args '--privileged -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        GIT_REPO = 'https://github.com/khushi-paldiwal/honors-6th-contAssmt1.git'
        GIT_BRANCH = 'main' // or your actual branch name
        EC2_HOST = 'ec2-52-91-201-177.compute-1.amazonaws.com'
        EC2_USER = 'ec2-user'
        SSH_CREDENTIALS_ID = '4ccea7d4-e292-43b1-b5ea-31a5df87c791'
        DOCKER_IMAGE_NAME = 'airline-management'
        DOCKER_IMAGE_PREFIX = 'khushi2312'
        PORT = '8080'
    }

    stages {
        stage('Checkout and Build Docker Image') {
            steps {
                dir("${DOCKER_IMAGE_NAME}") {
                    git url: "${GIT_REPO}", branch: "${GIT_BRANCH}"

                    sh """
                    docker build -t ${DOCKER_IMAGE_PREFIX}/${DOCKER_IMAGE_NAME}:latest .
                    docker save ${DOCKER_IMAGE_PREFIX}/${DOCKER_IMAGE_NAME}:latest -o ${DOCKER_IMAGE_NAME}.tar
                    """
                }
            }
        }

        stage('Deploy Docker Image on EC2') {
            steps {
                sshagent(credentials: [SSH_CREDENTIALS_ID]) {
                    sh """
                    scp -o StrictHostKeyChecking=no ${DOCKER_IMAGE_NAME}/${DOCKER_IMAGE_NAME}.tar ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/

                    ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} '
                        docker load -i /home/${EC2_USER}/${DOCKER_IMAGE_NAME}.tar &&
                        docker stop ${DOCKER_IMAGE_NAME} || true &&
                        docker rm ${DOCKER_IMAGE_NAME} || true &&
                        docker run -d --name ${DOCKER_IMAGE_NAME} -p ${PORT}:${PORT} ${DOCKER_IMAGE_PREFIX}/${DOCKER_IMAGE_NAME}:latest
                    '
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful!"
        }
        failure {
            echo "❌ Pipeline failed."
        }
    }
}
