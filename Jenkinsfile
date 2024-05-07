pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean -D DB_URL=${DB_URL} -D DB_USERNAME=${DB_USERNAME} -D DB_PASSWORD=${DB_PASSWORD} package'
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t workout-builder-demo . '
      }
    }

    stage('Docker Delete Current Container') {
      steps {
        sh 'docker rm -f workout-builder-container && echo "container workout-builder-container removed" || echo "container workout-builder-container does not exist"'
      }
    }

    stage('Docker Run Container') {
      steps {
        sh 'docker run -d -p 80:8080 --name workout-builder-container -e DB_URL=${DB_URL} -e DB_USERNAME=${DB_USERNAME} -e DB_PASSWORD=${DB_PASSWORD} workout-builder-demo'
      }
    }

  }
}