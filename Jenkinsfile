pipeline {

    agent any

    // ─── Build Parameters ────────────────────────────────────────────────────
    parameters {
        choice(
            name: 'SUITE',
            choices: ['web-smoke', 'web-e2e', 'api', 'master'],
            description: 'TestNG suite to execute'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'chrome-headless', 'edge', 'edge-headless'],
            description: 'Browser to run UI tests on'
        )
        string(
            name: 'APP_URL',
            defaultValue: 'https://practicesoftwaretesting.com/',
            description: 'Base URL of the application under test'
        )
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timestamps()
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    stages {

        // ── 1. Checkout ───────────────────────────────────────────────────────
        stage('Checkout') {
            steps {
                checkout scm
                echo "Branch: ${env.GIT_BRANCH} | Build: ${env.BUILD_NUMBER}"
            }
        }

        // ── 2. Verify Tools ───────────────────────────────────────────────────
        stage('Verify Tools') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
            }
        }

        // ── 3. Build & Compile ────────────────────────────────────────────────
        stage('Build') {
            steps {
                bat 'mvn clean compile test-compile -q'
            }
        }

        // ── 4. Run Tests ──────────────────────────────────────────────────────
        stage('Test') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'toolshop-test-credentials',
                    usernameVariable: 'TEST_USER',
                    passwordVariable: 'TEST_PASS'
                )]) {
                    // -fae = fail at end: run all tests, don't stop on first failure
                    bat """
                        mvn test -fae ^
                            -Dsuite=%SUITE% ^
                            -Dbrowser=%BROWSER% ^
                            "-Dapp.base.url=%APP_URL%" ^
                            "-Duser.login.email=%TEST_USER%" ^
                            "-Duser.login.password=%TEST_PASS%" ^
                            -q
                    """
                }
            }
            post {
                always {
                    testNG(
                        reportFilenamePattern: 'target/surefire-reports/testng-results.xml',
                        failureOnFailedTestConfig: false
                    )
                }
            }
        }

        // ── 5. Allure Report ──────────────────────────────────────────────────
        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    // ─── Post Actions ─────────────────────────────────────────────────────────
    post {
        always {
            archiveArtifacts(
                artifacts: 'screenshots/*.png, logs/automation.log',
                allowEmptyArchive: true
            )
        }
        failure {
            echo "Pipeline FAILED — check Allure report and archived screenshots."
        }
        success {
            echo "Pipeline PASSED — suite '${params.SUITE}' completed successfully."
        }
        cleanup {
            cleanWs()
        }
    }
}
