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

    // ─── Environment Variables ────────────────────────────────────────────────
    environment {
        JAVA_HOME        = tool name: 'JDK-25', type: 'jdk'
        MAVEN_HOME       = tool name: 'Maven-3', type: 'maven'
        PATH             = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
        ALLURE_RESULTS   = 'allure-results'
        TEST_CREDS       = credentials('toolshop-test-credentials')  // username/password secret
    }

    // ─── Pipeline Options ─────────────────────────────────────────────────────
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

        // ── 2. Build & Compile ────────────────────────────────────────────────
        stage('Build') {
            steps {
                dir('toolshop_webdriver_framework') {
                    sh 'mvn clean compile test-compile -q'
                }
            }
        }

        // ── 3. Run Tests ──────────────────────────────────────────────────────
        stage('Test') {
            steps {
                dir('toolshop_webdriver_framework') {
                    sh """
                        mvn test \
                            -Dsuite=${params.SUITE} \
                            -Dbrowser=${params.BROWSER} \
                            -Dapp.base.url=${params.APP_URL} \
                            -Duser.login.email=${TEST_CREDS_USR} \
                            -Duser.login.password=${TEST_CREDS_PSW} \
                            -q
                    """
                }
            }
            post {
                always {
                    // Archive raw TestNG surefire reports
                    junit(
                        testResults: 'toolshop_webdriver_framework/target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        // ── 4. Allure Report ──────────────────────────────────────────────────
        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: "toolshop_webdriver_framework/${env.ALLURE_RESULTS}"]]
                ])
            }
        }
    }

    // ─── Post Actions ─────────────────────────────────────────────────────────
    post {

        always {
            // Archive screenshots on every run
            archiveArtifacts(
                artifacts: 'toolshop_webdriver_framework/screenshots/*.png',
                allowEmptyArchive: true
            )
            // Archive logs
            archiveArtifacts(
                artifacts: 'toolshop_webdriver_framework/logs/automation.log',
                allowEmptyArchive: true
            )
        }

        failure {
            echo "Pipeline FAILED — check Allure report and archived screenshots."
        }

        success {
            echo "Pipeline PASSED — all tests in '${params.SUITE}' suite completed successfully."
        }

        cleanup {
            // Clean workspace after every build to avoid stale state
            cleanWs()
        }
    }
}
