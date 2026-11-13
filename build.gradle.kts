// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.sonarqube)
}

sonar {
    properties {
        property("sonar.projectKey", "CarlosLavilla_MotorEvents")
        property("sonar.organization", "carloslavilla")

        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${projectDir}/app/build/reports/coverage/test/debug/report.xml"
        )
    }
}
