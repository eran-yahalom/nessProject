pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
    }

    parameters {
        // פרמטר חדש לבחירת הלאנצ'ר הספציפי
        string(name: 'LAUNCHER', defaultValue: 'BookLauncher', description: 'Enter Launcher class name (e.g., BookLauncher, RegisterLauncher)')

        // פרמטר לתגים
        string(name: 'TAGS', defaultValue: '@book', description: 'Enter @tag (e.g., @book, @smoke)')
    }

    stages {
        stage('Cleanup') {
            steps {
                sh 'rm -rf target/allure-results'
            }
        }

        stage('Run Automation') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // שימוש ב-Dtest כדי להריץ רק את הקלאס שנבחר בפרמטרים
                  sh "mvn clean test -Dtest=${params.LAUNCHER} -Dcucumber.filter.tags='${params.TAGS}' -Ddataproviderthreadcount=3"
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}