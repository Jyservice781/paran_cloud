pipeline {
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64/'
        repository = "cheonghalim/paranmanzang"  // docker hub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('docker-hub') // jenkins에 등록해 놓은 docker hub credentials 이름
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
    }
    agent any
    stages {
        stage('Cleanup') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
              checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  userRemoteConfigs: [[
                      url: 'git@github.com:paranmanzang/paran_msa.git',
                      credentialsId: 'ssh-key'
                  ]],
                  extensions: [[$class: 'SubmoduleOption', recursiveSubmodules: true, parentCredentials: true]]
              ])

            }
        }

        stage('Build') {
            steps {
                script {
                    sh '''#!/bin/bash
                    set -e
                    export JAVA_HOME="$JAVA_HOME"

                    chmod +x ./gradlew

                    all_modules=("server:gateway-server" "server:config-server" "server:eureka-server"
                                 "service:user-service" "service:group-service" "service:chat-service"
                                 "service:file-service" "service:room-service" "service:comment-service")

                    echo "Cleaning..."
                    ./gradlew clean

                    for module in "${all_modules[@]}"
                    do
                      echo "Building BootJar for $module"
                      ./gradlew :$module:bootJar --warning-mode all
                    done
                    '''
                }
            }
        }
        stage('Login to Docker Hub') {
            steps {
                sh '''
                echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin
                '''
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                           def modulePaths = [
                               "config": "/var/lib/jenkins/workspace/paranmanzang/server/config-server",
                               "eureka": "/var/lib/jenkins/workspace/paranmanzang/server/eureka-server",
                               "user": "/var/lib/jenkins/workspace/paranmanzang/service/user-service",
                               "group": "/var/lib/jenkins/workspace/paranmanzang/service/group-service",
                               "chat": "/var/lib/jenkins/workspace/paranmanzang/service/chat-service",
                               "file": "/var/lib/jenkins/workspace/paranmanzang/service/file-service",
                               "room": "/var/lib/jenkins/workspace/paranmanzang/service/room-service",
                               "comment": "/var/lib/jenkins/workspace/paranmanzang/service/comment-service",
                               "gateway": "/var/lib/jenkins/workspace/paranmanzang/server/gateway-server"
                           ]

                           for (module in modulePaths.keySet()) {
                               echo "Building Docker image for ${module}"
                               sh """
                               docker build -t ${repository}:${module} ${modulePaths[module]}
                               """
                           }
               }
            }
        }

        stage('Push to Docker Hub') {
            steps {
            script {
                                def modulePaths = [
                                    "config": "/var/lib/jenkins/workspace/paranmanzang/server/config-server",
                                    "eureka": "/var/lib/jenkins/workspace/paranmanzang/server/eureka-server",
                                    "user": "/var/lib/jenkins/workspace/paranmanzang/service/user-service",
                                    "group": "/var/lib/jenkins/workspace/paranmanzang/service/group-service",
                                    "chat": "/var/lib/jenkins/workspace/paranmanzang/service/chat-service",
                                    "file": "/var/lib/jenkins/workspace/paranmanzang/service/file-service",
                                    "room": "/var/lib/jenkins/workspace/paranmanzang/service/room-service",
                                    "comment": "/var/lib/jenkins/workspace/paranmanzang/service/comment-service",
                                    "gateway": "/var/lib/jenkins/workspace/paranmanzang/server/gateway-server"
                                ]

                                // 각 모듈에 대해 Docker 이미지 태그 및 푸시
                                for (module in modulePaths.keySet()) {
                                    def imageTag = "${repository}:${module}"  // BUILD_ID로 고유한 태그 설정
                                    echo "Tagging and pushing Docker image for ${module} with tag ${env.BUILD_ID}"
                                    sh """
                                    docker tag ${repository}:${module} ${imageTag}
                                    docker push ${imageTag}
                                    """
                                }
                            }
            }
        }


       stage('Deploy to Kubernetes') {
           steps {
               script {
                   def modulePaths = [
                       "gateway": "/var/lib/jenkins/workspace/paranmanzang/server/gateway-server/gateway.yaml",
                       "config": "/var/lib/jenkins/workspace/paranmanzang/server/config-server/config.yaml",
                       "eureka": "/var/lib/jenkins/workspace/paranmanzang/server/eureka-server/eureka.yaml",
                       "user": "/var/lib/jenkins/workspace/paranmanzang/service/user-service/user.yaml",
                       "group": "/var/lib/jenkins/workspace/paranmanzang/service/group-service/group.yaml",
                       "chat": "/var/lib/jenkins/workspace/paranmanzang/service/chat-service/chat.yaml",
                       "file": "/var/lib/jenkins/workspace/paranmanzang/service/file-service/file.yaml",
                       "room": "/var/lib/jenkins/workspace/paranmanzang/service/room-service/room.yaml",
                       "comment": "/var/lib/jenkins/workspace/paranmanzang/service/comment-service/comment.yaml"
                   ]

                   for (module in modulePaths.keySet()) {
                       def yamlPath = modulePaths[module]
                       echo "Applying Kubernetes deployment for ${module} using YAML file: ${yamlPath}"

                       // YAML 파일을 사용하여 배포 적용
                       sh """
                       kubectl apply -f ${yamlPath}
                       """

                       // YAML 파일에서 정의된 실제 배포 이름을 사용하여 롤아웃 상태 확인
                       // 예: 배포 이름이 module 이름과 동일하다고 가정
                       def deploymentName = module
                       echo "Checking rollout status for deployment ${deploymentName}"

                       // 배포 롤아웃 상태 확인
                       sh """
                       kubectl rollout status deployment/${deploymentName}
                       """
                   }
               }
           }
       }


    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}
