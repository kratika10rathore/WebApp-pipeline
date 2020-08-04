node {
    try{
     stage('Git Checkout'){
        git credentialsId: 'github', url: 'https://github.com/kratika10rathore/WebApp-pipeline.git'
        
    }
     stage('Code Build & Test'){
        def mvnHome = tool name: 'Maven' , type: 'maven'
        def mvnCMD = "${mvnHome}/bin/mvn"
        sh "${mvnCMD} clean package"
    }
    stage('SonarQube Analysis') {
     def scannerHome = tool 'SonarQube Scanner';
       withSonarQubeEnv('sonarqube-6.4')  { 
       sh "${scannerHome}/bin/sonar-scanner   \
       -Dsonar.host.url=http:\\35.240.172.247:9000 \
       -Dsonar.login=2ce4e6ef2d679007972d722311f12adfb10564a5 \
       -Dsonar.projectVersion=1.0.0 \
       -Dsonar.projectKey=WebAppKey \
       -Dsonar.language=java \
       -Dsonar.junit.reportsPath=target/surefire-reports/TEST-com.TestSubject.xml   \
       -Dsonar.jacoco.reportPath=target/coverage-reports/jacoco-unit.exec  \
       -Dsonar.sources=src"
          }  
    }
    stage('Junit Report Publish'){
       junit 'target/surefire-reports/*.xml'
   }
    stage("Docker Build Image"){
        def dockerHome = tool name: 'Docker', type: 'dockerTool'
        def dockerCMD = "${dockerHome}/bin/docker"
        sh "${dockerCMD} ps"
        sh "${dockerCMD}  --version"
        sh "${dockerCMD} build -t kratika1/devops:1.${BUILD_NUMBER} ."
    }
    
    stage("Docker Pushing Image to DockerHub"){
        def dockerHome = tool name: 'Docker', type: 'dockerTool'
        def dockerCMD = "${dockerHome}/bin/docker"
        withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'password', usernameVariable: 'username')]) {
        sh "${dockerCMD} login -u ${username} -p ${password}"
        }
        
        sh "${dockerCMD} push kratika1/devops:1.${BUILD_NUMBER}"
}
    stage("Docker Run Image"){
        def dockerHome = tool name: 'Docker', type: 'dockerTool'
        def dockerCMD = "${dockerHome}/bin/docker"
        sh "${dockerCMD} run -p 8083:8080 -d kratika1/devops:1.${BUILD_NUMBER}"
    }
    stage("EC2 Configure Ansible"){
        sh 'which ansible'
        sh 'ansible --version'
          ansiblePlaybook  credentialsId: 'aws-pem', installation: 'Ansible', playbook: 'aws-ec2-playbook.yml'
     }
   stage("Wait to EC2 instance coming up"){
       sh 'sleep 40'
   }
   stage("Getting Ip of EC2 Instance"){
      def command = 'aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress[]"'
      def result = sh script: "${command}" , returnStdout:true
      def VmIp = result.split('"')
      ipAddress= VmIp[1]
      println ipAddress
}
   
   stage('Installing Docker On EC2 Instance'){
      def dockerCMD = 'sudo yum install docker -y'
      sshagent(credentials: ['aws-pem'], ignoreMissing: true){
      sh "ssh -o StrictHostKeyChecking=no ec2-user@${ipAddress} ${dockerCMD}"
       }
   }
   stage('Starting Docker On EC2 Instnace'){
        def dockerCMD = 'sudo service docker start'
        sshagent(credentials: ['aws-pem'], ignoreMissing: true){
        sh "ssh -o StrictHostKeyChecking=no ec2-user@${ipAddress} ${dockerCMD}"
       }
   }
   stage('Pulling Docker Image In EC2 Instnace'){
           def dockerCMD = 'sudo docker pull kratika1/devops:1.${BUILD_NUMBER}'
           sshagent(credentials: ['aws-pem'], ignoreMissing: true){
           sh "ssh -o StrictHostKeyChecking=no ec2-user@${ipAddress} ${dockerCMD}"
       }
   }
   stage('Running Docker Image In EC2 Instance'){
           def dockerCMD = 'sudo docker run -p 8082:8080 -d kratika1/devops:1.${BUILD_NUMBER}'
           sshagent(credentials: ['aws-pem'], ignoreMissing: true){
           sh "ssh -o StrictHostKeyChecking=no ec2-user@${ipAddress} ${dockerCMD}"
   
}
}
    
    stage('Sending Email '){
        emailext attachLog: true, body: 'Status of Build', compressLog: true, subject: 'Devops Pipeline Build', to: 'kratirathore104@gmail.com'
    } 
    }
    
    catch(e){
             currentBuild.result = 'FAILURE'
             emailext attachLog: true, body: 'Attaching the logs for failure.', subject: 'subject: \'Build failed in Jenkins: $PROJECT_NAME - #$BUILD_NUMBER\'', replyTo: 'rathore.kratika109@gmail.com', to: 'kratirathore104@gmail.com'
            
         }  
         finally {  
             echo 'This will run only if the run was marked finally'
            
         }  
    }
    
