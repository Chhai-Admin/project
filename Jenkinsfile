pipeline {
    agent any
    tools {
        maven "3.8.7"
    }
    environment {
        DOCKER_IMAGE = "kemchhai093/docker_jenkins_springboot:${BUILD_NUMBER}"
        REMOTE_HOST = "your.swarm.manager.ip"
        REMOTE_USER = "remote_user"
        STACK_NAME = "springbootstack"
    }
    stages {
        stage('Compile and Clean') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Package') {
            steps {
                sh "mvn package"
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u kemchhai093 -p ${Dockerpwd}"
                }
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push $DOCKER_IMAGE'
            }
        }
        stage('Prepare Compose File') {
            steps {
                script {
                    // Replace the image tag in docker-compose.yml
                    sh 'sed "s/\\\${BUILD_NUMBER}/$BUILD_NUMBER/g" docker-compose.yml > docker-compose.deploy.yml'
                }
            }
        }
        stage('Deploy to Docker Swarm') {
            steps {
                sshagent(credentials: ['SwarmSSHKey']) {
                    sh """
                        scp -o StrictHostKeyChecking=no docker-compose.deploy.yml ${REMOTE_USER}@${REMOTE_HOST}:/tmp/docker-compose.yml
                        ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} \\
                        'docker stack deploy -c /tmp/docker-compose.yml $STACK_NAME'
                    """
                }
            }
        }
        stage('Archiving') {
            steps {
                archiveArtifacts '**/target/*.jar'
            }
        }
    }
}
