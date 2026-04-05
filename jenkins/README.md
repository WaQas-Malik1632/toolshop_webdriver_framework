# Jenkins Setup Guide

## Required Jenkins Plugins

Install these from **Manage Jenkins → Plugins**:

| Plugin | Purpose |
|---|---|
| Pipeline | Declarative pipeline support |
| Git | Source code checkout |
| Allure Jenkins Plugin | Allure report generation |
| JUnit | TestNG XML result publishing |
| Credentials Binding | Inject test credentials securely |
| Timestamper | Add timestamps to console output |
| Workspace Cleanup | Clean workspace after builds |

---

## Global Tool Configuration

Go to **Manage Jenkins → Tools** and configure:

**JDK — add two entries:**
- Name: `JDK-21` → your Java 21 install path (Jenkins itself runs on this)
- Name: `JDK-25` → your Java 25 install path (Maven uses this to compile the project)

> Jenkins requires Java 17 or 21 to run. Your project compiles with Java 25.
> Setting `JAVA_HOME` to JDK-25 in the pipeline overrides the JDK used by Maven
> without affecting the JDK Jenkins itself runs on.

**Maven**
- Name: `Maven-3`
- Install automatically (3.9.x recommended) or point to local Maven

**Allure Commandline**
- Name: `allure`
- Install automatically from allure releases

---

## Credentials Setup

Go to **Manage Jenkins → Credentials → Global → Add Credentials**:

- Kind: `Username with password`
- ID: `toolshop-test-credentials`
- Username: `customer@practicesoftwaretesting.com`
- Password: `welcome01`
- Description: `Toolshop test user credentials`

This keeps credentials out of source code and the Jenkinsfile injects them as
`TEST_CREDS_USR` and `TEST_CREDS_PSW` environment variables at runtime.

---

## Creating the Pipeline Job

1. **New Item** → name it `toolshop-webdriver-framework` → select **Pipeline**
2. Under **Pipeline**:
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: your repo URL
   - Branch: `*/master` (or your default branch)
   - Script Path: `toolshop_webdriver_framework/Jenkinsfile`
3. Save

---

## Running the Pipeline

The pipeline is **parameterized** — on each run you choose:

| Parameter | Options | Default |
|---|---|---|
| `SUITE` | `web-smoke`, `web-e2e`, `api`, `master` | `web-smoke` |
| `BROWSER` | `chrome`, `chrome-headless`, `edge`, `edge-headless` | `chrome` |
| `APP_URL` | any URL string | `https://practicesoftwaretesting.com/` |

For CI (no display), always use `chrome-headless` or `edge-headless`.

---

## Pipeline Stages

```
Checkout → Build → Test → Allure Report
```

- **Checkout** — pulls latest code from SCM
- **Build** — compiles main and test sources (`mvn clean compile test-compile`)
- **Test** — runs the selected TestNG suite, publishes JUnit XML results
- **Allure Report** — generates and publishes the Allure HTML report

---

## Artifacts Archived on Every Run

- `screenshots/*.png` — captured on test failure
- `logs/automation.log` — full test execution log

---

## Recommended Trigger Strategy

| Trigger | Use case |
|---|---|
| On every push (webhook) | Run `web-smoke` suite — fast feedback |
| Nightly scheduled (`0 2 * * *`) | Run `master` suite — full regression |
| Manual / on-demand | Run `web-e2e` or `api` as needed |

To add a nightly trigger, add this inside `options {}` in the Jenkinsfile:

```groovy
triggers {
    cron('0 2 * * *')
}
```
