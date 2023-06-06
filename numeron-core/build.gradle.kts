plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":numeron-api"))
  implementation(project(":numeron-utils"))

  implementation(project(":numeron-deps"))
}