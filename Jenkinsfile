#!groovy

node('master') {

    environment { 
        registry = "35.153.93.153:8123/dockerrepo" 
        registryCredential = 'nexus' 
        dockerImage = ''
    }
  stages { 
       stage('SCM checkout'){
        steps{
            git branch: 'master', credentialsId: 'fd8c337c-6670-4621-8aef-58f6db408b08', url: 'https://github.com/karunakarreddych/assignments.git'
        }
    }
      stage('mvn build'){
         steps{
             script{
                def maven_home = tool name: 'maven', type: 'maven'
	            sh "${maven_home}/bin/mvn clean install"
             }
           }
	  }
       stage('docker-build') {
        steps{
            script{
               dockerImage = docker.build registry + ":$BUILD_NUMBER" 
            }
        }
       
    }
        stage('push image') {
         steps{
             script{
                 docker.withRegistry( 'http://35.153.93.153:8123', registryCredential ) {
                        dockerImage.push() 
             }
         }
       }
     }
	stage(deploy-to-k8s){
		steps{
			sshagent(['waveserver']) {
 			   sh 'scp -o StrictHostKeyChecking=no access-management-domain-api.yaml waveserver@10.9.9.196:/home/waveserver/assignment/"
			   script{
			         sh "ssh waveserver@10.9.9.196 kubectl apply -f ."	
				}
			}	
		}
	}
    }

}
