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
                docker.withRegistry("${IMAGE_REGISTRY}", ${IMAGE_PUSH_CRED}) {                
                	def dockerImage = docker.image("${IMAGE_REGISTRY}/${IMAGE_NAME}:v${BUILD_NUMBER}");
                    dockerImage.push();
                }
            }
        }
    }
  }
}