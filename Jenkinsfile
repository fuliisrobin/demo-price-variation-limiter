pipeline {
  agent {
    kubernetes {
      configMap 'jenkins-pod-template'
      configMapKey 'pod-template.yaml'
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
				docker build -t ${env.IMAGE_REGISTRY}/${env.IMAGE_NAME}:v${env.BUILD_NUMBER} - < context.tar.gz
            	'''
            }
            
            
        }
    }
	stage("Publish image") {
        steps {
            script {
                docker.withRegistry("${env.IMAGE_REGISTRY}", ${env.IMAGE_PUSH_CRED}) {                
                	def dockerImage = docker.image("${env.IMAGE_REGISTRY}/${env.IMAGE_NAME}:v${BUILD_NUMBER}");
                    dockerImage.push();
                }
            }
        }
    }
  }
}