#!groovy

node('chase-dev') {
    try{
    stage('Checkout') {
            echo 'configure started'
            git branch: '${BRANCH_NAME}', credentialsId:'519f61d8-ec2a-4ea8-9bdd-877e99afe9e3', url: 'https://gitlab.com/invente/chase/apis/search-service-apis.git'
            echo 'configure done'
        }
    stage('mvn build') {
                def mvn_version = 'M3'
                withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                sh 'mvn clean install -Dtest=none -Dit.test=none -Dmaven.test.skip=true'
            }
        }
stage('code quality') {
         sh 'mvn sonar:sonar -Dsonar.host.url=http://10.9.9.202:30009'
               }    
stage('docker-build') {
         sh 'sudo docker-compose build'
        }

        stage('docker-build') {
         sh 'sudo docker-compose push app'
        }


    }
    finally{
        stage('stop-container') {
	        sh 'sudo docker-compose down'
	    }
    }
    
}
