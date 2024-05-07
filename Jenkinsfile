pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean -D DB_URL=jdbc:postgresql://revature-sample-database.cx4mu6k601bl.us-east-1.rds.amazonaws.com:5432/postgres -D DB_USERNAME=postgres -D DB_PASSWORD=password package'
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
        sh 'docker run -d -p 80:8080 --name workout-builder-container -e DB_URL=jdbc:postgresql://revature-sample-database.cx4mu6k601bl.us-east-1.rds.amazonaws.com:5432/postgres -e DB_USERNAME=postgres -e DB_PASSWORD=password workout-builder-demo'
      }
    }

  }
}