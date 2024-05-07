pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean -D DB_URL=${DB_URL} -D DB_USERNAME=${DB_USERNAME} -D DB_PASSWORD=${DB_PASSWORD} package'
        sh 'docker build -t workout-builder-demo .'
      }
    }

    stage('Remove Existing Container') {
      steps {
        sh 'docker rm -f workout-builder-container && echo "existing workout container was removed" || echo "workout container does not exist"'
      }
    }

    stage('Deploy') {
      steps {
        sh 'docker run -d -p 80:8080 --name workout-builder-container -e DB_URL=${DB_URL} -e DB_USERNAME=${DB_USERNAME} -e DB_PASSWORD=${DB_PASSWORD} workout-builder-demo'
      }
    }

  }
}
