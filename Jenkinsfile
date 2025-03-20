// pipeline{
//     agent any
//     tools{
//         maven 'Maven'
//         jdk 'java21'
//     }
//
//     stages{
//         stage('checkout'){
//             steps{
//                 git branch: 'main', url: 'https://github.com/Pawaffle/InclassAikido.git'
//             }
//         }
//
//         stage('build'){
//             steps{
//                 bat 'mvn clean install'
//             }
//         }
//
//         stage('test'){
//             steps{
//                 bat 'mvn test'
//             }
//         }
//
//         stage('Test & Coverage') {
//             steps {
//                 bat 'mvn test jacoco:report' // Runs tests & generates JaCoCo coverage report
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml' // Publish JUnit test results
//
//                     // Discover reference build for comparison
//                     discoverReferenceBuild()
//
//                     // Record Coverage using Coverage Plugin
//                     recordCoverage(
//                         tools: [[parser: 'JACOCO']],
//                         id: 'jacoco',
//                         name: 'JaCoCo Coverage',
//                         sourceCodeRetention: 'EVERY_BUILD',
//                         qualityGates: [
//                             [threshold: 60.0, metric: 'LINE', baseline: 'PROJECT', unstable: true],
//                             [threshold: 60.0, metric: 'BRANCH', baseline: 'PROJECT', unstable: true]
//                         ]
//                     )
//                 }
//             }
//         }
//     }
// }


pipeline {
    agent any
     environment {
            // Define Docker Hub credentials ID
            DOCKERHUB_CREDENTIALS_ID = 'dockerhub-credentials'
            // Define Docker Hub repository name
            DOCKERHUB_REPO = 'paveldeg/class_trip_test'
            // Define Docker image tag
            DOCKER_IMAGE_TAG = 'latest_v1'
        }
    stages {
        stage('Checkout') {
            steps{
                git branch: 'main', url: 'https://github.com/Pawaffle/LectureTripTest.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }

         stage('Build Docker Image') {
            steps {
                // Build Docker image
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                // Push Docker image to Docker Hub
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }
}
