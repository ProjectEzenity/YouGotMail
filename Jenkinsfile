node {
    stage('Build') {
        git url: 'https://github.com/ProjectEzenity/YouGotMail.git'
        withMaven {
            sh "mvn -B -DskipTests clean package"
        }
    }
}