load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.2"

RULES_JVM_EXTERNAL_SHA = "82262ff4223c5fda6fb7ff8bd63db8131b51b413d26eb49e3131037e79e324af"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:specs.bzl", "maven")

http_archive(
    name = "bazel_skylib",
    sha256 = "7ac0fa88c0c4ad6f5b9ffb5e09ef81e235492c873659e6bb99efb89d11246bcb",
    strip_prefix = "bazel-skylib-1.0.3",
    urls = ["https://github.com/bazelbuild/bazel-skylib/archive/1.0.3.tar.gz"],
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        maven.artifact(
            group = "org.openjfx",
            artifact = "javafx-base",
            version = "19.0.2.1",
            classifier = "win",
        ),
        maven.artifact(
            group = "org.openjfx",
            artifact = "javafx-controls",
            version = "19.0.2.1",
            classifier = "win",
        ),
        maven.artifact(
            group = "org.openjfx",
            artifact = "javafx-graphics",
            version = "19.0.2.1",
            classifier = "win",
        ),
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
