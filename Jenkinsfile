pipeline {
  agent {
    kubernetes {
      label 'default'
      retries 2
    }
  }
  stages {
    stage ("Test") {
    	steps {
    		script {
    			sh 'mvn test'
    		}
    	}
    }
    
    stage ("Build") {
    	steps {
    		script {
    			sh 'mvn package -Dmaven.test.skip'
    		}
    	}
    }
    stage("Build image") {
        steps {
            script {
            	sh './build-image.sh'
            }
            
            
        }
    }
	stage("Publish image") {
        steps {
            script {
                docker.withRegistry("https://${IMAGE_REGISTRY}", "${IMAGE_PUSH_CRED}") {                
                	def dockerImage = docker.image("${IMAGE_REGISTRY}/${IMAGE_NAME}:v${BUILD_NUMBER}");
                    dockerImage.push();
                }
            }
        }
    }
    
    stage("Build helm chart") {
      steps {
        script {
          withCredentials([usernamePassword(credentialsId: "${IMAGE_PUSH_CRED}", passwordVariable: 'HARBOR_PASSWORD', usernameVariable: 'HARBOR_USERNAME')]) {
            sh '''
            helm package helm-charts --app-version=v${BUILD_NUMBER} --version=0.1.${BUILD_NUMBER}
            helm push demo-trading-system-0.1.${BUILD_NUMBER}.tgz oci://10.5.1.16/hsbc-demo --username=${HARBOR_USERNAME} --password=${HARBOR_PASSWORD}
            '''
          }
        }
      }       
    }
  }
}