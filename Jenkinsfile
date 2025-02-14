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
            	sh '''
            	tar -czf context.tar.gz Dockerfile -C target demo-trading-system.jar
				docker build -t "${IMAGE_REGISTRY}/${IMAGE_NAME}:v${BUILD_NUMBER}" - < context.tar.gz
            	'''
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