# 🛝 Lint Playground

### [🔍 Android Lint SARIF Viewer](https://simonmarquis.github.io/Lint-Playground/)

### ▶️ Run configurations

- `🕵️ Lint`: [txt](app/build/reports/lint-results-debug.txt), [xml](app/build/reports/lint-results-debug.xml), [html](app/build/reports/lint-results-debug.html), [sarif](app/build/reports/lint-results-debug.sarif)
- `👷 Build`: [lint-debug.aar](lint/build/outputs/aar/lint-debug.aar), [lint-release.aar](lint/build/outputs/aar/lint-release.aar)
- `🧑‍🔬 Test`: [:app](app/build/reports/tests/testDebugUnitTest/index.html), [:checks](checks/build/reports/tests/test/index.html), [:libs:android](libs/android/build/reports/tests/testDebugUnitTest/index.html)

### 🧮 More info

- [Issue registry](checks/src/main/kotlin/fr/smarquis/playground/lint/IssueRegistry.kt): where issues are registered
- [lint.xml](.config/lint.xml): lint configuration
- [lint-baseline.xml](.config/lint-baseline.xml): lint baseline

### 🏗️  Architecture

```mermaid
---
config:
  layout: elk
  elk:
    nodePlacementStrategy: BRANDES_KOEPF
---
graph LR
  :build-logic
  :app:::android-application
  :lint:::android-library
  :checks:::android-library

  subgraph :libs
    :libs:android[:android]:::android-library
    :libs:jvm[:jvm]:::jvm
  end

  :libs:android -->|api|:libs:jvm
  :app -->|implementation|:libs:android
  :app & :libs:android & :libs:jvm & :lint -->|lintChecks|:checks
  :lint -->|lintPublish|:checks
  

classDef android-application fill:#2C4162,stroke:#fff,stroke-width:2px,color:#fff;
classDef android-library fill:#3BD482,stroke:#fff,stroke-width:2px,color:#fff;
classDef jvm fill:#7F52FF,stroke:#fff,stroke-width:2px,color:#fff;
```

### 🔗 Links

- https://googlesamples.github.io/android-custom-lint-rules/
- https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:lint/
