plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":numeron-core"))
  implementation(project(":numeron-api"))
  implementation(project(":numeron-utils"))
  implementation(project(":numeron-menu"))
  implementation(project(":numeron-boot"))
  implementation(project(":numeron-deps"))
}